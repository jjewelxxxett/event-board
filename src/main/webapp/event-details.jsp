<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Event" %>
<html>
<head>
    <title>Деталі заходу</title>
</head>
<body>
    <% Event e = (Event) request.getAttribute("event"); %>

    <h1>Захід: <%= e.title %></h1>
    <p>Максимально місць: <%= e.maxSeats %></p>
    <p>Вільних місць зараз: <b><%= e.freeSeats %></b></p>

    <hr>
    <h3>Записатися на цей захід</h3>
    <form action="event" method="POST">
        <input type="hidden" name="eventId" value="<%= e.id %>">

        Ваше ім'я: <input type="text" name="studentName" required><br><br>
        Ваш Email: <input type="email" name="studentEmail" required><br><br>

        <button type="submit">Підтвердити реєстрацію</button>
    </form>

    <br>
    <a href="events">Повернутися до списку всіх заходів</a>
</body>
</html>