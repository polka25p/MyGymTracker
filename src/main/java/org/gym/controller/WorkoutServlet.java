package org.gym.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gym.dao.WorkoutVisitDao;
import org.gym.dao.impl.WorkoutVisitDaoImpl;
import org.gym.model.WorkoutVisit;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/workouts")
public class WorkoutServlet extends HttpServlet {
    private final WorkoutVisitDao workoutDao = new WorkoutVisitDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<WorkoutVisit> workouts = workoutDao.getAllWorkouts();
            request.setAttribute("workouts", workouts);
            request.getRequestDispatcher("/workouts.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Помилка підключення до бази даних!", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("delete")) {
            String idParam = request.getParameter("id");
            int id = Integer.parseInt(idParam);
            try {
                workoutDao.delete(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect(request.getContextPath() + "/workouts");
        } else {
            String dateParam = request.getParameter("visitDate");
            LocalDate visitDate = LocalDate.parse(dateParam);
            String notes = request.getParameter("notes");
            boolean withTrainer = request.getParameter("withTrainer") != null;
            boolean trainerPaid = request.getParameter("trainerPaid") != null;

            WorkoutVisit workout = new WorkoutVisit();
            workout.setVisitDate(visitDate);
            workout.setNotes(notes);
            workout.setWithTrainer(withTrainer);
            workout.setTrainerPaid(trainerPaid);
            try {
                workoutDao.save(workout);
            } catch (SQLException e) {
                throw new ServletException(e);
            }

            response.sendRedirect(request.getContextPath() + "/workouts");
        }
    }
}