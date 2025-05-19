package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Comment;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.CommentRepository;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import com.shevchenko.mentalHealthSupport.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
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
    private PostRepository postRepository;

    @GetMapping("/post/{post}")
    public String commentsByPost(@PathVariable("post") Long post, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("post", post);

        List<Comment> comments = commentRepository.findByPostPostid(post);
        model.addAttribute("comments", comments);

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        return "comments";
    }
    @GetMapping("/post/{post}/new")
    public String showAddForm(@PathVariable("post") Long post, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("post", post);

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        return "newComment";
    }

    @PostMapping("/posts/{postid}/new")
    public String AddNewComment(Model model,
                                @RequestParam String content,
                                @RequestParam (name = "isAnonymous", required = false) Boolean isAnonymous,
                                @PathVariable("postid") Long post,
                                @AuthenticationPrincipal UserDetails userDetails){

        String username = userDetails.getUsername();
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
    public String ShowMyComments(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }
        Optional<User> optionalUser = userRepository.findByUsername(username);
        List<Comment> comments = commentRepository.findByUserId(optionalUser.get().getId());
        model.addAttribute("comments", comments);
        model.addAttribute("post", 1L);
        return "comments";
    }
    @PostMapping("/comment/{id}/delete")
    public String deletePost(@PathVariable("id") Long commentId) {

        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Comment comment = optionalComment.get();

        commentRepository.delete(comment);


        return "redirect:/";
    }
}
