package com.practice.blog.services;

import com.practice.blog.adapters.UserConverter;
import com.practice.blog.dtos.UserRequest;
import com.practice.blog.dtos.UserResponse;
import com.practice.blog.entities.UserEntity;
import com.practice.blog.exceptions.ResourceNotFoundException;
import com.practice.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userConverter::convertToResponse)
                .toList();
    }

    public UserResponse getUserById(long id) {
        return userRepository.findById(id)
                .map(userConverter::convertToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public UserResponse registerUser(UserRequest userRequest) {
        if(userRepository.existsByUserName(userRequest.getUserName())){
            throw new IllegalArgumentException("User name " + userRequest.getUserName() + " already exists!");
        }
        UserEntity registeredUser = userRepository.save(userConverter.convertToEntity(userRequest));

        return userConverter.convertToResponse(registeredUser);
    }

    public UserResponse updateUser(long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserEntity updatedEntity = userConverter.converterForUpdate(userRequest, userEntity);
        UserEntity saved = userRepository.save(updatedEntity);

        return userConverter.convertToResponse(saved);
    }

    public void deleteById(long id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("User is not found with id: " + id);
        }
    }

}
