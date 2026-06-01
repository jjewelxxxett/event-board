package service;

import model.Event;
import repository.EventRepository;

public class EventService {
    private EventRepository repository;


    public EventService(EventRepository repository) {
        this.repository = repository;
    }


    public boolean registerStudent(int eventId, String name, String email) {

        Event event = repository.getEventById(eventId);


        if (event.freeSeats <= 0) {
            return false;
        }

        repository.addParticipant(eventId, name, email);
        return true;
    }
}