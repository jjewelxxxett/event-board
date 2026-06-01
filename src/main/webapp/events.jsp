<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Event, java.util.List" %>
<html>
<head>
    <title>Дошка оголошень</title>
</head>
<body>
    <h1>Список заходів</h1>
    <table border="1">
        <tr>
            <th>Назва</th>
            <th>Вільні місця</th>
            <th>Дія</th>
        </tr>
        <%
            // Витягуємо список, який поклав туди сервлет
            List<Event> events = (List<Event>) request.getAttribute("eventsList");
            for (Event e : events) {
        %>
        <tr>
            <td><%= e.title %></td>
            <td><%= e.freeSeats %> / <%= e.maxSeats %></td>
            <td><a href="event?id=<%= e.id %>">Записатися</a></td>
        </tr>
        <% } %>
    </table>

    <hr>
    <h3>Додати нову подію</h3>
    <form action="events" method="POST">
        Назва: <input type="text" name="title"><br>
        Місць: <input type="number" name="maxSeats"><br>
        <button type="submit">Додати</button>
    </form>
</body>
</html>