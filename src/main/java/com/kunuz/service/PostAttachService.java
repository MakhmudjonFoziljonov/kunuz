package com.kunuz.service;

import com.kunuz.dto.AttachDto;
import com.kunuz.entity.PostAttachEntity;
import com.kunuz.repository.PostAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostAttachService {
    @Autowired
    private PostAttachRepository postAttachRepository;
    @Autowired
    private AttachService attachService;

    public void create(Long postId, List<AttachDto> attachIdList) {
        if (attachIdList == null) {
            return;
        }

        for (AttachDto attachId : attachIdList) {
            PostAttachEntity entity = new PostAttachEntity();
            entity.setPostId(postId);
            entity.setAttachId(attachId.getId());
            postAttachRepository.save(entity);
        }
    }

    public List<AttachDto> getAttachList(Long postId) {
        List<String> attachIdList = postAttachRepository.findAllByPostId(postId);

        List<AttachDto> attachDTOList = new ArrayList<>();

        for (String attachId : attachIdList) {
            attachDTOList.add(attachService.getDTO(attachId));
        }
        return attachDTOList;
    }

    public void merge(Long postId, List<AttachDto> newAttachIdList) {
        // old [1,2,3,4]
        // new [1,7]
        // -----------
        // result [1,7]

        if (newAttachIdList == null) {
            newAttachIdList = new ArrayList<>();
        }

        List<String> oldAttachIdList = postAttachRepository.findAllByPostId(postId);
        for (String attachId : oldAttachIdList) {
            if (!exists(attachId, newAttachIdList)) {
                // exists operation {attachId}
                postAttachRepository.deleteByPostIdAndAttachId(postId, attachId);
            }
        }

        for (AttachDto newAttach : newAttachIdList) {
            if (!oldAttachIdList.contains(newAttach.getId())) {
                // save
                PostAttachEntity entity = new PostAttachEntity();
                entity.setPostId(postId);
                entity.setAttachId(newAttach.getId());
                postAttachRepository.save(entity);
            }
        }
//
//        postAttachRepository.deleteByPostId(postId);
//        create(postId, newAttachIdList);
    }

    private boolean exists(String attachId, List<AttachDto> dtoList) {
        for (AttachDto dto : dtoList) {
            if (dto.getId().equals(attachId)) {
                return true;
            }
        }
        return false;
    }

}
