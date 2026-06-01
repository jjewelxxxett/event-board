package model;

import java.time.LocalDate;

public class Event {
    public int id;
    public String title;
    public LocalDate date;
    public int maxSeats;
    public int freeSeats;


    public Event() {}


    public Event(int id, String title, LocalDate date, int maxSeats) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.maxSeats = maxSeats;
    }
}