package org.gym.service;

import org.gym.model.WorkoutVisit;
import org.gym.service.impl.GymServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GymServiceTest {

    private final GymService gymService = new GymServiceImpl();

    @Test
    public void testCalculateTrainerDebts_WithUnpaidWorkouts() {
        List<WorkoutVisit> workouts = new ArrayList<>();

        WorkoutVisit w1 = new WorkoutVisit();
        w1.setWithTrainer(true);
        w1.setTrainerPaid(false); // Борг 700 грн

        WorkoutVisit w2 = new WorkoutVisit();
        w2.setWithTrainer(true);
        w2.setTrainerPaid(true);  // Оплачено

        WorkoutVisit w3 = new WorkoutVisit();
        w3.setWithTrainer(true);
        w3.setTrainerPaid(false); // Борг 700 грн (Разом 1400)

        workouts.add(w1);
        workouts.add(w2);
        workouts.add(w3);

        double debt = gymService.calculateTrainerDebts(workouts);

        assertEquals(1400.0, debt, "Розрахунок боргу тренеру працює некоректно!");
    }

    @Test
    public void testGetWorkoutCountInCurrentMonth_FiltersCorrectly() {
        List<WorkoutVisit> workouts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        WorkoutVisit w1 = new WorkoutVisit();
        w1.setVisitDate(today); // Цей місяць -> ПОВИНЕН порахувати

        WorkoutVisit w2 = new WorkoutVisit();
        w2.setVisitDate(today.minusDays(1)); // Теж цей місяць -> ПОВИНЕН порахувати

        WorkoutVisit w3 = new WorkoutVisit();
        w3.setVisitDate(today.minusMonths(2)); // Два місяці тому -> НЕ ПОВИНЕН рахувати

        workouts.add(w1);
        workouts.add(w2);
        workouts.add(w3);

        long count = gymService.getWorkoutCountInCurrentMonth(workouts);

        assertEquals(2, count, "Фільтрація тренувань за поточний місяць працює невірно!");
    }
}