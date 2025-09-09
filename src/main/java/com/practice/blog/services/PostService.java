package com.practice.blog.services;

import com.practice.blog.adapters.PostConverter;
import com.practice.blog.dtos.PostRequest;
import com.practice.blog.dtos.PostResponse;
import com.practice.blog.entities.PostEntity;
import com.practice.blog.entities.UserEntity;
import com.practice.blog.exceptions.ResourceNotFoundException;
import com.practice.blog.repositories.PostRepository;
import com.practice.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostConverter postConverter;

    public List<PostResponse> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postConverter::convertToResponse)
                .toList();
    }

    public PostResponse getPostById(long id){
        return postRepository.findById(id)
                .map(postConverter::convertToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
    }

    public PostResponse savePost(PostRequest postRequest){
        if(postRepository.existsByTitle(postRequest.getTitle())){
            throw new IllegalArgumentException("Post Title " + postRequest.getTitle() + " already exists!");
        }

        UserEntity userEntity = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + postRequest.getUserId()));

        PostEntity savedPost = postRepository.save(postConverter.convertToEntity(postRequest, userEntity));
        return postConverter.convertToResponse(savedPost);
    }

    public PostResponse updatePost(long id, PostRequest postRequest){
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id:" + id));

        UserEntity userEntity = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + postRequest.getUserId()));

        PostEntity updatedPost = postRepository.save(postConverter.converterForUpdate(postRequest, postEntity, userEntity));

        return postConverter.convertToResponse(updatedPost);
    }

    public void deletePostById(long id) {
        if(postRepository.findById(id).isPresent()){
            postRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("Post is not found with id: " + id);
        }
    }
}
