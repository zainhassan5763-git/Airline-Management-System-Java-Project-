package airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class
AdminGUI extends JFrame {

    private static final String ADMIN_USERNAME  = "admin";
    private static final String ADMIN_PASS_HASH = hashOf("admin123");

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JLabel         lblError;

    public AdminGUI() { initUI(); }

    private void initUI() {
        setTitle("Airline Management — Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(UITheme.BG_DARK);
        root.setBorder(BorderFactory.createEmptyBorder(36, 60, 36, 60));
        setContentPane(root);

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.gridwidth = 1;

        // ── Logo brand header ──
        JPanel brand = UITheme.brandHeader("Admin Login", "Airline Management System");
        g.gridy = 0; g.insets = new Insets(0, 0, 28, 0);
        root.add(brand, g);

        // ── Username ──
        g.gridy = 1; g.insets = new Insets(0, 0, 4, 0);
        root.add(UITheme.fieldLabel("Username"), g);
        txtUsername = UITheme.styledField();
        txtUsername.setPreferredSize(new Dimension(310, 38));
        g.gridy = 2; g.insets = new Insets(0, 0, 14, 0);
        root.add(txtUsername, g);

        // ── Password ──
        g.gridy = 3; g.insets = new Insets(0, 0, 4, 0);
        root.add(UITheme.fieldLabel("Password"), g);
        txtPassword = UITheme.styledPasswordField();
        txtPassword.setPreferredSize(new Dimension(310, 38));
        g.gridy = 4; g.insets = new Insets(0, 0, 6, 0);
        root.add(txtPassword, g);

        // ── Error label ──
        lblError = new JLabel(" ");
        lblError.setForeground(UITheme.ACCENT_RED);
        lblError.setFont(UITheme.FONT_SMALL);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 5; g.insets = new Insets(0, 0, 6, 0);
        root.add(lblError, g);

        // ── Login button ──
        btnLogin = UITheme.primaryButton("Login  \u2192");
        btnLogin.setPreferredSize(new Dimension(310, 42));
        g.gridy = 6; g.insets = new Insets(0, 0, 0, 0);
        root.add(btnLogin, g);

        // ── Hint ──
        JLabel hint = UITheme.fieldLabel("Default: admin / admin123");
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        hint.setFont(UITheme.FONT_SMALL);
        g.gridy = 7; g.insets = new Insets(12, 0, 0, 0);
        root.add(hint, g);

        ActionListener loginAction = e -> attemptLogin();
        btnLogin.addActionListener(loginAction);
        txtPassword.addActionListener(loginAction);
        txtUsername.addActionListener(e -> txtPassword.requestFocus());

        pack();
        setLocationRelativeTo(null);
    }

    private void attemptLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Please fill in both fields.");
            return;
        }
        if (user.equals(ADMIN_USERNAME) && hashOf(pass).equals(ADMIN_PASS_HASH)) {
            AirlineFrame frame = new AirlineFrame();
            frame.setVisible(true);
            dispose();
        } else {
            lblError.setText("Incorrect username or password.");
            txtPassword.setText("");
        }
    }

    private static String hashOf(String s) {
        int h = 0;
        for (char c : s.toCharArray()) h = 31 * h + c;
        return Integer.toHexString(h);
    }

    public static void main(String[] args) {
        UITheme.apply();
        SwingUtilities.invokeLater(() -> new AdminGUI().setVisible(true));
    }
}
