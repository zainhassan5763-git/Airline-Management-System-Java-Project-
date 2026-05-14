package airline;

import javax.swing.*;
import java.awt.*;

public class SearchFlight_GUI extends JInternalFrame {

    private JTextField txtFlightId;
    private JTextArea  txtResult;
    private JButton    btnSearch, btnClose;

    public SearchFlight_GUI() {
        super("Search Flight", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(460, 400));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT);
        JLabel title = new JLabel("Search Flight");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 28, 10, 28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        txtFlightId = UITheme.styledField();
        txtFlightId.setPreferredSize(new Dimension(380, 36));

        g.gridx = 0; g.gridy = 0; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Enter Flight ID (e.g. FL001)"), g);
        g.gridy = 1; g.insets = new Insets(0, 0, 12, 0);
        form.add(txtFlightId, g);

        btnSearch = UITheme.primaryButton("Search");
        g.gridy = 2; g.insets = new Insets(0, 0, 16, 0);
        form.add(btnSearch, g);

        g.gridy = 3; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Result"), g);

        txtResult = UITheme.styledTextArea();
        JScrollPane scroll = new JScrollPane(txtResult);
        UITheme.styleScrollPane(scroll);
        scroll.setPreferredSize(new Dimension(380, 150));
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
        btnSearch.addActionListener(e -> search());
        btnClose.addActionListener(e -> dispose());
        txtFlightId.addActionListener(e -> search());
    }

    private void search() {
        String id = txtFlightId.getText().trim();
        if (id.isEmpty()) { txtResult.setText("Please enter a Flight ID."); return; }
        Flight f = AppData.flightData.searchById(id);
        if (f == null) { txtResult.setText("No flight found with ID: " + id); return; }
        txtResult.setText(
                "Flight ID   : " + f.getFlightId()   + "\n" +
                "Source      : " + f.getSource()      + "\n" +
                "Destination : " + f.getDestination() + "\n" +
                "Seats Left  : " + f.getSeats()       + "\n" +
                "Price (PKR) : " + String.format("%.0f", f.getCharges())
        );
    }
}
