package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
//http:localhost:8080/api/posts/1/comments
    @Autowired
    private CommentService commentService;
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http:localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsById(@PathVariable(value="postId") long postId){
        List<CommentDto> dtos =commentService.getComments(postId);
        return dtos;
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable(value = "postId") long postId,
                                     @PathVariable(value = "commentId") long commentId
                                     ){
        CommentDto dto=commentService.getComment(postId,commentId);
        return dto;
    }
    //  http://localhost:8080/api/posts/1/comments/4
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value="postId") long postId,
            @PathVariable(value="commentId") long commentId,
            @RequestBody CommentDto dto

    ){

       CommentDto newDto= commentService.updateComment(postId,commentId,dto);
        return new ResponseEntity<>(newDto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted Successfully..!!",HttpStatus.OK);

    }

}
