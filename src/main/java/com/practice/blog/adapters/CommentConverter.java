package com.practice.blog.adapters;

import com.practice.blog.dtos.CommentRequest;
import com.practice.blog.dtos.CommentResponse;
import com.practice.blog.entities.CommentEntity;
import com.practice.blog.entities.PostEntity;
import com.practice.blog.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentConverter {
    public CommentEntity convertToEntity(CommentRequest commentRequest, PostEntity postEntity, UserEntity userEntity){
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);

        return commentEntity;
    }

    public CommentResponse convertToResponse(CommentEntity commentEntity){
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(commentEntity.getId());
        commentResponse.setContent(commentEntity.getContent());
        commentResponse.setPostId(commentEntity.getPost().getId());

        return commentResponse;
    }

    public CommentEntity converterForUpdate(CommentEntity commentEntity, CommentRequest commentRequest, UserEntity userEntity, PostEntity postEntity) {
        commentEntity.setContent(Optional.ofNullable(commentRequest.getContent()).orElseGet(commentEntity::getContent));
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);

        return commentEntity;
    }
}
