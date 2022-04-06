package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD Rest Apis for Comment resources")
@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Create Comment Rest API")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                              @Valid @RequestBody CommentDto commentDto){

        CommentDto cm = commentService.createComment(postId,commentDto);

        return new ResponseEntity<>(cm, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Getting All Comment By ID Rest API")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @ApiOperation(value = "Getting A Comment By ID Rest API")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "commentId") long commentId){
        CommentDto commentDto = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    @ApiOperation(value = "Update A Comment By ID Rest API")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentId") long commentId,
                                             @Valid @RequestBody CommentDto commentDto){

        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);

    }
    @ApiOperation(value = "Delete A Comment By ID Rest API")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId){

         commentService.deleteComment(postId,commentId);
         return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }


}
