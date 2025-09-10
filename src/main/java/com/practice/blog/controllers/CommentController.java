package com.practice.blog.controllers;

import com.practice.blog.dtos.CommentRequest;
import com.practice.blog.dtos.CommentResponse;
import com.practice.blog.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(){
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable long id){
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(@Valid @RequestBody CommentRequest commentRequest){
        return new ResponseEntity<>(commentService.saveComment(commentRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable long id, @Valid @RequestBody CommentRequest commentRequest){
        return new ResponseEntity<>(commentService.updateComment(id, commentRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }
}
