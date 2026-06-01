package controller;

import repository.EventRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private EventRepository repository;

    @Override
    public void init() {

        this.repository = new EventRepository();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("eventsList", repository.getAllEvents());

        req.getRequestDispatcher("/events.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        int maxSeats = Integer.parseInt(req.getParameter("maxSeats"));


        repository.createEvent(title, maxSeats);


        resp.sendRedirect(req.getContextPath() + "/events");
    }
}