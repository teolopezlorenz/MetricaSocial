package com.metricasocial.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {

    private Long id;
    private String username;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
