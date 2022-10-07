package com.resttemplet.service;

import com.resttemplet.model.Comment;
import com.resttemplet.model.Post;

public interface PostService {
    Post[] getAllPosts();
    Comment[] getAllCommentsforAPosts(Long id);
    Post createPost(Post post);
    Post updatePost(Post post,Long id);
    void deletePost(Long id);
}
