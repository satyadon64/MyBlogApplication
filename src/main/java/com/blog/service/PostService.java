package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
   PostDto createPost(PostDto postDto);

   List<PostDto> listAllDto(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePost(long id);
}

