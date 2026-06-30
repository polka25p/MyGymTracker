package org.gym.service.impl;

import org.gym.model.WorkoutVisit;
import org.gym.service.GymService;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link GymService} interface providing business logic
 * for analyzing workout visit telemetry.
 * <p>
 * This service handles core calculations such as identifying accumulated trainer debts
 * and tracking corporate workout performance metrics within dynamic calendar boundaries.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see GymService
 * @see WorkoutVisit
 */

public class GymServiceImpl implements GymService {

    /**
     * Calculates the total outstanding debt owed to the personal trainer from a list of visits.
     * <p>
     * Filters the provided stream of workout records to isolate items where a trainer was
     * present ({@code isWithTrainer() == true}) but the services have not yet been compensated
     * ({@code isTrainerPaid() == false}). Each matching unpaid record increments the total
     * balance debt by a fixed rate of {@code 700.0} credits.
     * </p>
     *
     * @param workouts a {@link List} of {@link WorkoutVisit} logs to evaluate;
     * if {@code null} or empty, this method safely returns {@code 0.0}.
     * @return the total accumulated trainer debt as a {@code double}.
     */

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

    /**
     * Determines the total number of workout visits logged during the current calendar month.
     * <p>
     * Evaluates the local execution environment's timestamp via {@link LocalDate#now()} to resolve
     * the present target year and month index. It then filters the provided collection, dropping
     * empty entries or logs mapped to historical or future calendar periods.
     * </p>
     *
     * @param workouts a {@link List} of {@link WorkoutVisit} logs to evaluate;
     * if {@code null} or empty, this method safely returns {@code 0}.
     * @return the total count of workout occurrences recorded in the current month as a {@code long}.
     */

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