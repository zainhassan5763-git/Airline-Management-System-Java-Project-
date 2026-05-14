package airline;

public class Flight {
    private String flightId;
    private String source;
    private String destination;
    private int    seats;
    private double charges;

    public Flight(String flightId, String source, String destination, int seats, double charges) {
        this.flightId    = flightId;
        this.source      = source;
        this.destination = destination;
        this.seats       = seats;
        this.charges     = charges;
    }

    public String getFlightId()      { return flightId; }
    public String getSource()        { return source; }
    public String getDestination()   { return destination; }
    public int    getSeats()         { return seats; }
    public double getCharges()       { return charges; }
    public void   setSeats(int s)    { this.seats = s; }

    @Override
    public String toString() {
        return String.format("%-8s %-14s -> %-14s Seats: %3d  Price: PKR %.0f",
                flightId, source, destination, seats, charges);
    }
}
