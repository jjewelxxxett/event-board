package repository;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EventRepository {
    private String url = "jdbc:postgresql://localhost:5432/event-board";
    private String user = "postgres";
    private String pass = "root";

    public EventRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        return DriverManager.getConnection(url, props);
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        // Запит рахує вільні місця як (max - кількість учасників)
        String sql = "SELECT e.id, e.title, e.event_date, e.max_seats, " +
                "(e.max_seats - (SELECT count(*) FROM participants WHERE event_id = e.id)) as free_seats " +
                "FROM events e";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Event e = new Event();
                e.id = rs.getInt("id");
                e.title = rs.getString("title");
                e.date = rs.getDate("event_date").toLocalDate();
                e.maxSeats = rs.getInt("max_seats");
                e.freeSeats = rs.getInt("free_seats");
                list.add(e);
            }
        } catch (SQLException e) {
            System.err.println("ПОМИЛКА БАЗИ (getAllEvents): " + e.getMessage());
        }
        return list;
    }

    public void createEvent(String title, int maxSeats) {
        String sql = "INSERT INTO events (title, event_date, max_seats) VALUES (?, CURRENT_DATE, ?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, title);
            st.setInt(2, maxSeats);
            st.executeUpdate();
            System.out.println("Подія додана: " + title);
        } catch (SQLException e) {
            System.err.println("ПОМИЛКА БАЗИ (createEvent): " + e.getMessage());
        }
    }

    // ВИПРАВЛЕНИЙ МЕТОД: тепер він бачить вільні місця
    public Event getEventById(int id) {
        String sql = "SELECT e.*, " +
                "(e.max_seats - (SELECT count(*) FROM participants WHERE event_id = e.id)) as free " +
                "FROM events e WHERE e.id = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.id = rs.getInt("id");
                    event.title = rs.getString("title");
                    event.date = rs.getDate("event_date").toLocalDate();
                    event.maxSeats = rs.getInt("max_seats");
                    event.freeSeats = rs.getInt("free"); // Встановлюємо реальну кількість
                    return event;
                }
            }
        } catch (SQLException e) {
            System.err.println("ПОМИЛКА БАЗИ (getEventById): " + e.getMessage());
        }
        return null;
    }

    public void addParticipant(int eventId, String name, String email) {
        String sql = "INSERT INTO participants (event_id, student_name, student_email) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventId);
            st.setString(2, name);
            st.setString(3, email);
            st.executeUpdate();
            System.out.println("Учасник доданий: " + name);
        } catch (SQLException e) {
            System.err.println("ПОМИЛКА БАЗИ (addParticipant): " + e.getMessage());
        }
    }
}