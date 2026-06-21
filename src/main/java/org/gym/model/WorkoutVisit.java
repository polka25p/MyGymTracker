package org.gym.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutVisit {
private int id;
private LocalDate visitDate;
private String notes;
private boolean withTrainer;
private boolean trainerPaid;
}
