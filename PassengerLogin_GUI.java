package airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PassengerLogin_GUI extends JInternalFrame {

    private JTextField txtUsername, txtPassengerId;
    private JButton    btnLogin, btnCancel;
    private JLabel     lblError;

    public PassengerLogin_GUI() {
        super("Passenger Login", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(420, 360));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT);
        JLabel title = new JLabel("Passenger Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(24, 32, 10, 32));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;

        txtUsername    = UITheme.styledField();
        txtPassengerId = UITheme.styledField();

        g.gridx = 0; g.gridy = 0; g.insets = new Insets(6, 0, 2, 0);
        form.add(UITheme.fieldLabel("Username"), g);
        g.gridy = 1; g.insets = new Insets(0, 0, 10, 0);
        txtUsername.setPreferredSize(new Dimension(340, 36));
        form.add(txtUsername, g);

        g.gridy = 2; g.insets = new Insets(6, 0, 2, 0);
        form.add(UITheme.fieldLabel("Passenger ID"), g);
        g.gridy = 3; g.insets = new Insets(0, 0, 6, 0);
        txtPassengerId.setPreferredSize(new Dimension(340, 36));
        form.add(txtPassengerId, g);

        lblError = new JLabel(" ");
        lblError.setForeground(UITheme.ACCENT_RED);
        lblError.setFont(UITheme.FONT_SMALL);
        g.gridy = 4;
        form.add(lblError, g);

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UITheme.BG_CARD);
        scrollPane.getViewport().setBackground(UITheme.BG_CARD);
        UITheme.styleScrollPane(scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 14));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnCancel = UITheme.ghostButton("Cancel");
        btnLogin  = UITheme.primaryButton("Login");
        btnPanel.add(btnCancel); btnPanel.add(btnLogin);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);

        ActionListener loginAction = e -> login();
        btnLogin.addActionListener(loginAction);
        btnCancel.addActionListener(e -> dispose());
        txtUsername.addActionListener(e -> txtPassengerId.requestFocus());
        txtPassengerId.addActionListener(loginAction);
    }

    private void login() {
        String username    = txtUsername.getText().trim();
        String passengerId = txtPassengerId.getText().trim();
        if (username.isEmpty() || passengerId.isEmpty()) {
            lblError.setText("Please fill in both fields."); return;
        }
        Passenger p = AppData.passengerData.loginByUsernameAndId(username, passengerId);
        if (p == null) {
            lblError.setText("Invalid username or passenger ID.");
            txtPassengerId.setText(""); return;
        }
        AppData.currentPassenger = p;
        JOptionPane.showMessageDialog(this,
                "<html>Welcome back, <b>" + p.getName() + "</b>!</html>",
                "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        PassengerFrame pFrame = new PassengerFrame();
        pFrame.setVisible(true);
        pFrame.setLocationRelativeTo(null);
    }
}
