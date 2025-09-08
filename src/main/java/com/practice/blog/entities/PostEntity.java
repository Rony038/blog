package com.practice.blog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Title can not be blank.")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Lob
    @NotBlank(message = "Content can not be blank.")
    @Size(min = 10, message = "Content must not less than 10 characters")
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
