package airline;

import javax.swing.*;
import java.awt.*;

public class AddFlight_GUI extends JInternalFrame {

    private JTextField txtFlightId, txtSource, txtDestination, txtSeats, txtCharges;
    private JButton btnAdd, btnCancel;

    public AddFlight_GUI() {
        super("Add Flight", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(480, 440));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 16));
        header.setBackground(UITheme.ACCENT);
        JLabel title = new JLabel("✈ Add New Flight");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(24, 32, 10, 32));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 0, 6, 0);
        g.gridwidth = 1;

        txtFlightId    = UITheme.styledField();
        txtFlightId.setText(AppData.flightData.generateFlightId());
        txtFlightId.setEditable(false);
        txtFlightId.setForeground(UITheme.TEXT_MUTED);
        txtSource      = UITheme.styledField();
        txtDestination = UITheme.styledField();
        txtSeats       = UITheme.styledField();
        txtCharges     = UITheme.styledField();

        addRow(form, g, 0, "Flight ID",          txtFlightId);
        addRow(form, g, 1, "Source City",         txtSource);
        addRow(form, g, 2, "Destination",         txtDestination);
        addRow(form, g, 3, "Total Seats",         txtSeats);
        addRow(form, g, 4, "Ticket Price (PKR)",  txtCharges);


        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UITheme.BG_CARD);
        scrollPane.getViewport().setBackground(UITheme.BG_CARD);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        UITheme.styleScrollPane(scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 16));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnCancel = UITheme.ghostButton("Cancel");
        btnAdd    = UITheme.primaryButton("Add Flight");
        btnPanel.add(btnCancel);
        btnPanel.add(btnAdd);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);

        btnAdd.addActionListener(e -> addFlight());
        btnCancel.addActionListener(e -> dispose());
        txtSource.addActionListener(e -> txtDestination.requestFocus());
        txtDestination.addActionListener(e -> txtSeats.requestFocus());
        txtSeats.addActionListener(e -> txtCharges.requestFocus());
        txtCharges.addActionListener(e -> addFlight());
    }

    private void addRow(JPanel p, GridBagConstraints g, int row, String label, JTextField field) {
        g.gridx = 0; g.gridy = row * 2; g.insets = new Insets(10, 0, 2, 0);
        p.add(UITheme.fieldLabel(label), g);
        g.gridy = row * 2 + 1; g.insets = new Insets(0, 0, 4, 0);
        field.setPreferredSize(new Dimension(380, 36));
        p.add(field, g);
    }

    private void addFlight() {
        String source      = txtSource.getText().trim();
        String destination = txtDestination.getText().trim();
        String seatsText   = txtSeats.getText().trim();
        String chargesText = txtCharges.getText().trim();

        if (source.isEmpty() || destination.isEmpty() || seatsText.isEmpty() || chargesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int seats;
        try {
            seats = Integer.parseInt(seatsText);
            if (seats <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seats must be a positive whole number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            txtSeats.requestFocus(); return;
        }

        double charges;
        try {
            charges = Double.parseDouble(chargesText);
            if (charges < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Charges must be a valid positive number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            txtCharges.requestFocus(); return;
        }

        String id = txtFlightId.getText();
        Flight f  = new Flight(id, source, destination, seats, charges);
        AppData.flightData.addFlight(f);

        JOptionPane.showMessageDialog(this,
                "<html><b>Flight Added!</b><br>ID: " + id + " &nbsp;|&nbsp; " +
                source + " → " + destination +
                "<br>Seats: " + seats + " &nbsp;|&nbsp; Price: PKR " +
                String.format("%.0f", charges) + "</html>",
                "Success", JOptionPane.INFORMATION_MESSAGE);

        txtSource.setText(""); txtDestination.setText("");
        txtSeats.setText("");  txtCharges.setText("");
        txtFlightId.setText(AppData.flightData.generateFlightId());
        txtSource.requestFocus();
    }
}
