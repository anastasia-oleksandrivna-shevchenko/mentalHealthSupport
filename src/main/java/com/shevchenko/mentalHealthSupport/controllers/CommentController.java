package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Comment;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.CommentRepository;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import com.shevchenko.mentalHealthSupport.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post/{post}")
    public String commentsByPost(@PathVariable("post") Long post, Model model) {
        model.addAttribute("post", post);
        List<Comment> comments = commentRepository.findByPostPostid(post);
        model.addAttribute("comments", comments);
        return "comments";
    }
    @GetMapping("/post/{post}/new")
    public String showAddForm(@PathVariable("post") Long post, Model model) {
        model.addAttribute("post", post);
        return "newComment";
    }

    @PostMapping("/posts/{postid}/new")
    public String AddNewComment(Model model,
                                @RequestParam String content,
                                @RequestParam (name = "isAnonymous", required = false) Boolean isAnonymous,
                                @PathVariable("postid") Long post){

        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }
        if (isAnonymous == null) {
            isAnonymous = false;
        }
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Ім'я користувача вже зайняте.");
            return "register";
        }
        Optional<Post> optionalPost = postRepository.findByPostid(post);
        if (optionalPost.isEmpty()) {
            model.addAttribute("error", "Ім'я користувача вже зайняте.");
            return "register";
        }


        Comment comment = new Comment();
        comment.setContent(content);
        comment.setIs_anonymous(isAnonymous);
        comment.setUser(optionalUser.get());
        comment.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        comment.setPost(optionalPost.get());
        commentRepository.save(comment);

        return "redirect:/post/" + post;
    }

    @GetMapping("/my-comments")
    public String ShowMyComments(Model model){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }
        Optional<User> optionalUser = userRepository.findByUsername(username);
        List<Comment> comments = commentRepository.findByUserId(optionalUser.get().getId());
        model.addAttribute("comments", comments);
        model.addAttribute("post", 1L);
        return "comments";
    }
}
