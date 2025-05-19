package com.shevchenko.mentalHealthSupport.controllers;

import com.shevchenko.mentalHealthSupport.models.Category;
import com.shevchenko.mentalHealthSupport.models.Post;
import com.shevchenko.mentalHealthSupport.models.Tag;
import com.shevchenko.mentalHealthSupport.models.User;
import com.shevchenko.mentalHealthSupport.repositories.CategoryRepository;
import com.shevchenko.mentalHealthSupport.repositories.PostRepository;
import com.shevchenko.mentalHealthSupport.repositories.TagRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/category/{categoryid}")
    public String postsByCategory(@PathVariable("categoryid") Long categoryid,
                                  Model model,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("category", categoryid);

        List<Post> posts = postRepository.findByCategoryCategoryid(categoryid);
        model.addAttribute("posts", posts);

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        return "posts";
    }
    @GetMapping("/category/{categoryid}/new")
    public String showAddForm(@PathVariable("categoryid") Long categoryid,
                              Model model,
                              @AuthenticationPrincipal UserDetails userDetails) {

        boolean isLoggedIn = userDetails != null;
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("category", categoryid);

        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "disabled");
        model.addAttribute("_csrf", csrfToken);

        return "newPost";
    }

    @PostMapping("/category/{categoryid}/new")
    public String AddNewPost(Model model,
                             @RequestParam String title,
                             @RequestParam String content,
                             @RequestParam (name = "isAnonymous", required = false) Boolean isAnonymous,
                             @PathVariable("categoryid") Long categoryid,
                             @RequestParam(required = false) List<String> tags,
                             @AuthenticationPrincipal UserDetails userDetails){

        String username = userDetails.getUsername();

        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }
        if (isAnonymous == null) {
            isAnonymous = false;
        }
        Optional<Category> optionalCategory = categoryRepository.findByCategoryid(categoryid);
        if (optionalCategory.isEmpty()) {
            return "redirect:/error";
        }
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Ім'я користувача вже зайняте.");
            return "register";
        }

        Post post = new Post();
        post.setContent(content);
        post.setIs_anonymous(isAnonymous);
        post.setUser(optionalUser.get());
        post.setTitle(title);
        post.setCategory(optionalCategory.get());
        post.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        Set<Tag> tagSet = new HashSet<>();
        if (tags != null) {
            for (String tagName : tags) {
                Optional<Tag> tagOptional = tagRepository.findByName(tagName);
                Tag tag = tagOptional.orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });
                tagSet.add(tag);
            }
        }
        post.setTags(tagSet);

        postRepository.save(post);

        return "redirect:/category/" + categoryid;
    }

    @GetMapping("/my-posts")
    public String ShowMyPosts(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        if (username == null) {
            return "redirect:/login?redirect=/profile";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);

        List<Post> posts = postRepository.findByUserId(optionalUser.get().getId());
        model.addAttribute("posts", posts);
        model.addAttribute("category", 1L);
        return "posts";
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable("id") Long postId) {

        Optional<Post> optionalPost = postRepository.findById(postId);

        Post post = optionalPost.get();

            postRepository.delete(post);


        return "redirect:/";
    }


}
