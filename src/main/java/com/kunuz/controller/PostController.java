package com.kunuz.controller;

import com.kunuz.dto.PostDto;
import com.kunuz.service.AttachService;
import com.kunuz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public PostDto create(@RequestBody PostDto dto) {
        return postService.create(dto);
    }

    @GetMapping("/get/{id}")
    public PostDto getById(@PathVariable("id") Long id) {
        return postService.getById(id);
    }

    @PutMapping("/update/{id}")
    public String updateById(@RequestBody PostDto dto,@PathVariable("id") Long id){
        return postService.updateById(dto,id);
    }

}
