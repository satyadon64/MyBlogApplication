package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts",uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="description",nullable = false)
    private String description;
    @Column(name="content",nullable = false)
    private String content;

    //CascadeTyoe.ALL == means whenever we make a change in post then it will reflect in comments also..
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments;

}

