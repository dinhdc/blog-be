package com.person.blogbe.controller;

import com.person.blogbe.payload.CommentDto;
import com.person.blogbe.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("id") Long postId,
            @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<CommentDto>(this.commentService.createComment(postId, commentDto),
                HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(this.commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return new ResponseEntity<>(this.commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(this.commentService.updateComment(postId, commentId, commentDto),
                HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        this.commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
