package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
//   postRepo dependency injection
    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    //   modelMapper dependency injection
    @Autowired
    private ModelMapper modelMapper;

//    public PostServiceImpl(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post= new Post();

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepo.save(post);

        PostDto dto= new PostDto();

        dto.setId(newPost.getId());
        dto.setContent(newPost.getContent());
        dto.setDescription(newPost.getDescription());
        dto.setTitle(newPost.getTitle());

        return dto;
    }

    @Override
    public List<PostDto> listAllDto(int pageNo, int pageSize, String sortBy, String sortDir) {

//     Ternary Operator
//        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Sort sort=null;
    if(sortDir.equalsIgnoreCase("asc")) {
        sort = Sort.by(sortBy).ascending();
    }else {
        Sort.by(sortBy).descending();
    }

        Pageable pageable =PageRequest.of(pageNo,pageSize,sort);

        Page<Post> ListOfPosts = postRepo.findAll(pageable);
        List<Post> posts = ListOfPosts.getContent();

        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());


        return dtos;
    }

    @Override
    public PostDto getById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid id no/ Or id not found at id=" + id)
        );
        PostDto dto=mapToDto(post);
        return dto;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post=postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Invalid id no/ Or id not found at id=" + id)
        );

        Post newPost = mapToEntity(postDto);
        newPost.setId(id);
        postRepo.save(newPost);

        PostDto dto = mapToDto(newPost);


        return dto;
    }

    @Override
    public void deletePost(long id) {
        Post post=postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Invalid id at id="+id)
        );
        postRepo.deleteById(id);
    }


    public PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);

//        PostDto dto= new PostDto();
//
//        dto.setId(post.getId());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());

        return dto;
    }

    public Post mapToEntity(PostDto postDto){
        Post newPost = modelMapper.map(postDto, Post.class);

//        Post newPost= new Post();
//        newPost.setId(postDto.getId());
//        newPost.setContent(postDto.getContent());
//        newPost.setDescription(postDto.getDescription());
//        newPost.setTitle(postDto.getTitle());

        return newPost;
    }
}
