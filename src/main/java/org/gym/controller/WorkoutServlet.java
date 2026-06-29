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

/**
 * Controller servlet responsible for handling web requests related to workout visits
 * and gym statistics.
 * <p>
 * This servlet maps to the {@code /workouts} URL pattern. It coordinates interactions
 * between the presentation layer (JSP), data access objects (DAOs), and core business
 * service layers (calculating financial metrics, membership deadlines, and monthly logs).
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see HttpServlet
 * @see WorkoutVisitDao
 * @see GymService
 * @see MembershipService
 */

@WebServlet("/workouts")
public class WorkoutServlet extends HttpServlet {
    private final WorkoutVisitDao workoutDao = new WorkoutVisitDaoImpl();
    private final GymService gymService = new GymServiceImpl();
    private final MembershipService membershipService = new MembershipServiceImpl();
    private LocalDate nextPaymentDate = LocalDate.now().plusDays(14);

    /**
     * Handles HTTP {@code GET} requests to aggregate dashboard metrics and populate the UI layout.
     * <p>
     * Fetches all registered workout logs, updates subscription status based on
     * {@code nextPaymentDate}, triggers aggregations for financial debts or counts,
     * appends metrics into scope attributes, and forwards execution context directly to the
     * {@code /workouts.jsp} page.
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing reference state bindings.
     * @param response the {@link HttpServletResponse} carrying pipeline response dispatchers.
     * @throws ServletException if an underlying database failure cascades or forward steps collapse.
     * @throws IOException      if structural networking input/output pipelines throw validation faults.
     */

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

    /**
     * Handles HTTP {@code POST} requests executing target form mutations or state transitions.
     * <p>
     * Delegates commands according to the provided {@code action} request parameter:
     * <ul>
     * <li>{@code updateMembership}: Modifies the class level {@code nextPaymentDate} instance threshold.</li>
     * <li>{@code delete}: Drops a specified workout log based on its target ID parameter.</li>
     * <li>{@code pay}: Updates the financial cleared flat status targeting the trainer balance mapping.</li>
     * <li>{@code default (or empty fallback)}: Parses incoming raw text forms to register and save a completely new {@link WorkoutVisit}.</li>
     * </ul>
     * Once execution wraps up successfully, this method responds with an HTTP 302 redirect
     * pointing back straight to the core {@code /workouts} route endpoint (Post/Redirect/Get pattern).
     * </p>
     *
     * @param request  the {@link HttpServletRequest} holding action metadata and input data fields.
     * @param response the {@link HttpServletResponse} processing the redirection execution flow.
     * @throws ServletException if database integrity steps block mutations or trigger access rejections.
     * @throws IOException      if unexpected transmission blocks hit the redirect tracking sequence.
     */

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
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = (int) Long.parseLong(idStr);
                    workoutDao.delete(id);
                }

            } else if ("pay".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    workoutDao.updateTrainerPaidStatus(id);
                }

            } else {
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

        response.sendRedirect(request.getContextPath() + "/workouts");
    }
}