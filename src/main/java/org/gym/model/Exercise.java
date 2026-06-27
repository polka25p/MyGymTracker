package org.gym.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Exercise {
    private int id;
    private String name;
    private int workoutId;
    private double weight;
    private int setsReps;
}
