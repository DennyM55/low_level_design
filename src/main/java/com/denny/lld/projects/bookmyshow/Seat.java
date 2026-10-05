package com.denny.lld.projects.bookmyshow;

public class Seat {

    private final String seatId;
    private final String row;
    private final int seatNumber;
    private final SeatCategory category;
    private SeatStatus status;
    private final double price;

    public Seat(String seatId, String row, int seatNumber, SeatCategory category, double price) {
        this.seatId = seatId;
        this.row = row;
        this.seatNumber = seatNumber;
        this.category = category;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getSeatId() { return seatId; }
    public String getRow() { return row; }
    public int getSeatNumber() { return seatNumber; }
    public SeatCategory getCategory() { return category; }
    public SeatStatus getStatus() { return status; }
    public double getPrice() { return price; }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return seatId + "(" + category + ", " + status + ", Rs." + price + ")";
    }
}
