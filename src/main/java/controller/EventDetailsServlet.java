package controller;

import repository.EventRepository;
import model.Event;
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
}