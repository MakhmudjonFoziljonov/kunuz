package com.kunuz.service;

import com.kunuz.dto.AttachDto;
import com.kunuz.dto.AttachRecord;
import com.kunuz.entity.AttachEntity;
import com.kunuz.exps.AppBadRequestException;
import com.kunuz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class AttachService {
    private String folderName = "attaches";
    @Autowired
    private AttachRepository attachRepository;
    @Value("${server.domain}")
    private String domainName;

    private static final Map<String, Object> images = new HashMap<>();

    static {
        images.put("jpg", new Object());
        images.put("png", new Object());
        images.put("jpeg", new Object());
    }


    public ResponseEntity<Resource> open(String id) {
        AttachEntity entity = getEntity(id);
        String path = folderName + "/" + entity.getPath() + "/" + entity.getId();


        Path filePath = Paths.get(path).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + entity.getId());
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public AttachDto upload(MultipartFile file) throws IOException {
        // attaches/2024/11/2
        String pathFolder = getYmDString();
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // .jpg, .png, .mp4

        // create folder if not exists
        File folder = new File(folderName + "/" + pathFolder); // attaches/2024/09/27
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2024/09/27/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // save to db
        AttachEntity entity = new AttachEntity();
        entity.setId(key + "." + extension);
        entity.setPath(pathFolder);
        entity.setSize(file.getSize());
        entity.setOrigenName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setVisible(true);
        attachRepository.save(entity);

        if (images.containsKey(extension.toLowerCase())) {
            AttachDto compressedAttachDto = uploadImagesWithCompress(file);

            if (compressedAttachDto != null) {
                AttachEntity compressedAttachEntity = attachRepository.findById(compressedAttachDto.getId()).orElse(null);
                entity.setCompressedAttach(compressedAttachEntity);  // Set the relationship
                attachRepository.save(entity);  // Save updated entity with compressed attach reference
            }
        }

        return toDTO(entity);
    }

    public AttachDto uploadImagesWithCompress(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new AppBadRequestException("File not found");
        }
        try {
            String pathFolder = getYmDString();
            String key = UUID.randomUUID().toString();
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            File file1 = new File(folderName + "/" + pathFolder);
            if (!file1.exists()) {
                boolean mkdirs = file1.mkdirs();
            }

            BufferedImage image = ImageIO.read(file.getInputStream());
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
            ImageWriter writer = writers.next();
            ImageOutputStream outputStream = null;
            outputStream = ImageIO.createImageOutputStream(path.toFile());

            writer.setOutput(outputStream);

            ImageWriteParam params = writer.getDefaultWriteParam();
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            params.setCompressionQuality(0.30f);

            writer.write(null, new IIOImage(image, null, null), params);

            outputStream.close();
            writer.dispose();
            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ResponseEntity<Resource> download(String fileName) {
        try {
            AttachEntity entity = getEntity(fileName);
            Path filePath = Paths.get(getPath(entity)).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getOrigenName() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read the file!");
        }

    }


    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf("."); // mazgi.latta.jpg
        return fileName.substring(lastIndex + 1);
    }

    private AttachDto toDTO(AttachEntity entity) {
        AttachDto attachDTO = new AttachDto();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(getUrl(entity.getId()));
        return attachDTO;
    }

    public String getUrl(String id) {
        return domainName + "attach/open/" + id;
    }

    public AttachEntity getEntity(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("File not found");
        }
        return optional.get();
    }

    private String getPath(AttachEntity entity) {
        return folderName + "/" + entity.getPath() + "/" + entity.getId();
    }

    public AttachDto getDTO(String id) {
        if (id == null) return null;

        AttachDto dto = new AttachDto();
        dto.setId(id);
        dto.setUrl(getUrl(id));
        return dto;
    }


    public AttachRecord getRecord(String id) {
        if (id == null) return null;

        AttachRecord dto = AttachRecord
                .builder()
                .id(id)
                .url(getUrl(id))
                .build();
        return dto;
    }
}
