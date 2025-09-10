package com.practice.blog.services;

import com.practice.blog.adapters.CommentConverter;
import com.practice.blog.dtos.CommentRequest;
import com.practice.blog.dtos.CommentResponse;
import com.practice.blog.entities.CommentEntity;
import com.practice.blog.entities.PostEntity;
import com.practice.blog.entities.UserEntity;
import com.practice.blog.exceptions.ResourceNotFoundException;
import com.practice.blog.repositories.CommentRepository;
import com.practice.blog.repositories.PostRepository;
import com.practice.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentConverter commentConverter;

    public List<CommentResponse> getAllComments(){
        return commentRepository.findAll().stream()
                .map(commentConverter::convertToResponse)
                .toList();
    }

    public CommentResponse getCommentById(long id){
        return commentRepository.findById(id)
                .map(commentConverter::convertToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: "+ id));
    }

    public CommentResponse saveComment(CommentRequest commentRequest){
        UserEntity userEntity = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + commentRequest.getUserId()));

        PostEntity postEntity = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: "+ commentRequest.getPostId()));

        CommentEntity savedComment = commentRepository.save(commentConverter.convertToEntity(commentRequest, postEntity, userEntity));

        return commentConverter.convertToResponse(savedComment);
    }

    public CommentResponse updateComment(long id, CommentRequest commentRequest){
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id));

        UserEntity userEntity = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + commentRequest.getUserId()));

        PostEntity postEntity = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: "+ commentRequest.getPostId()));

        CommentEntity updatedComment = commentRepository.save(commentConverter.converterForUpdate(commentEntity, commentRequest, userEntity, postEntity));

        return commentConverter.convertToResponse(updatedComment);
    }

    public void deleteCommentById(long id){
        if(commentRepository.findById(id).isPresent()){
            commentRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("Comment is not found with ID: " +id);
        }
    }
}
