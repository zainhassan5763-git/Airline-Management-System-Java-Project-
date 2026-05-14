package airline;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TicketSearch_GUI extends JInternalFrame {

    private JTextField  txtTicketId;
    private JButton     btnSearch, btnClose;
    private JPanel      ticketCard;

    public TicketSearch_GUI() {
        super("Search Ticket", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(560, 540));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT_AMBER);
        JLabel title = new JLabel("🔍 Search Ticket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        // Search bar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        searchBar.setBackground(UITheme.BG_CARD);
        searchBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER));
        txtTicketId = UITheme.styledField();
        txtTicketId.setPreferredSize(new Dimension(260, 36));
        btnSearch = UITheme.primaryButton("Search");
        JLabel lbl = UITheme.fieldLabel("Ticket ID:");
        searchBar.add(lbl);
        searchBar.add(txtTicketId);
        searchBar.add(btnSearch);
        root.add(searchBar, BorderLayout.NORTH);


        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(searchBar, BorderLayout.SOUTH);
        root.add(topWrapper, BorderLayout.NORTH);


        ticketCard = new JPanel(new BorderLayout());
        ticketCard.setBackground(UITheme.BG_DARK);
        ticketCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel prompt = new JLabel("Enter a Ticket ID and press Search", SwingConstants.CENTER);
        prompt.setForeground(UITheme.TEXT_MUTED);
        prompt.setFont(UITheme.FONT_LABEL);
        ticketCard.add(prompt, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(ticketCard);
        UITheme.styleScrollPane(scrollPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnClose = UITheme.ghostButton("Close");
        btnPanel.add(btnClose);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);
        btnSearch.addActionListener(e -> search());
        btnClose.addActionListener(e -> dispose());
        txtTicketId.addActionListener(e -> search());
    }

    private void search() {
        String id = txtTicketId.getText().trim();
        ticketCard.removeAll();

        if (id.isEmpty()) {
            showMsg("Please enter a Ticket ID.");
            return;
        }

        Ticket t = AppData.ticketData.searchById(id);
        if (t == null) {
            showMsg("No ticket found with ID: " + id);
            return;
        }

        // Build boarding-pass style card
        JPanel card = buildBoardingPass(t);
        ticketCard.setBackground(UITheme.BG_DARK);
        ticketCard.add(card, BorderLayout.CENTER);
        ticketCard.revalidate();
        ticketCard.repaint();
    }

    private JPanel buildBoardingPass(Ticket t) {
        // Outer wrapper with padding
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG_DARK);

        // Card with blue header + white body (mimics the boarding pass image)
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 40, 80));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(480, 320));

        // ── Top blue header ──────────────────────────────────────────────────
        JPanel cardHeader = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 60, 160),
                        getWidth(), 0, new Color(99, 102, 241));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight() + 16, 16, 16);
            }
        };
        cardHeader.setOpaque(false);
        cardHeader.setPreferredSize(new Dimension(480, 70));
        cardHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel headerTitle = new JLabel("✈  AIRLINE TICKET   |   BOARDING PASS");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        headerTitle.setForeground(Color.WHITE);

        JLabel ticketNum = new JLabel(t.getTicketId());
        ticketNum.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ticketNum.setForeground(new Color(200, 210, 255));
        ticketNum.setHorizontalAlignment(SwingConstants.RIGHT);

        cardHeader.add(headerTitle, BorderLayout.WEST);
        cardHeader.add(ticketNum, BorderLayout.EAST);
        card.add(cardHeader, BorderLayout.NORTH);

        // ── Body: left info + divider + right mini-stub ──────────────────────
        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        // Left: main ticket info
        JPanel left = new JPanel(new GridLayout(0, 2, 16, 12));
        left.setOpaque(false);

        addField(left, "NAME OF PASSENGER", t.getPassenger().getName());
        addField(left, "FLIGHT",            t.getFlight().getFlightId());
        addField(left, "FROM",              t.getFlight().getSource());
        addField(left, "TO",                t.getFlight().getDestination());
        addField(left, "SEATS BOOKED",      String.valueOf(t.getSeatsBooked()));
        addField(left, "TOTAL (PKR)",       String.format("%.0f", t.getTotalAmount()));

        // Dotted vertical divider
        JPanel divider = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(100, 110, 180));
                float[] dash = {4f, 4f};
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1f, dash, 0f));
                g2.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            }
        };
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(20, 200));

        // Right: boarding stub
        JPanel right = new JPanel(new GridLayout(0, 1, 0, 10));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(130, 200));

        JLabel stubTitle = new JLabel("BOARDING PASS");
        stubTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        stubTitle.setForeground(new Color(180, 190, 255));
        right.add(stubTitle);

        addStubField(right, "NAME",   t.getPassenger().getName());
        addStubField(right, "FROM",   t.getFlight().getSource());
        addStubField(right, "TO",     t.getFlight().getDestination());
        addStubField(right, "PASS ID", t.getPassenger().getPassengerId());

        body.add(left,    BorderLayout.CENTER);
        body.add(divider, BorderLayout.EAST);

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        rightWrapper.add(right, BorderLayout.CENTER);
        body.add(rightWrapper, BorderLayout.EAST);

        card.add(body, BorderLayout.CENTER);

        // Barcode-style footer
        JPanel footer = buildBarcodeFooter();
        card.add(footer, BorderLayout.SOUTH);

        wrapper.add(card);
        return wrapper;
    }

    private void addField(JPanel p, String label, String value) {
        JPanel cell = new JPanel(new BorderLayout(0, 2));
        cell.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        lbl.setForeground(new Color(160, 170, 220));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(Color.WHITE);
        // Underline bar
        JPanel bar = new JPanel();
        bar.setBackground(new Color(70, 80, 140));
        bar.setPreferredSize(new Dimension(100, 2));
        cell.add(lbl, BorderLayout.NORTH);
        cell.add(val, BorderLayout.CENTER);
        cell.add(bar, BorderLayout.SOUTH);
        p.add(cell);
    }

    private void addStubField(JPanel p, String label, String value) {
        JPanel cell = new JPanel(new BorderLayout(0, 1));
        cell.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        lbl.setForeground(new Color(160, 170, 220));
        JLabel val = new JLabel(value.length() > 14 ? value.substring(0, 14) + "…" : value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 11));
        val.setForeground(Color.WHITE);
        cell.add(lbl, BorderLayout.NORTH);
        cell.add(val, BorderLayout.CENTER);
        p.add(cell);
    }

    private JPanel buildBarcodeFooter() {
        JPanel fp = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(18, 25, 60));
                g.fillRect(0, 0, getWidth(), getHeight());
                // Draw barcode lines
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.WHITE);
                int x = 60; int y = 10; int h = 28;
                int[] widths = {2,1,3,1,2,3,1,2,1,3,2,1,2,3,1,2,1,3,2,1,2,3,1,2,1,2,3,1};
                boolean black = true;
                for (int w : widths) {
                    if (black) g2.fillRect(x, y, w, h);
                    x += w + 1;
                    black = !black;
                }
            }
        };
        fp.setOpaque(false);
        fp.setPreferredSize(new Dimension(480, 48));
        return fp;
    }

    private void showMsg(String msg) {
        JLabel lbl = new JLabel(msg, SwingConstants.CENTER);
        lbl.setForeground(UITheme.TEXT_MUTED);
        lbl.setFont(UITheme.FONT_LABEL);
        ticketCard.setBackground(UITheme.BG_DARK);
        ticketCard.add(lbl, BorderLayout.CENTER);
        ticketCard.revalidate();
        ticketCard.repaint();
    }
}
