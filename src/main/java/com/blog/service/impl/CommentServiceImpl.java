package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found at id="+postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);


        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getComments(long postId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("No post found at id="+postId)
        );
        List<Comment> comments =commentRepository.findByPostId(postId);

        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto getComment(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found at id="+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found at id=" + commentId)
        );
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto dto) {
        Post post= postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("No post found at id="+postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("No comment found at id=" + commentId)
        );
        Comment comment1 = mapToEntity(dto);
        comment1.setId(commentId);
        comment1.setPost(post);

        Comment newComment = commentRepository.save(comment1);

        return mapToDto(newComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post= postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("No post found at id="+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("No comment found at id=" + commentId)
        );
    commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment comment){

        CommentDto dto = modelMapper.map(comment, CommentDto.class);
//        CommentDto dto= new CommentDto();
//
//        dto.setId(comment.getId());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());

        return dto;

    }

    private Comment mapToEntity(CommentDto dto){

        Comment comment = modelMapper.map(dto, Comment.class);
//        Comment comment= new Comment();
//
//        comment.setId(dto.getId());
//        comment.setBody(dto.getBody());
//        comment.setEmail(dto.getEmail());
//        comment.setName(dto.getName());

        return comment;

    }
}
