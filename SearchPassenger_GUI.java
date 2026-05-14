package airline;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SearchPassenger_GUI extends JInternalFrame {

    private JTextField txtPassengerId;
    private JTextArea  txtResult;
    private JLabel     picLabel;
    private JButton    btnSearch, btnClose;

    public SearchPassenger_GUI() {
        super("Search Passenger", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(500, 500));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT);
        JLabel title = new JLabel("🔍 Search Passenger");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);


        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(20, 28, 10, 28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        txtPassengerId = UITheme.styledField();
        txtPassengerId.setPreferredSize(new Dimension(380, 36));

        g.gridx = 0; g.gridy = 0; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Enter Passenger ID (e.g. PS001)"), g);
        g.gridy = 1; g.insets = new Insets(0, 0, 12, 0);
        form.add(txtPassengerId, g);

        btnSearch = UITheme.primaryButton("Search");
        g.gridy = 2; g.insets = new Insets(0, 0, 16, 0);
        form.add(btnSearch, g);


        g.gridy = 3; g.insets = new Insets(0, 0, 6, 0);
        form.add(UITheme.fieldLabel("Passenger Photo"), g);

        picLabel = new JLabel();
        picLabel.setPreferredSize(new Dimension(110, 120));
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        picLabel.setVerticalAlignment(SwingConstants.CENTER);
        picLabel.setOpaque(true);
        picLabel.setBackground(UITheme.BG_INPUT);
        picLabel.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        picLabel.setIcon(noPhotoIcon());
        g.gridy = 4; g.insets = new Insets(0, 0, 14, 0);
        form.add(picLabel, g);


        g.gridy = 5; g.insets = new Insets(0, 0, 4, 0);
        form.add(UITheme.fieldLabel("Passenger Details"), g);

        txtResult = UITheme.styledTextArea();
        JScrollPane resultScroll = new JScrollPane(txtResult);
        UITheme.styleScrollPane(resultScroll);
        resultScroll.setPreferredSize(new Dimension(380, 150));
        g.gridy = 6; g.insets = new Insets(0, 0, 0, 0);
        form.add(resultScroll, g);


        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UITheme.BG_CARD);
        scrollPane.getViewport().setBackground(UITheme.BG_CARD);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        UITheme.styleScrollPane(scrollPane);
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
        txtPassengerId.addActionListener(e -> search());
    }

    private void search() {
        String id = txtPassengerId.getText().trim();
        if (id.isEmpty()) { txtResult.setText("Please enter a Passenger ID."); return; }

        Passenger p = AppData.passengerData.searchById(id);
        if (p == null) {
            txtResult.setText("No passenger found with ID: " + id);
            picLabel.setIcon(noPhotoIcon());
            return;
        }


        txtResult.setText(
                "Passenger ID : " + p.getPassengerId()   + "\n" +
                "Name         : " + p.getName()           + "\n" +
                "Username     : " + p.getUserName()       + "\n" +
                "CNIC         : " + p.getCnic()           + "\n" +
                "Passport     : " + p.getPassportId()     + "\n" +
                "Address      : " + p.getAddress()        + "\n" +
                "Gender       : " + p.getGender()         + "\n" +
                "Contact      : " + p.getContactNumber()
        );


        String picPath = p.getPicturePath();
        if (picPath != null && !picPath.isEmpty()) {
            try {
                Image img = ImageIO.read(new File(picPath));
                if (img != null) {
                    picLabel.setIcon(new ImageIcon(img.getScaledInstance(110, 120, Image.SCALE_SMOOTH)));
                    return;
                }
            } catch (Exception ex) { /* fall through */ }
        }
        picLabel.setIcon(noPhotoIcon());
    }

    private ImageIcon noPhotoIcon() {
        BufferedImage img = new BufferedImage(110, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(UITheme.BG_INPUT);
        g2.fillRect(0, 0, 110, 120);
        g2.setColor(UITheme.TEXT_MUTED);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        g2.drawString("👤", 30, 70);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        g2.drawString("No Photo", 25, 100);
        g2.dispose();
        return new ImageIcon(img);
    }
}
