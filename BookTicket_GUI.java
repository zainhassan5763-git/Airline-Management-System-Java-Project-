package airline;

import javax.swing.*;
import java.awt.*;

public class BookTicket_GUI extends JInternalFrame {

    private JTextField txtPassenger, txtFlightId, txtSeats;
    private JTextArea  txtSummary;
    private JButton    btnBook, btnClose;

    public BookTicket_GUI() {
        super("Book Ticket", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(480, 520));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT_AMBER);
        JLabel title = new JLabel("🎫 Book Ticket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 28, 10, 28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        txtPassenger = UITheme.styledField();
        txtPassenger.setEditable(false);
        txtPassenger.setForeground(UITheme.TEXT_MUTED);
        if (AppData.currentPassenger != null) txtPassenger.setText(AppData.currentPassenger.getName());
        txtFlightId = UITheme.styledField();
        txtSeats    = UITheme.styledField();

        addRow(form, g, 0, "Passenger",      txtPassenger);
        addRow(form, g, 1, "Flight ID",       txtFlightId);
        addRow(form, g, 2, "Number of Seats", txtSeats);

        g.gridy = 7; g.insets = new Insets(16, 0, 4, 0);
        form.add(UITheme.fieldLabel("Booking Summary"), g);

        txtSummary = UITheme.styledTextArea();
        JScrollPane scroll = new JScrollPane(txtSummary);
        UITheme.styleScrollPane(scroll);
        scroll.setPreferredSize(new Dimension(390, 140));
        g.gridy = 8; g.insets = new Insets(0, 0, 0, 0);
        form.add(scroll, g);


        JScrollPane outerScroll = new JScrollPane(form);
        outerScroll.setBorder(null);
        outerScroll.setBackground(UITheme.BG_CARD);
        outerScroll.getViewport().setBackground(UITheme.BG_CARD);
        outerScroll.getVerticalScrollBar().setUnitIncrement(16);
        UITheme.styleScrollPane(outerScroll);
        root.add(outerScroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 14));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnClose = UITheme.ghostButton("Close");
        btnBook  = UITheme.primaryButton("Confirm Booking");
        btnPanel.add(btnClose); btnPanel.add(btnBook);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);
        btnBook.addActionListener(e -> book());
        btnClose.addActionListener(e -> dispose());
        txtFlightId.addActionListener(e -> txtSeats.requestFocus());
        txtSeats.addActionListener(e -> book());
    }

    private void addRow(JPanel p, GridBagConstraints g, int row, String label, JTextField field) {
        g.gridx = 0; g.gridy = row * 2; g.insets = new Insets(8, 0, 2, 0);
        p.add(UITheme.fieldLabel(label), g);
        g.gridy = row * 2 + 1; g.insets = new Insets(0, 0, 2, 0);
        field.setPreferredSize(new Dimension(390, 36));
        p.add(field, g);
    }

    private void book() {
        if (AppData.currentPassenger == null) {
            JOptionPane.showMessageDialog(this, "No passenger is logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String flightId = txtFlightId.getText().trim();
        if (flightId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Flight ID.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int seats;
        try {
            seats = Integer.parseInt(txtSeats.getText().trim());
            if (seats <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid seat count.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Flight f = AppData.flightData.searchById(flightId);
        if (f == null) {
            JOptionPane.showMessageDialog(this, "Flight '" + flightId + "' not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (f.getSeats() < seats) {
            JOptionPane.showMessageDialog(this, "Only " + f.getSeats() + " seat(s) available.", "Not Enough Seats", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double total = seats * f.getCharges();
        f.setSeats(f.getSeats() - seats);
        String ticketId = AppData.ticketData.generateTicketId();
        Ticket t = new Ticket(ticketId, AppData.currentPassenger, f, seats, total);
        AppData.ticketData.addTicket(t);

        txtSummary.setText(
                "Ticket ID    : " + ticketId + "\n" +
                "Passenger    : " + AppData.currentPassenger.getName() + "\n" +
                "Flight       : " + f.getFlightId() + "\n" +
                "Route        : " + f.getSource() + " → " + f.getDestination() + "\n" +
                "Seats Booked : " + seats + "\n" +
                "Total (PKR)  : " + String.format("%.0f", total)
        );
        JOptionPane.showMessageDialog(this,
                "<html><b>Booking Confirmed!</b><br>Ticket ID: <b>" + ticketId + "</b></html>",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
