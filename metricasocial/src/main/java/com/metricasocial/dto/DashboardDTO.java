package com.metricasocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private String username;
    private Integer totalPoints;
    private Integer totalActivities;
    private Double averagePointsPerActivity;
    private Integer rankingPosition;
}
