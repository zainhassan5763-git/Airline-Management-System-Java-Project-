package airline;

import javax.swing.*;
import java.awt.*;

public class TicketCancel_GUI extends JInternalFrame {

    private JTextField txtTicketId;
    private JTextArea  txtResult;
    private JButton    btnCancel, btnClose;

    public TicketCancel_GUI() {
        super("Cancel Ticket", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(460, 400));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT_RED);
        JLabel title = new JLabel("Cancel Ticket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 28, 10, 28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        txtTicketId = UITheme.styledField();
        txtTicketId.setPreferredSize(new Dimension(380, 36));

        g.gridx = 0; g.gridy = 0; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Enter Ticket ID to cancel"), g);
        g.gridy = 1; g.insets = new Insets(0, 0, 12, 0);
        form.add(txtTicketId, g);

        btnCancel = UITheme.dangerButton("Cancel Ticket");
        g.gridy = 2; g.insets = new Insets(0, 0, 16, 0);
        form.add(btnCancel, g);

        g.gridy = 3; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Result"), g);

        txtResult = UITheme.styledTextArea();
        JScrollPane scroll = new JScrollPane(txtResult);
        UITheme.styleScrollPane(scroll);
        scroll.setPreferredSize(new Dimension(380, 130));
        g.gridy = 4; g.insets = new Insets(0, 0, 0, 0);
        form.add(scroll, g);

        JScrollPane outerScroll = new JScrollPane(form);
        outerScroll.setBorder(null);
        outerScroll.setBackground(UITheme.BG_CARD);
        outerScroll.getViewport().setBackground(UITheme.BG_CARD);
        outerScroll.getVerticalScrollBar().setUnitIncrement(16);
        UITheme.styleScrollPane(outerScroll);
        root.add(outerScroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnClose = UITheme.ghostButton("Close");
        btnPanel.add(btnClose);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);
        btnCancel.addActionListener(e -> cancelTicket());
        btnClose.addActionListener(e -> dispose());
        txtTicketId.addActionListener(e -> cancelTicket());
    }

    private void cancelTicket() {
        String id = txtTicketId.getText().trim();
        if (id.isEmpty()) { txtResult.setText("Please enter a Ticket ID."); return; }
        Ticket t = AppData.ticketData.searchById(id);
        if (t == null) { txtResult.setText("No ticket found with ID: " + id); return; }

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html>Are you sure you want to cancel ticket <b>" + id + "</b>?<br>" +
                "Passenger: " + t.getPassenger().getName() + "<br>" +
                "Flight: " + t.getFlight().getSource() + " to " + t.getFlight().getDestination() + "</html>",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        Flight f = t.getFlight();
        f.setSeats(f.getSeats() + t.getSeatsBooked());
        AppData.ticketData.getTickets().remove(t);

        txtResult.setText(
                "Ticket " + id + " cancelled.\n" +
                "Seats restored on flight " + f.getFlightId() + ".\n" +
                "Available seats now: " + f.getSeats()
        );
    }
}
