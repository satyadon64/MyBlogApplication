package com.blog.payload;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "title should be more than 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 5,max = 100,message = "description should be more than 5 characters")
    private String description;
    private String content;
}
