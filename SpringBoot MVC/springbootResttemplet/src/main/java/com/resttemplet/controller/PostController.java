package com.resttemplet.controller;

import com.resttemplet.model.Comment;
import com.resttemplet.model.Post;
import com.resttemplet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/getposts")
    public Post[] getAllPosts(){
      return postService.getAllPosts();

    }
    @GetMapping("/getcomments/{id}/comments")
    public Comment[] getAllCommentsForAPost(@PathVariable("id") Long id){
        return postService.getAllCommentsforAPosts(id);
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @PutMapping("/update/{id}")
    public Post updatePost(@RequestBody Post post,@PathVariable("id")Long id){
        return postService.updatePost(post,id);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id") Long id){
         postService.deletePost(id);
    }



}
