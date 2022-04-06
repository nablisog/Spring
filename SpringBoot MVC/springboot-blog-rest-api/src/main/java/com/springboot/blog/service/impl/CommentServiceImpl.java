package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ModelMapper mapper;
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
       Comment newComment =  commentRepository.save(comment);
       return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedcomment = commentRepository.save(comment);
        return mapToDto(updatedcomment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }
         commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        /*
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        */
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        /*
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
         */
        return comment;
    }
}
