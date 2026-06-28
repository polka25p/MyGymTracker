package org.gym.service;

import org.gym.model.WorkoutVisit;
import java.util.List;

public interface GymService {
    double calculateTrainerDebts(List<WorkoutVisit> workouts);
    long getWorkoutCountInCurrentMonth(List<WorkoutVisit> workouts);
}