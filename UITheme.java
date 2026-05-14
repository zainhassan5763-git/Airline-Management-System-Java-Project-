package airline;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class UITheme {

    public static final Color BG_DARK      = new Color(18, 18, 28);
    public static final Color BG_CARD      = new Color(28, 30, 45);
    public static final Color BG_INPUT     = new Color(38, 40, 60);
    public static final Color ACCENT       = new Color(99, 102, 241);
    public static final Color ACCENT_HOVER = new Color(129, 132, 255);
    public static final Color ACCENT_GREEN = new Color(34, 197, 94);
    public static final Color ACCENT_RED   = new Color(239, 68, 68);
    public static final Color ACCENT_AMBER = new Color(245, 158, 11);
    public static final Color TEXT_PRIMARY = new Color(240, 240, 250);
    public static final Color TEXT_MUTED   = new Color(180, 182, 200);
    public static final Color BORDER       = new Color(55, 58, 85);

    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_LABEL  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_INPUT  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);

    public static void apply() {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        UIManager.put("Panel.background",              BG_DARK);
        UIManager.put("OptionPane.background",         BG_CARD);
        UIManager.put("OptionPane.messageForeground",  TEXT_PRIMARY);
        UIManager.put("Button.background",             ACCENT);
        UIManager.put("Button.foreground",             Color.WHITE);
        UIManager.put("Button.font",                   FONT_BUTTON);
        UIManager.put("Button.select",                 ACCENT_HOVER);
        UIManager.put("Button.focus",                  new Color(0,0,0,0));
        UIManager.put("Label.foreground",              TEXT_PRIMARY);
        UIManager.put("Label.font",                    FONT_LABEL);
        UIManager.put("TextField.background",          BG_INPUT);
        UIManager.put("TextField.foreground",          TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground",     TEXT_PRIMARY);
        UIManager.put("TextField.font",                FONT_INPUT);
        UIManager.put("TextField.selectionBackground", ACCENT);
        UIManager.put("TextField.selectionForeground", Color.WHITE);
        UIManager.put("TextField.inactiveForeground",  TEXT_MUTED);
        UIManager.put("PasswordField.background",      BG_INPUT);
        UIManager.put("PasswordField.foreground",      TEXT_PRIMARY);
        UIManager.put("PasswordField.caretForeground", TEXT_PRIMARY);
        UIManager.put("PasswordField.font",            FONT_INPUT);
        UIManager.put("TextArea.background",           BG_INPUT);
        UIManager.put("TextArea.foreground",           TEXT_PRIMARY);
        UIManager.put("TextArea.font",                 FONT_INPUT);
        UIManager.put("TextArea.selectionBackground",  ACCENT);
        UIManager.put("TextArea.selectionForeground",  Color.WHITE);
        UIManager.put("ScrollPane.background",         BG_CARD);
        UIManager.put("Viewport.background",           BG_INPUT);
        UIManager.put("ScrollBar.background",          BG_CARD);
        UIManager.put("ScrollBar.thumb",               BORDER);
        UIManager.put("ScrollBar.track",               BG_CARD);
        UIManager.put("MenuBar.background",            BG_CARD);
        UIManager.put("MenuBar.foreground",            TEXT_PRIMARY);
        UIManager.put("Menu.background",               BG_CARD);
        UIManager.put("Menu.foreground",               TEXT_PRIMARY);
        UIManager.put("Menu.selectionBackground",      ACCENT);
        UIManager.put("Menu.selectionForeground",      Color.WHITE);
        UIManager.put("MenuItem.background",           BG_CARD);
        UIManager.put("MenuItem.foreground",           TEXT_PRIMARY);
        UIManager.put("MenuItem.selectionBackground",  ACCENT);
        UIManager.put("MenuItem.selectionForeground",  Color.WHITE);
        UIManager.put("PopupMenu.background",          BG_CARD);
        UIManager.put("PopupMenu.foreground",          TEXT_PRIMARY);
        UIManager.put("Separator.foreground",          BORDER);
        UIManager.put("InternalFrame.background",      BG_CARD);
        UIManager.put("InternalFrame.activeTitleBackground",   ACCENT);
        UIManager.put("InternalFrame.activeTitleForeground",   Color.WHITE);
        UIManager.put("InternalFrame.inactiveTitleBackground", BG_CARD);
        UIManager.put("InternalFrame.inactiveTitleForeground", TEXT_MUTED);
        UIManager.put("InternalFrame.titleFont",               FONT_BUTTON);
        UIManager.put("DesktopPane.background",        BG_DARK);
        UIManager.put("RadioButton.background",        BG_CARD);
        UIManager.put("RadioButton.foreground",        TEXT_PRIMARY);
        UIManager.put("RadioButton.font",              FONT_LABEL);
        UIManager.put("CheckBox.background",           BG_CARD);
        UIManager.put("CheckBox.foreground",           TEXT_PRIMARY);
        UIManager.put("ComboBox.background",           BG_INPUT);
        UIManager.put("ComboBox.foreground",           TEXT_PRIMARY);
        UIManager.put("ComboBox.font",                 FONT_INPUT);
    }

    public static JLabel airplaneLogo(int size) {
        Icon icon = new Icon() {
            public int getIconWidth()  { return size; }
            public int getIconHeight() { return size; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                double s = size / 64.0;
                g2.translate(x, y);
                g2.scale(s, s);
                g2.setColor(new Color(99, 102, 241, 55));
                g2.fillOval(2, 2, 60, 60);
                g2.setColor(new Color(99, 102, 241, 25));
                g2.fillOval(8, 8, 48, 48);
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawOval(3, 3, 58, 58);
                g2.setColor(Color.WHITE);
                GeneralPath body = new GeneralPath();
                body.moveTo(32, 11);
                body.curveTo(42, 13, 52, 20, 53, 32);
                body.curveTo(52, 42, 42, 42, 36, 42);
                body.lineTo(29, 54);
                body.lineTo(25, 54);
                body.lineTo(28, 42);
                body.curveTo(22, 42, 12, 42, 11, 32);
                body.curveTo(12, 20, 22, 13, 32, 11);
                body.closePath();
                g2.fill(body);
                g2.setColor(ACCENT);
                GeneralPath wL = new GeneralPath();
                wL.moveTo(25, 33); wL.lineTo(8, 23); wL.lineTo(8, 28); wL.lineTo(24, 37);
                wL.closePath(); g2.fill(wL);
                GeneralPath wR = new GeneralPath();
                wR.moveTo(39, 33); wR.lineTo(56, 23); wR.lineTo(56, 28); wR.lineTo(40, 37);
                wR.closePath(); g2.fill(wR);
                GeneralPath tL = new GeneralPath();
                tL.moveTo(27, 48); tL.lineTo(16, 55); tL.lineTo(19, 55); tL.lineTo(29, 50);
                tL.closePath(); g2.fill(tL);
                GeneralPath tR = new GeneralPath();
                tR.moveTo(37, 48); tR.lineTo(48, 55); tR.lineTo(45, 55); tR.lineTo(35, 50);
                tR.closePath(); g2.fill(tR);
                g2.setColor(new Color(99, 102, 241, 210));
                g2.fillRoundRect(27, 25, 5, 4, 2, 2);
                g2.fillRoundRect(34, 24, 5, 4, 2, 2);
                g2.fillRoundRect(41, 25, 4, 4, 2, 2);
                g2.dispose();
            }
        };
        JLabel lbl = new JLabel(icon);
        lbl.setOpaque(false);
        return lbl;
    }

    public static JPanel brandHeader(String title, String subtitle) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0; g.insets = new Insets(0, 0, 10, 0);
        p.add(airplaneLogo(62), g);
        g.gridy = 1; g.insets = new Insets(0, 0, 4, 0);
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(FONT_TITLE); t.setForeground(TEXT_PRIMARY);
        p.add(t, g);
        g.gridy = 2;
        JLabel s = new JLabel(subtitle, SwingConstants.CENTER);
        s.setFont(FONT_SMALL); s.setForeground(TEXT_MUTED);
        p.add(s, g);
        return p;
    }

    public static JTextField styledField() {
        JTextField f = new JTextField();
        f.setBackground(BG_INPUT); f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(TEXT_PRIMARY); f.setFont(FONT_INPUT); f.setOpaque(true);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return f;
    }

    public static JPasswordField styledPasswordField() {
        JPasswordField f = new JPasswordField();
        f.setBackground(BG_INPUT); f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(TEXT_PRIMARY); f.setFont(FONT_INPUT); f.setOpaque(true);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return f;
    }

    public static JTextArea styledTextArea() {
        JTextArea a = new JTextArea();
        a.setBackground(BG_INPUT); a.setForeground(TEXT_PRIMARY);
        a.setCaretColor(TEXT_PRIMARY); a.setFont(FONT_INPUT); a.setOpaque(true);
        a.setLineWrap(true); a.setWrapStyleWord(true);
        a.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        a.setEditable(false);
        return a;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT); b.setForeground(Color.WHITE);
        b.setFont(FONT_BUTTON); b.setFocusPainted(false);
        b.setBorderPainted(false); b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(9, 22, 9, 22));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(ACCENT_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(ACCENT); }
        });
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT_RED); b.setForeground(Color.WHITE);
        b.setFont(FONT_BUTTON); b.setFocusPainted(false);
        b.setBorderPainted(false); b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(9, 22, 9, 22));
        return b;
    }

    public static JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BG_CARD); b.setForeground(TEXT_PRIMARY);
        b.setFont(FONT_BUTTON); b.setFocusPainted(false); b.setOpaque(true);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel titleLabel(String text) {
        JLabel l = new JLabel(text); l.setFont(FONT_TITLE); l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text); l.setFont(FONT_LABEL); l.setForeground(TEXT_MUTED);
        return l;
    }

    public static JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD); p.setOpaque(true);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        return p;
    }

    public static void styleScrollPane(JScrollPane sp) {
        sp.setBackground(BG_INPUT);
        sp.getViewport().setBackground(BG_INPUT);
        sp.setBorder(BorderFactory.createLineBorder(BORDER, 1));
    }
}
