package airline;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class AddPassenger_GUI extends JInternalFrame {

    private JTextField txtName, txtUsername, txtCnic, txtPassport, txtAddress, txtContact;
    private JRadioButton rbMale, rbFemale;
    private JButton btnSubmit, btnCancel, btnChoosePic;
    private JLabel  picLabel;
    private String  selectedPicturePath = "";

    public AddPassenger_GUI() {
        super("Add Passenger", true, true, true, true);
        initUI();
        setPreferredSize(new Dimension(520, 600));
        pack();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_CARD);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(UITheme.ACCENT_GREEN);
        JLabel title = new JLabel("👤 Add New Passenger");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        root.add(header, BorderLayout.NORTH);


        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_CARD);
        form.setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridwidth = 1;

        txtName     = UITheme.styledField();
        txtUsername = UITheme.styledField();
        txtCnic     = UITheme.styledField();
        txtPassport = UITheme.styledField();
        txtAddress  = UITheme.styledField();
        txtContact  = UITheme.styledField();

        addRow(form, g, 0, "Full Name",   txtName);
        addRow(form, g, 1, "Username",    txtUsername);
        addRow(form, g, 2, "CNIC",        txtCnic);
        addRow(form, g, 3, "Passport ID", txtPassport);
        addRow(form, g, 4, "Address",     txtAddress);
        addRow(form, g, 5, "Contact No",  txtContact);


        g.gridx = 0; g.gridy = 12; g.insets = new Insets(10, 0, 2, 0);
        form.add(UITheme.fieldLabel("Gender"), g);
        ButtonGroup bg = new ButtonGroup();
        rbMale   = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        rbMale.setBackground(UITheme.BG_CARD);   rbFemale.setBackground(UITheme.BG_CARD);
        rbMale.setForeground(UITheme.TEXT_PRIMARY); rbFemale.setForeground(UITheme.TEXT_PRIMARY);
        rbMale.setFont(UITheme.FONT_LABEL);       rbFemale.setFont(UITheme.FONT_LABEL);
        rbMale.setSelected(true);
        bg.add(rbMale); bg.add(rbFemale);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        genderPanel.setBackground(UITheme.BG_CARD);
        genderPanel.add(rbMale); genderPanel.add(rbFemale);
        g.gridy = 13; g.insets = new Insets(0, 0, 10, 0);
        form.add(genderPanel, g);


        g.gridx = 0; g.gridy = 14; g.insets = new Insets(10, 0, 6, 0);
        form.add(UITheme.fieldLabel("Passenger Photo"), g);


        picLabel = new JLabel();
        picLabel.setPreferredSize(new Dimension(100, 110));
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        picLabel.setVerticalAlignment(SwingConstants.CENTER);
        picLabel.setOpaque(true);
        picLabel.setBackground(UITheme.BG_INPUT);
        picLabel.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        picLabel.setIcon(placeholderIcon(100, 110));

        btnChoosePic = new JButton("📷  Add Picture");
        btnChoosePic.setFont(UITheme.FONT_BUTTON);
        btnChoosePic.setBackground(UITheme.BG_INPUT);
        btnChoosePic.setForeground(UITheme.TEXT_PRIMARY);
        btnChoosePic.setFocusPainted(false);
        btnChoosePic.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.ACCENT, 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        btnChoosePic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel photoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        photoRow.setBackground(UITheme.BG_CARD);
        photoRow.add(picLabel);
        photoRow.add(btnChoosePic);

        g.gridy = 15; g.insets = new Insets(0, 0, 8, 0);
        form.add(photoRow, g);


        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UITheme.BG_CARD);
        scrollPane.getViewport().setBackground(UITheme.BG_CARD);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        UITheme.styleScrollPane(scrollPane);
        root.add(scrollPane, BorderLayout.CENTER);


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 14));
        btnPanel.setBackground(UITheme.BG_CARD);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));
        btnCancel = UITheme.ghostButton("Cancel");
        btnSubmit = UITheme.primaryButton("Add Passenger");
        btnPanel.add(btnCancel); btnPanel.add(btnSubmit);
        root.add(btnPanel, BorderLayout.SOUTH);

        setContentPane(root);


        btnSubmit.addActionListener(e -> submit());
        btnCancel.addActionListener(e -> dispose());
        btnChoosePic.addActionListener(e -> choosePicture());

        txtName.addActionListener(e -> txtUsername.requestFocus());
        txtUsername.addActionListener(e -> txtCnic.requestFocus());
        txtCnic.addActionListener(e -> txtPassport.requestFocus());
        txtPassport.addActionListener(e -> txtAddress.requestFocus());
        txtAddress.addActionListener(e -> txtContact.requestFocus());
        txtContact.addActionListener(e -> submit());
    }


    private void choosePicture() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Passenger Photo");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Image Files (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            selectedPicturePath = selected.getAbsolutePath();

            try {
                Image img = ImageIO.read(selected);
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 110, Image.SCALE_SMOOTH));
                    picLabel.setIcon(icon);
                    picLabel.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Could not load image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addRow(JPanel p, GridBagConstraints g, int row, String label, JTextField field) {
        g.gridx = 0; g.gridy = row * 2; g.insets = new Insets(8, 0, 2, 0);
        p.add(UITheme.fieldLabel(label), g);
        g.gridy = row * 2 + 1; g.insets = new Insets(0, 0, 2, 0);
        field.setPreferredSize(new Dimension(420, 36));
        p.add(field, g);
    }

    private void submit() {
        String name     = txtName.getText().trim();
        String username = txtUsername.getText().trim();
        String cnic     = txtCnic.getText().trim();
        String passport = txtPassport.getText().trim();
        String address  = txtAddress.getText().trim();
        String contact  = txtContact.getText().trim();
        String gender   = rbMale.isSelected() ? "Male" : "Female";

        if (name.isEmpty() || username.isEmpty() || cnic.isEmpty() ||
            passport.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String passengerId = AppData.passengerData.generatePassengerId();
        Passenger p = new Passenger(name, username, passengerId, cnic, passport, address, gender, contact);
        p.setPicturePath(selectedPicturePath); // save photo path
        AppData.passengerData.addPassenger(p);

        JOptionPane.showMessageDialog(this,
                "<html><b>Passenger Added!</b><br>Name: " + name +
                "<br>ID: <b>" + passengerId + "</b> (save this for login)</html>",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }


    private ImageIcon placeholderIcon(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(UITheme.BG_INPUT);
        g2.fillRect(0, 0, w, h);
        g2.setColor(UITheme.TEXT_MUTED);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        g2.drawString("👤", w / 2 - 14, h / 2 + 8);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        g2.drawString("No Photo", w / 2 - 20, h / 2 + 26);
        g2.dispose();
        return new ImageIcon(img);
    }
}
