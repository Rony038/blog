package com.practice.blog.controllers;

import com.practice.blog.dtos.PostRequest;
import com.practice.blog.dtos.PostResponse;
import com.practice.blog.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("**")
@RequestMapping("/blog/api/posts")
@Tag(name = "Post APIs", description = "Create, Read, Update & Delete Posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostResponse> savePost(@Valid @RequestBody PostRequest postRequest){
        return new ResponseEntity<>(postService.savePost(postRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @Valid @RequestBody PostRequest postRequest){
        return new ResponseEntity<>(postService.updatePost(id, postRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

}
