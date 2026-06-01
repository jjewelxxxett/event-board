<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Event, java.util.List" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Події | Event Board</title>
    <style>
        :root {
            --primary: #4f46e5;
            --bg: #f8fafc;
            --text: #1e293b;
        }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: var(--bg); color: var(--text); padding: 40px; margin: 0; }
        .container { max-width: 900px; margin: auto; }
        h2 { font-weight: 600; margin-bottom: 25px; color: #0f172a; }

        /* Таблиця */
        .table-container { background: white; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); overflow: hidden; margin-bottom: 30px; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #f1f5f9; padding: 15px; text-align: left; font-size: 0.875rem; text-transform: uppercase; letter-spacing: 0.05em; }
        td { padding: 15px; border-top: 1px solid #e2e8f0; }
        tr:hover { background-color: #f8fafc; }

        /* Кнопки та посилання */
        .btn-register { color: white; background: var(--primary); padding: 8px 16px; border-radius: 6px; text-decoration: none; font-size: 14px; transition: 0.2s; }
        .btn-register:hover { background: #4338ca; }

        /* Картка форми */
        .card { background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: 500; font-size: 14px; }
        input { width: 100%; padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; box-sizing: border-box; outline: none; transition: 0.3s; }
        input:focus { border-color: var(--primary); ring: 2px solid #c7d2fe; }
        .btn-submit { width: 100%; background: #10b981; color: white; border: none; padding: 12px; border-radius: 6px; font-weight: 600; cursor: pointer; margin-top: 10px; }
        .btn-submit:hover { background: #059669; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🚀 Список активних заходів</h2>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Назва заходу</th>
                        <th>Вільні місця</th>
                        <th>Дія</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Event> events = (List<Event>) request.getAttribute("eventsList");
                        if (events != null && !events.isEmpty()) {
                            for (Event e : events) {
                    %>
                        <tr>
                            <td><strong><%= e.title %></strong></td>
                            <td><span style="color: <%= e.freeSeats > 0 ? "#059669" : "#dc2626" %>">
                                <%= e.freeSeats %></span> / <%= e.maxSeats %></td>
                            <td><a href="event?id=<%= e.id %>" class="btn-register">Записатися</a></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr><td colspan="3" style="text-align: center; color: #94a3b8; padding: 30px;">Поки що ніхто не створив жодного заходу...</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h3>🆕 Створити нову подію</h3>
            <form action="events" method="POST">
                <div class="form-group">
                    <label>Назва події</label>
                    <input type="text" name="title" placeholder="Наприклад: Workshop Java Core" required>
                </div>
                <div class="form-group">
                    <label>Кількість місць</label>
                    <input type="number" name="maxSeats" min="1" placeholder="10" required>
                </div>
                <button type="submit" class="btn-submit">Додати до списку</button>
            </form>
        </div>
    </div>
</body>
</html>