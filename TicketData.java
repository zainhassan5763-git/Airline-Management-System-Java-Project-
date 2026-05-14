package airline;

import java.util.ArrayList;

public class TicketData {
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private int counter = 1;

    public String generateTicketId() {
        return "TK" + String.format("%03d", counter++);
    }

    public void addTicket(Ticket t) { tickets.add(t); }

    public Ticket searchById(String ticketId) {
        for (Ticket t : tickets)
            if (t.getTicketId().equalsIgnoreCase(ticketId)) return t;
        return null;
    }

    public ArrayList<Ticket> getTickets() { return tickets; }
}
