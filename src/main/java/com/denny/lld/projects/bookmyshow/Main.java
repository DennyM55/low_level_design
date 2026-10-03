package com.denny.lld.projects.bookmyshow;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Movie movie = new Movie("M1", "Interstellar", "Sci-Fi", 169);

        Show show = new Show("SH1", movie, "SC1", LocalDateTime.now().plusHours(2));
        show.addSeat(new Seat("A1", "A", 1, SeatCategory.SILVER, 150.0));
        show.addSeat(new Seat("A2", "A", 2, SeatCategory.SILVER, 150.0));
        show.addSeat(new Seat("B1", "B", 1, SeatCategory.GOLD, 250.0));
        show.addSeat(new Seat("B2", "B", 2, SeatCategory.GOLD, 250.0));
        show.addSeat(new Seat("C1", "C", 1, SeatCategory.PLATINUM, 400.0));

        System.out.println("Show: " + movie.getTitle() + " at " + show.getStartTime());
        System.out.println("Available seats:");
        show.getSeats().values().forEach(s -> System.out.println("  " + s));

        User user = new User("U1", "Priya", "priya@example.com");
        BookingService service = new BookingService();

        System.out.println();
        Booking booking = service.bookSeats(user, show, List.of("B1", "B2"));

        System.out.println();
        System.out.println("Seat status after booking:");
        show.getSeats().values().forEach(s -> System.out.println("  " + s));
    }
}
