package com.practice.blog.adapters;

import com.practice.blog.dtos.PostResponse;
import com.practice.blog.dtos.UserRequest;
import com.practice.blog.dtos.UserResponse;
import com.practice.blog.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserConverter {
    @Autowired
    private PostConverter postConverter;

    public UserEntity convertToEntity(UserRequest request){
        UserEntity entity = new UserEntity();

        entity.setUserName(request.getUserName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        return entity;
    }
    public UserResponse convertToResponse(UserEntity entity){
        UserResponse response = new UserResponse();

        response.setId(entity.getId());
        response.setUserName(entity.getUserName());
        response.setEmail(entity.getEmail());
        //response.setPassword(entity.getPassword());
        List<PostResponse> postResponseList = Optional.ofNullable(entity.getPosts())
                .orElse(Collections.emptyList())
                .stream()
                .map(postConverter::convertToResponse)
                .toList();

        response.setPosts(postResponseList);

        return response;
    }

    public UserEntity converterForUpdate(UserRequest userRequest, UserEntity userEntity){
        userEntity.setUserName(Optional.ofNullable(userRequest.getUserName()).orElseGet(userEntity::getUserName));
        userEntity.setEmail(Optional.ofNullable(userRequest.getEmail()).orElseGet(userEntity::getEmail));
        userEntity.setPassword(Optional.ofNullable(userRequest.getPassword()).orElseGet(userEntity::getPassword));

        return userEntity;
    }
}
