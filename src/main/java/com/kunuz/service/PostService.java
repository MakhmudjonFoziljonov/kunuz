package com.kunuz.service;

import com.kunuz.dto.AttachDto;
import com.kunuz.dto.PostDto;
import com.kunuz.entity.PostEntity;
import com.kunuz.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostAttachService postAttachService;

    public PostDto create(PostDto dto) {

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());

        postRepository.save(postEntity);

        postAttachService.create(postEntity.getId(), dto.getAttachDtoList());
        dto.setId(dto.getId());
        return dto;
    }

    public PostDto getById(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Post not found");
                });

        PostDto postDTO = new PostDto();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        // attach list
        List<AttachDto> attachList = postAttachService.getAttachList(id);
        postDTO.setAttachDtoList(attachList);
        return postDTO;
    }

    public String updateById(PostDto dto, Long id) {

        Optional<PostEntity> entity = postRepository.findById(id);
        PostEntity postEntity = entity.get();
        postEntity.setId(dto.getId());
        postEntity.setContent(dto.getContent());
        postEntity.setTitle(dto.getTitle());
        postRepository.save(postEntity);

        postAttachService.merge(postEntity.getId(),dto.getAttachDtoList());
        return "Updated";
    }
}
