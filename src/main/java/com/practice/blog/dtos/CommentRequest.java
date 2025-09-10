package com.practice.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private long id;

    @NotBlank(message = "Content can not be blank.")
    @Size(min = 10, message = "Content must not less than 10 characters.")
    private String content;

    private long postId;
    private long userId;
}
