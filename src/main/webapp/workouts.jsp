<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>My Gym Tracker - Тренування</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f8fafc;
      color: #1e293b;
      margin: 0;
      padding: 40px 20px;
    }

    .main-wrapper {
      max-width: 900px;
      margin: 0 auto;
    }

    .app-header {
      text-align: center;
      margin-bottom: 35px;
    }

    .app-header h2 {
      color: #0f172a;
      font-size: 2rem;
      margin: 0 0 8px 0;
      font-weight: 700;
    }

    .app-header p {
      color: #64748b;
      margin: 0;
      font-size: 1rem;
    }

    .card {
      background: #ffffff;
      padding: 30px;
      border-radius: 16px;
      box-shadow: 0 4px 20px rgba(15, 23, 42, 0.04);
      margin-bottom: 35px;
      border: 1px solid #e2e8f0;
    }

    .card-title {
      font-size: 1.25rem;
      color: #0f172a;
      margin-top: 0;
      margin-bottom: 25px;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 8px;
      font-weight: 500;
      color: #334155;
      font-size: 0.9rem;
    }

    .form-control {
      width: 100%;
      padding: 12px 16px;
      font-size: 0.95rem;
      border: 1px solid #cbd5e1;
      border-radius: 10px;
      background-color: #fff;
      color: #1e293b;
      box-sizing: border-box;
      transition: all 0.2s;
      font-family: inherit;
    }

    .form-control:focus {
      outline: none;
      border-color: #3b82f6;
      box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.12);
    }

    textarea.form-control {
      height: 90px;
      min-height: 60px;
      max-height: 250px;
      resize: vertical;
    }

    .checkbox-row {
      display: flex;
      gap: 20px;
      margin-bottom: 25px;
    }

    .checkbox-group {
      display: flex;
      align-items: center;
      cursor: pointer;
      padding: 10px 14px;
      background-color: #f8fafc;
      border-radius: 10px;
      border: 1px solid #f1f5f9;
      flex: 1;
      transition: background-color 0.2s;
    }

    .checkbox-group:hover {
      background-color: #f1f5f9;
    }

    .checkbox-group input[type="checkbox"] {
      width: 18px;
      height: 18px;
      margin-right: 12px;
      cursor: pointer;
      accent-color: #3b82f6;
    }

    .checkbox-group label {
      margin-bottom: 0;
      font-weight: 500;
      color: #475569;
      cursor: pointer;
      user-select: none;
      font-size: 0.9rem;
    }

    .btn-submit {
      display: block;
      width: 100%;
      padding: 14px;
      font-size: 1rem;
      font-weight: 600;
      color: #ffffff;
      background-color: #3b82f6;
      border: none;
      border-radius: 10px;
      cursor: pointer;
      transition: all 0.2s;
      box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
    }

    .btn-submit:hover {
      background-color: #2563eb;
      box-shadow: 0 6px 16px rgba(59, 130, 246, 0.3);
    }

    .btn-submit:active {
      transform: scale(0.99);
    }

    .table-responsive {
      width: 100%;
      overflow-x: auto;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      text-align: left;
    }

    th {
      background-color: #f8fafc;
      color: #64748b;
      font-weight: 600;
      font-size: 0.85rem;
      text-transform: uppercase;
      letter-spacing: 0.05em;
      padding: 14px 18px;
      border-bottom: 1px solid #e2e8f0;
    }

    td {
      padding: 16px 18px;
      border-bottom: 1px solid #f1f5f9;
      font-size: 0.95rem;
      color: #334155;
      vertical-align: middle;
    }

    tr:last-child td {
      border-bottom: none;
    }

    tr:hover td {
      background-color: #fafafa;
    }

    .badge {
      display: inline-flex;
      align-items: center;
      padding: 4px 10px;
      font-size: 0.8rem;
      font-weight: 600;
      border-radius: 6px;
    }

    .badge-success {
      background-color: #dcfce7;
      color: #15803d;
    }

    .badge-secondary {
      background-color: #f1f5f9;
      color: #64748b;
    }

    .btn-delete {
      background-color: transparent;
      color: #ef4444;
      border: 1px solid #fee2e2;
      padding: 6px 12px;
      border-radius: 8px;
      cursor: pointer;
      font-size: 0.85rem;
      font-weight: 500;
      transition: all 0.2s;
      background-color: #fef2f2;
    }

    .btn-delete:hover {
      background-color: #ef4444;
      color: white;
      border-color: #ef4444;
    }

    .delete-form {
      margin: 0;
      padding: 0;
    }

    .empty-state {
      text-align: center;
      color: #64748b;
      padding: 30px 0;
      font-style: italic;
    }
  </style>
</head>
<body>

<div class="main-wrapper">

  <div class="app-header">
    <h2>🏋️‍♂️ MyGymTracker</h2>
    <p>Ваш персональний щоденник залікових тренувань</p>
  </div>

  <div class="card">
    <div class="card-title">📝 Додати нове тренування</div>

    <form action="${pageContext.request.contextPath}/workouts" method="POST">
      <input type="hidden" name="action" value="add">

      <div class="form-group">
        <label for="workoutDate">Дата тренування</label>
        <input type="date" id="workoutDate" name="visitDate" class="form-control" required>
      </div>

      <div class="form-group">
        <label for="workoutNotes">Нотатки / План тренування</label>
        <textarea id="workoutNotes" name="notes" class="form-control"
                  placeholder="Вставте план від Gemini або розпишіть вправи (наприклад: Присідання 4х10, Жим 3х12)..."></textarea>
      </div>

      <div class="checkbox-row">
        <div class="checkbox-group">
          <input type="checkbox" id="withTrainer" name="withTrainer" value="true">
          <label for="withTrainer">🧑‍🏫 З тренером</label>
        </div>

        <div class="checkbox-group">
          <input type="checkbox" id="trainerPaid" name="trainerPaid" value="true">
          <label for="trainerPaid">💵 Оплачено тренеру</label>
        </div>
      </div>

      <button type="submit" class="btn-submit">Зберегти тренування</button>
    </form>
  </div>

  <div class="card">
    <div class="card-title">📊 Історія активностей</div>

    <div class="table-responsive">
      <c:choose>
        <c:when test="${empty workouts}">
          <div class="empty-state">
            <p>Тренувань поки немає. Час завітати до залу! 💪</p>
          </div>
        </c:when>
        <c:otherwise>
          <table>
            <thead>
            <tr>
              <th>Дата</th>
              <th>План / Нотатки</th>
              <th>Тренер</th>
              <th>Оплата</th>
              <th style="text-align: right;">Дії</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="workout" items="${workouts}">
              <tr>
                <td style="font-weight: 600; white-space: nowrap;">${workout.visitDate}</td>
                <td style="color: #475569;">${workout.notes}</td>
                <td>
                                        <span class="badge ${workout.withTrainer ? 'badge-success' : 'badge-secondary'}">
                                            ${workout.withTrainer ? 'Так' : 'Ні'}
                                        </span>
                </td>
                <td>
                                        <span class="badge ${workout.trainerPaid ? 'badge-success' : 'badge-secondary'}">
                                            ${workout.trainerPaid ? 'Оплачено' : 'Ні'}
                                        </span>
                </td>
                <td style="text-align: right;">
                  <form action="workouts" method="POST" class="delete-form" onsubmit="return confirm('Ви впевнені, що хочете видалити це тренування?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${workout.id}">
                    <button type="submit" class="btn-delete">Видалити</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </div>
  </div>

</div>

</body>
</html>