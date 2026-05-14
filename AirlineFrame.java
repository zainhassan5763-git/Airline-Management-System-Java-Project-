package airline;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class AirlineFrame extends JFrame {

    private JDesktopPane desktop;

    public AirlineFrame() { initUI(); }

    private void initUI() {
        setTitle("Airline Management — Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 760));


        desktop = new JDesktopPane() {
            private Image bgImage = loadBg();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {

                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(10, 15, 40, 180)); // dark overlay
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(UITheme.BG_DARK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }

            private Image loadBg() {
                String[] paths = {
                    "assets/airplane.png",
                    "../assets/airplane.png",
                    System.getProperty("user.dir") + "/assets/airplane.png"
                };
                for (String path : paths) {
                    try {
                        File f = new File(path);
                        if (f.exists()) return ImageIO.read(f);
                    } catch (Exception ignored) {}
                }
                return null;
            }
        };
        desktop.setBackground(UITheme.BG_DARK);


        JPanel banner = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        banner.setBackground(UITheme.BG_CARD);
        banner.setOpaque(true);
        JLabel logoSmall = UITheme.airplaneLogo(28);
        JLabel bannerText = new JLabel("Airline Management  —  Admin Dashboard");
        bannerText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bannerText.setForeground(UITheme.TEXT_PRIMARY);
        banner.add(logoSmall);
        banner.add(bannerText);
        desktop.add(banner);
        banner.setBounds(0, 0, 1280, 42);

        getContentPane().setBackground(UITheme.BG_DARK);
        add(desktop, BorderLayout.CENTER);


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(UITheme.BG_CARD);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER));

        JMenu menuPassenger = styledMenu("👤 Passenger");
        menuPassenger.add(styledItem("✚  Add Passenger",    e -> openInternal(new AddPassenger_GUI())));
        menuPassenger.add(styledItem("🔍  Search Passenger", e -> openInternal(new SearchPassenger_GUI())));
        menuPassenger.add(new JSeparator());
        menuPassenger.add(styledItem("🔐  Passenger Login",  e -> openInternal(new PassengerLogin_GUI())));
        menuBar.add(menuPassenger);

        JMenu menuFlights = styledMenu("✈ Flights");
        menuFlights.add(styledItem("✚  Add Flight",    e -> openInternal(new AddFlight_GUI())));
        menuFlights.add(styledItem("🔍  Search Flight", e -> openInternal(new SearchFlight_GUI())));
        menuBar.add(menuFlights);

        JMenu menuAbout = styledMenu("ℹ About");
        menuAbout.add(styledItem("Version 2.0 — Enhanced Edition", e -> {}));
        menuBar.add(menuAbout);

        setJMenuBar(menuBar);
        pack();
        setLocationRelativeTo(null);
    }

    private void openInternal(JInternalFrame frame) {
        frame.setVisible(true);
        desktop.add(frame);
        int count = desktop.getAllFrames().length;
        frame.setLocation(60 + (count % 5) * 30, 55 + (count % 5) * 30);
        try { frame.setSelected(true); } catch (Exception ignored) {}
    }

    private JMenu styledMenu(String text) {
        JMenu m = new JMenu(text);
        m.setForeground(UITheme.TEXT_PRIMARY);
        m.setFont(UITheme.FONT_LABEL);
        m.setBackground(UITheme.BG_CARD);
        return m;
    }

    private JMenuItem styledItem(String text, java.awt.event.ActionListener al) {
        JMenuItem item = new JMenuItem(text);
        item.setBackground(UITheme.BG_CARD);
        item.setForeground(UITheme.TEXT_PRIMARY);
        item.setFont(UITheme.FONT_LABEL);
        item.addActionListener(al);
        return item;
    }
}
