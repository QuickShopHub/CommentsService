package com.myshop.commentsService.service;

import com.myshop.commentsService.DTO.CommentsRequest;
import com.myshop.commentsService.repository.Comment;
import com.myshop.commentsService.repository.CommentsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Comment> getComments(CommentsRequest commentsRequest){

        Pageable pageable = PageRequest.of(commentsRequest.getPage(), commentsRequest.getSize());

        if(commentsRequest.getIds() == null || commentsRequest.getIds().isEmpty()){
            return Page.empty(pageable);
        }

        List<Comment> allComments = commentsRepository.findAllById(commentsRequest.getIds());

        List<Comment> res = allComments.subList((commentsRequest.getPage()*commentsRequest.getSize())%allComments.size(),
                (commentsRequest.getPage()*commentsRequest.getSize() + commentsRequest.getSize())%allComments.size());

        return new PageImpl<>(res, pageable, allComments.size());

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
