package controller;

import repository.EventRepository;
import model.Event;
import service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/event")
public class EventDetailsServlet extends HttpServlet {
    private EventRepository repository;

    @Override
    public void init() {
        this.repository = new EventRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Event event = repository.getEventById(id);
        req.setAttribute("event", event);
        req.getRequestDispatcher("/event-details.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Отримуємо дані з полів форми
        int eventId = Integer.parseInt(req.getParameter("eventId"));
        String name = req.getParameter("studentName");
        String email = req.getParameter("studentEmail");

        // Викликаємо сервіс для перевірки і запису
        EventService eventService = new EventService(repository);
        boolean success = eventService.registerStudent(eventId, name, email);

        if (success) {
            // Якщо записали — йдемо на список подій
            resp.sendRedirect(req.getContextPath() + "/events");
        } else {
            // Якщо місць немає — помилка 400
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No more seats!");
        }
    }
}