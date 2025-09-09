package com.practice.blog.controllers;

import com.practice.blog.dtos.UserRequest;
import com.practice.blog.dtos.UserResponse;
import com.practice.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blog/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id,
                                                   @Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.updateUser(id, userRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
