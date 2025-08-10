package com.myshop.commentsService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsRequest {
    List<UUID> ids;
    int page = 0;
    int size = 10;
}
