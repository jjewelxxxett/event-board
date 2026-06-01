package repository;

import model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    // Налаштування підключення до бази
    private String url = "jdbc:postgresql://localhost:5432/event_board";
    private String user = "postgres";
    private String pass = "root"; //

    // 1. Метод: Взяти список всіх подій
    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            String sql = "SELECT e.id, e.title, e.event_date, e.max_seats, " +
                    "(e.max_seats - (SELECT count(*) FROM participants WHERE event_id = e.id)) as free_seats " +
                    "FROM events e";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

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
            System.out.println("Помилка в getAllEvents: " + e.getMessage());
        }
        return list;
    }

    // 2. Метод: Створити нову подію
    public void createEvent(String title, int maxSeats) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO events (title, event_date, max_seats) VALUES (?, CURRENT_DATE, ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, title);
            st.setInt(2, maxSeats);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Помилка в createEvent: " + e.getMessage());
        }
    }

    // 3. Метод: Знайти подію по ID (треба для реєстрації)
    public Event getEventById(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM events WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt("id"), rs.getString("title"),
                        rs.getDate("event_date").toLocalDate(), rs.getInt("max_seats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Метод: Додати учасника
    public void addParticipant(int eventId, String name, String email) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO participants (event_id, student_name, student_email) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, eventId);
            st.setString(2, name);
            st.setString(3, email);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}