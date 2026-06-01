<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Event" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Реєстрація | Event Board</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f1f5f9; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); width: 100%; max-width: 400px; }
        h2 { margin-top: 0; color: #1e293b; font-size: 24px; }
        .info { background: #eff6ff; padding: 15px; border-radius: 8px; margin-bottom: 25px; color: #1e40af; font-size: 14px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 500; color: #475569; }
        input { width: 100%; padding: 12px; border: 1px solid #e2e8f0; border-radius: 8px; box-sizing: border-box; outline: none; }
        input:focus { border-color: #3b82f6; }
        .btn-confirm { width: 100%; background: #3b82f6; color: white; border: none; padding: 14px; border-radius: 8px; font-weight: 600; cursor: pointer; font-size: 16px; }
        .btn-confirm:hover { background: #2563eb; }
        .back-link { display: block; text-align: center; margin-top: 20px; color: #64748b; text-decoration: none; font-size: 14px; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="card">
        <% Event e = (Event) request.getAttribute("event"); %>
        <h2>📝 Реєстрація</h2>
        <div class="info">
            <strong>Захід:</strong> <%= e.title %><br>
            <strong>Вільних місць:</strong> <%= e.freeSeats %>
        </div>

        <form action="event" method="POST">
            <input type="hidden" name="eventId" value="<%= e.id %>">

            <div class="form-group">
                <label>Ваше повне ім'я</label>
                <input type="text" name="studentName" placeholder="Іван Іванов" required>
            </div>

            <div class="form-group">
                <label>Електронна пошта</label>
                <input type="email" name="studentEmail" placeholder="example@gmail.com" required>
            </div>

            <button type="submit" class="btn-confirm">Підтвердити участь</button>
        </form>

        <a href="events" class="back-link">← Скасувати та повернутися</a>
    </div>
</body>
</html>