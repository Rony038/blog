package com.practice.blog.adapters;

import com.practice.blog.dtos.PostRequest;
import com.practice.blog.dtos.PostResponse;
import com.practice.blog.entities.PostEntity;
import org.springframework.stereotype.Service;

@Service
public class PostConverter {
    public PostEntity convertToEntity(PostRequest request){
        PostEntity entity = new PostEntity();

        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        return entity;
    }

    public PostResponse convertToResponse(PostEntity entity){
        PostResponse response = new PostResponse();

        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setUserId(entity.getUser().getId());
        response.setUserName(entity.getUser().getUserName());

        return response;
    }
}
