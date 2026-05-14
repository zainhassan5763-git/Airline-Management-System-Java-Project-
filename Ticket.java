package airline;

public class Ticket {
    private String    ticketId;
    private Passenger passenger;
    private Flight    flight;
    private int       seatsBooked;
    private double    totalAmount;

    public Ticket(String ticketId, Passenger passenger, Flight flight,
                  int seatsBooked, double totalAmount) {
        this.ticketId    = ticketId;
        this.passenger   = passenger;
        this.flight      = flight;
        this.seatsBooked = seatsBooked;
        this.totalAmount = totalAmount;
    }

    public String    getTicketId()    { return ticketId; }
    public Passenger getPassenger()   { return passenger; }
    public Flight    getFlight()      { return flight; }
    public int       getSeatsBooked() { return seatsBooked; }
    public double    getTotalAmount() { return totalAmount; }
}
