package com.myshop.commentsService.controller;


import com.myshop.commentsService.repository.Comment;
import com.myshop.commentsService.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "new_comment")
    public Comment save(@RequestBody @Valid Comment comment) {
        return commentService.save(comment);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(path = "comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable UUID id) {
        return commentService.deleteComment(id);
    }

    @GetMapping(path = "comments")
    public List<Comment> getComment(@RequestParam UUID id) {
        return commentService.getComments(id);
    }

    @PutMapping
    public ResponseEntity<String> setLike(@RequestBody UUID id) {
        return commentService.setLike(id);
    }

}
