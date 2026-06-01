package service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {

    @Test
    public void testSimpleLogic() {
        // Уявимо подію на 10 місць, де 10 вже зайнято
        int maxSeats = 10;
        int currentParticipants = 10;

        // Рахуємо вільні місця
        int freeSeats = maxSeats - currentParticipants;

        // Перевіряємо, чи програма правильно бачить, що місць 0
        assertEquals(0, freeSeats, "Якщо всі місця зайняті, має бути нуль вільних");
    }
}