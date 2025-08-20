package com.myshop.commentsService.controller;


import com.myshop.commentsService.DTO.CommentsRequest;
import com.myshop.commentsService.repository.Comment;
import com.myshop.commentsService.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return commentService.deleteComment(id);
    }

    @PostMapping(path = "comments")
    public ResponseEntity<PagedModel<EntityModel<Comment>>> getComment(@RequestBody CommentsRequest commentsRequest,
                                                                        PagedResourcesAssembler<Comment> pagedResourcesAssembler) {
        Page<Comment> page = commentService.getComments(commentsRequest);
        PagedModel<EntityModel<Comment>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping
    public ResponseEntity<String> setLike(@RequestBody UUID id) {
        return commentService.setLike(id);
    }

}
