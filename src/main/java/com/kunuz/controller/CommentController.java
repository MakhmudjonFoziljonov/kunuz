package com.kunuz.controller;

import com.kunuz.dto.CommentDto;
import com.kunuz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','MODERATOR')")
    public String update(@RequestBody CommentDto dto,
                         @RequestParam String id) {
        return commentService.update(dto, id);
    }


}
