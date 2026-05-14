package airline;

import java.util.ArrayList;

public class PassengerData {
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private int counter = 1;

    public String generatePassengerId() {
        return "PS" + String.format("%03d", counter++);
    }

    public void addPassenger(Passenger p) { passengers.add(p); }

    public Passenger searchById(String id) {
        for (Passenger p : passengers)
            if (p.getPassengerId().equalsIgnoreCase(id)) return p;
        return null;
    }

    public Passenger loginByUsernameAndId(String username, String passengerId) {
        for (Passenger p : passengers)
            if (p.getUserName().equalsIgnoreCase(username) &&
                p.getPassengerId().equalsIgnoreCase(passengerId))
                return p;
        return null;
    }

    public ArrayList<Passenger> getAllPassengers() { return passengers; }
}
