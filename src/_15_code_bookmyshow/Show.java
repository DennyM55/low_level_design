package _15_code_bookmyshow;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Show {

    private final String showId;
    private final Movie movie;
    private final String screenId;
    private final LocalDateTime startTime;
    private final Map<String, Seat> seats;

    public Show(String showId, Movie movie, String screenId, LocalDateTime startTime) {
        this.showId = showId;
        this.movie = movie;
        this.screenId = screenId;
        this.startTime = startTime;
        this.seats = new LinkedHashMap<>();
    }

    public void addSeat(Seat seat) {
        seats.put(seat.getSeatId(), seat);
    }

    public synchronized boolean lockSeats(java.util.List<String> seatIds) {
        for (String id : seatIds) {
            Seat seat = seats.get(id);
            if (seat == null || seat.getStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        for (String id : seatIds) {
            seats.get(id).setStatus(SeatStatus.LOCKED);
        }
        return true;
    }

    public synchronized void confirmSeats(java.util.List<String> seatIds) {
        for (String id : seatIds) {
            Seat seat = seats.get(id);
            if (seat != null) seat.setStatus(SeatStatus.BOOKED);
        }
    }

    public synchronized void releaseSeats(java.util.List<String> seatIds) {
        for (String id : seatIds) {
            Seat seat = seats.get(id);
            if (seat != null && seat.getStatus() == SeatStatus.LOCKED) {
                seat.setStatus(SeatStatus.AVAILABLE);
            }
        }
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public String getScreenId() { return screenId; }
    public LocalDateTime getStartTime() { return startTime; }
    public Map<String, Seat> getSeats() { return seats; }
}
