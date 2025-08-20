package com.myshop.commentsService.service;

import com.myshop.commentsService.repository.Comment;
import com.myshop.commentsService.repository.CommentsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentsRepository commentsRepository;

    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }


    public Comment save(Comment comment) {
        comment.setId(UUID.randomUUID());
        comment.setCreatedAt(LocalDate.now());




        return commentsRepository.save(comment);
    }

    public ResponseEntity<String> deleteComment(UUID id){
        if(id == null){
            return ResponseEntity.badRequest().build();
        }
        commentsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public List<Comment> getComments(UUID id) {
        if (id == null) {
            return List.of();
        }
        return commentsRepository.findAllByProductId(id);
    }

    public ResponseEntity<String> setLike(UUID id){
        Comment comment = commentsRepository.findById(id).orElse(null);

        if(comment == null){
            return ResponseEntity.badRequest().body("Comment not found");
        }

        comment.like();
        commentsRepository.save(comment);
        return ResponseEntity.ok().build();
    }

}
