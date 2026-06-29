package org.gym.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gym.dao.WorkoutVisitDao;
import org.gym.dao.impl.WorkoutVisitDaoImpl;
import org.gym.model.WorkoutVisit;
import org.gym.service.GymService;
import org.gym.service.MembershipService;
import org.gym.service.impl.GymServiceImpl;
import org.gym.service.impl.MembershipServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/workouts")
public class WorkoutServlet extends HttpServlet {
    private final WorkoutVisitDao workoutDao = new WorkoutVisitDaoImpl();
    private final GymService gymService = new GymServiceImpl();
    private final MembershipService membershipService = new MembershipServiceImpl();
    private LocalDate nextPaymentDate = LocalDate.now().plusDays(14);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<WorkoutVisit> workouts = workoutDao.getAllWorkouts();

            double trainerDebt = gymService.calculateTrainerDebts(workouts);
            long monthlyCount = gymService.getWorkoutCountInCurrentMonth(workouts);

            long daysLeft = membershipService.calculateDaysLeft(nextPaymentDate);
            boolean isActive = membershipService.isSubscriptionActive(nextPaymentDate);

            request.setAttribute("workouts", workouts);
            request.setAttribute("trainerDebt", trainerDebt);
            request.setAttribute("monthlyWorkouts", monthlyCount);
            request.setAttribute("daysLeft", daysLeft);
            request.setAttribute("isMembershipActive", isActive);
            request.setAttribute("nextPaymentDate", nextPaymentDate);

            request.getRequestDispatcher("/workouts.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Помилка завантаження даних", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("updateMembership".equals(action)) {
                String newDateStr = request.getParameter("newPaymentDate");
                if (newDateStr != null && !newDateStr.isEmpty()) {
                    this.nextPaymentDate = LocalDate.parse(newDateStr);
                }

            } else if ("delete".equals(action)) {
                // 1. Отримуємо ID тренування для видалення
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = (int) Long.parseLong(idStr);
                    // 2. Викликаємо метод видалення у твого DAO
                    workoutDao.delete(id);
                }

            } else {
                // 4. Створення НОВОГО тренування (спрацьовує, коли action порожній)
                String visitDateStr = request.getParameter("visitDate");
                String notes = request.getParameter("notes");
                boolean withTrainer = "true".equals(request.getParameter("withTrainer"));
                boolean trainerPaid = "true".equals(request.getParameter("trainerPaid"));

                if (visitDateStr != null && !visitDateStr.isEmpty()) {
                    WorkoutVisit workout = new WorkoutVisit();
                    workout.setVisitDate(LocalDate.parse(visitDateStr));
                    workout.setNotes(notes);
                    workout.setWithTrainer(withTrainer);
                    workout.setTrainerPaid(trainerPaid);

                    workoutDao.save(workout);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Помилка обробки запиту в базі даних", e);
        }

        // Захист від повторного відправлення форми (PRG)
        response.sendRedirect(request.getContextPath() + "/workouts");
    }
}