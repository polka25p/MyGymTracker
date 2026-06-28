package org.gym.service.impl;

import org.gym.model.WorkoutVisit;
import org.gym.service.GymService;

import java.time.LocalDate;
import java.util.List;

public class GymServiceImpl implements GymService {

    @Override
    public double calculateTrainerDebts(List<WorkoutVisit> workouts) {
        if (workouts == null || workouts.isEmpty()) {
            return 0.0;
        }

        long unpaidCount = workouts.stream()
                .filter(w -> w.isWithTrainer() && !w.isTrainerPaid())
                .count();

        return unpaidCount * 700.0;
    }

    @Override
    public long getWorkoutCountInCurrentMonth(List<WorkoutVisit> workouts) {
        if (workouts == null || workouts.isEmpty()) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        return workouts.stream()
                .filter(w -> w.getVisitDate() != null
                        && w.getVisitDate().getMonthValue() == currentMonth
                        && w.getVisitDate().getYear() == currentYear)
                .count();
    }
}