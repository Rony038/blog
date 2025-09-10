package com.practice.blog.adapters;

import com.practice.blog.dtos.PostRequest;
import com.practice.blog.dtos.PostResponse;
import com.practice.blog.entities.PostEntity;
import com.practice.blog.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

@Service
public class PostConverter {
    @Autowired
    private CommentConverter commentConverter;

    public PostEntity convertToEntity(PostRequest request, UserEntity userEntity){
        PostEntity entity = new PostEntity();

        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setUser(userEntity);
        return entity;
    }

    public PostResponse convertToResponse(PostEntity entity){
        PostResponse response = new PostResponse();

        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setUserId(entity.getUser().getId());
        response.setUserName(entity.getUser().getUserName());
        response.setCommentResponseList(Optional.ofNullable(entity.getComments())
                .orElse(Collections.emptyList())
                .stream()
                .map(commentConverter::convertToResponse)
                .toList());

        return response;
    }

    public PostEntity converterForUpdate(PostRequest postRequest, PostEntity postEntity, UserEntity userEntity) {
        postEntity.setTitle(Optional.ofNullable(postRequest.getTitle()).orElseGet(postEntity::getTitle));
        postEntity.setContent(Optional.ofNullable(postRequest.getContent()).orElseGet(postEntity::getContent));
        postEntity.setUser(userEntity);

        return postEntity;
    }
}
