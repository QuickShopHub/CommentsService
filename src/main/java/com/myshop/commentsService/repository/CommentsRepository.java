package com.myshop.commentsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    
}
