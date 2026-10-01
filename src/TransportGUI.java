import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

/**
 * TransportGUI — Premium dark-mode Swing UI for the School Bus Tracking System.
 */
public class TransportGUI extends JFrame {

    // =========================================================
    // DESIGN TOKENS
    // =========================================================

    static final Color BG          = new Color(10,  14,  26);
    static final Color SURFACE     = new Color(17,  24,  39);
    static final Color SURFACE2    = new Color(26,  35,  50);
    static final Color SIDEBAR_BG  = new Color(8,   12,  22);
    static final Color ACCENT      = new Color(99,  102, 241);
    static final Color ACCENT2     = new Color(34,  211, 238);
    static final Color TEXT        = new Color(249, 250, 251);
    static final Color TEXT_DIM    = new Color(107, 114, 128);
    static final Color BORDER_COL  = new Color(31,  41,  55);
    static final Color INPUT_BG    = new Color(20,  28,  42);
    static final Color SUCCESS     = new Color(16,  185, 129);
    static final Color DANGER      = new Color(239, 68,  68);
    static final Color WARN        = new Color(245, 158, 11);
    static final Color STEEL       = new Color(75,  85,  99);

    static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD,  22);
    static final Font FONT_FIELD_LBL = new Font("Segoe UI", Font.BOLD,  11);
    static final Font FONT_BTN       = new Font("Segoe UI", Font.BOLD,  13);
    static final Font FONT_LABEL     = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font FONT_TABLE     = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_MONO      = new Font("Consolas", Font.PLAIN, 13);

    // =========================================================
    // STATE
    // =========================================================

    private CardLayout   cardLayout;
    private JPanel       contentPanel;
    private JLabel       statusLabel;
    private JButton[]    navBtns;

    private DefaultTableModel studentTM, busTM, driverTM, routeTM, stopTM;

    private static final String[][] NAV_ITEMS = {
        {"\uD83D\uDC64", "Students"},
        {"\uD83D\uDE8C", "Buses"},
        {"\uD83D\uDE97", "Drivers"},
        {"\uD83D\uDDFA", "Routes"},
        {"\uD83D\uDCCD", "Stops"},
        {"\uD83D\uDD17", "Allocation"},
        {"\uD83D\uDD0D", "Search & Sort"},
        {"\uD83D\uDCCB", "History & Report"},
    };

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TransportGUI() {
        configUIManager();
        setTitle("BusTrack Pro \u2014 School Transportation System");
        setSize(1300, 820);
        setMinimumSize(new Dimension(1100, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BG);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(buildHeader(),     BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);
        body.add(buildSidebar(), BorderLayout.WEST);
        body.add(buildContent(), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    // =========================================================
    // UI MANAGER DARK DEFAULTS
    // =========================================================

    private void configUIManager() {
        UIManager.put("OptionPane.background",        SURFACE);
        UIManager.put("Panel.background",             SURFACE);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("TextField.background",         INPUT_BG);
        UIManager.put("TextField.foreground",         TEXT);
        UIManager.put("TextField.caretForeground",    ACCENT2);
        UIManager.put("TextArea.background",          INPUT_BG);
        UIManager.put("TextArea.foreground",          TEXT);
        UIManager.put("ScrollPane.background",        SURFACE);
        UIManager.put("Viewport.background",          SURFACE);
        UIManager.put("Table.background",             SURFACE);
        UIManager.put("Table.foreground",             TEXT);
        UIManager.put("Table.selectionBackground",    ACCENT);
        UIManager.put("Table.selectionForeground",    Color.WHITE);
        UIManager.put("Table.gridColor",              BORDER_COL);
        UIManager.put("TableHeader.background",       SURFACE2);
        UIManager.put("TableHeader.foreground",       TEXT_DIM);
        UIManager.put("Label.foreground",             TEXT);
        UIManager.put("Button.background",            ACCENT);
        UIManager.put("Button.foreground",            TEXT);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(20, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(25, 15, 60),
                        getWidth(), getHeight(), new Color(8, 28, 60));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 8));
                for (int x = 0; x < getWidth(); x += 22)
                    for (int y = 0; y < getHeight(); y += 22)
                        g2.fillOval(x, y, 2, 2);
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(99, 102, 241, 90)),
                BorderFactory.createEmptyBorder(0, 28, 0, 28)
        ));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        left.setOpaque(false);
        JLabel iconLbl = new JLabel("\uD83D\uDE8C");
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        JPanel titles = new JPanel(new GridLayout(2, 1, 0, 1));
        titles.setOpaque(false);
        JLabel appName = new JLabel("BusTrack Pro");
        appName.setFont(FONT_TITLE);
        appName.setForeground(TEXT);
        JLabel appSub = new JLabel("School Transportation Management System");
        appSub.setFont(FONT_SMALL);
        appSub.setForeground(new Color(148, 163, 184));
        titles.add(appName);
        titles.add(appSub);
        left.add(iconLbl);
        left.add(titles);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JPanel badge = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(16, 185, 129, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(16, 185, 129, 80));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        badge.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));
        JLabel dot = new JLabel("\u25CF");
        dot.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        dot.setForeground(SUCCESS);
        JLabel liveText = new JLabel("SYSTEM ACTIVE");
        liveText.setFont(new Font("Segoe UI", Font.BOLD, 11));
        liveText.setForeground(SUCCESS);
        badge.add(dot);
        badge.add(liveText);
        right.add(badge);

        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(SIDEBAR_BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COL));

        sidebar.add(Box.createRigidArea(new Dimension(0, 24)));
        JLabel navHeading = new JLabel("  NAVIGATION");
        navHeading.setFont(new Font("Segoe UI", Font.BOLD, 10));
        navHeading.setForeground(new Color(75, 85, 99));
        navHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        navHeading.setBorder(BorderFactory.createEmptyBorder(0, 20, 8, 0));
        sidebar.add(navHeading);

        navBtns = new JButton[NAV_ITEMS.length];
        for (int i = 0; i < NAV_ITEMS.length; i++) {
            final int idx = i;
            JButton btn = makeNavBtn(NAV_ITEMS[i][0], NAV_ITEMS[i][1], i == 0);
            btn.addActionListener(e -> navigateTo(idx));
            navBtns[i] = btn;
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        sidebar.add(Box.createVerticalGlue());
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COL);
        sep.setBackground(BORDER_COL);
        sep.setMaximumSize(new Dimension(230, 1));
        sidebar.add(sep);
        JLabel ver = new JLabel("  v1.0  \u00B7  School Bus System");
        ver.setFont(FONT_SMALL);
        ver.setForeground(new Color(55, 65, 81));
        ver.setAlignmentX(Component.LEFT_ALIGNMENT);
        ver.setBorder(BorderFactory.createEmptyBorder(12, 20, 16, 0));
        sidebar.add(ver);
        return sidebar;
    }

    private JButton makeNavBtn(String icon, String label, boolean active) {
        JButton btn = new JButton(icon + "   " + label) {
            private boolean hov = false;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean isActive = Boolean.TRUE.equals(getClientProperty("active"));
                if (isActive) {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(99, 102, 241, 55),
                            getWidth(), 0, new Color(34, 211, 238, 20));
                    g2.setPaint(gp);
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                    g2.setColor(ACCENT);
                    g2.fillRoundRect(0, 10, 4, getHeight() - 20, 4, 4);
                } else if (hov) {
                    g2.setColor(new Color(255, 255, 255, 12));
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hov = false; repaint(); }
                });
            }
        };
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btn.setForeground(active ? TEXT : TEXT_DIM);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(11, 22, 11, 22));
        btn.setMaximumSize(new Dimension(230, 48));
        btn.setPreferredSize(new Dimension(230, 48));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (active) btn.putClientProperty("active", Boolean.TRUE);
        return btn;
    }

    public void navigateTo(int idx) {
        for (int i = 0; i < navBtns.length; i++) {
            boolean a = (i == idx);
            navBtns[i].putClientProperty("active", a);
            navBtns[i].setForeground(a ? TEXT : TEXT_DIM);
            navBtns[i].repaint();
        }
        cardLayout.show(contentPanel, NAV_ITEMS[idx][1]);
        setStatus("Viewing: " + NAV_ITEMS[idx][1]);
    }

    // =========================================================
    // CONTENT (CardLayout)
    // =========================================================

    private JPanel buildContent() {
        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG);

        contentPanel.add(buildStudentPanel(),  NAV_ITEMS[0][1]);
        contentPanel.add(buildBusPanel(),      NAV_ITEMS[1][1]);
        contentPanel.add(buildDriverPanel(),   NAV_ITEMS[2][1]);
        contentPanel.add(buildRoutePanel(),    NAV_ITEMS[3][1]);
        contentPanel.add(buildStopPanel(),     NAV_ITEMS[4][1]);
        contentPanel.add(buildAllocPanel(),    NAV_ITEMS[5][1]);
        contentPanel.add(buildSearchPanel(),   NAV_ITEMS[6][1]);
        contentPanel.add(buildHistoryPanel(),  NAV_ITEMS[7][1]);
        return contentPanel;
    }

    // =========================================================
    // STATUS BAR
    // =========================================================

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(SIDEBAR_BG);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COL),
                BorderFactory.createEmptyBorder(7, 24, 7, 24)
        ));
        statusLabel = new JLabel("\u25CF Ready \u2014 BusTrack Pro");
        statusLabel.setFont(FONT_SMALL);
        statusLabel.setForeground(TEXT_DIM);
        bar.add(statusLabel, BorderLayout.WEST);
        JLabel ri = new JLabel("Java Swing  \u00B7  School Bus Tracking System");
        ri.setFont(FONT_SMALL);
        ri.setForeground(new Color(55, 65, 81));
        bar.add(ri, BorderLayout.EAST);
        return bar;
    }

    public void setStatus(String msg) {
        statusLabel.setText("\u25CF " + msg);
        statusLabel.setForeground(ACCENT2);
        javax.swing.Timer t = new javax.swing.Timer(3000, e -> {
            statusLabel.setText("\u25CF " + msg);
            statusLabel.setForeground(TEXT_DIM);
        });
        t.setRepeats(false);
        t.start();
    }

    public JPanel getContentPanel() { return contentPanel; }
    public DefaultTableModel getStudentTM() { return studentTM; }
    public DefaultTableModel getBusTM() { return busTM; }
    public DefaultTableModel getDriverTM() { return driverTM; }
    public DefaultTableModel getRouteTM() { return routeTM; }
    public DefaultTableModel getStopTM() { return stopTM; }

    // =========================================================
    // COMPONENT HELPERS
    // =========================================================

    private JPanel sectionShell(String title, String sub) {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(BG);
        p.setBorder(BorderFactory.createEmptyBorder(26, 28, 22, 28));
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setOpaque(false);
        hdr.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        JLabel tl = new JLabel(title);
        tl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tl.setForeground(TEXT);
        JLabel sl = new JLabel(sub);
        sl.setFont(FONT_LABEL);
        sl.setForeground(TEXT_DIM);
        JPanel stack = new JPanel(new GridLayout(2, 1, 0, 3));
        stack.setOpaque(false);
        stack.add(tl);
        stack.add(sl);
        hdr.add(stack, BorderLayout.WEST);
        JPanel line = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, ACCENT, 200, 0, new Color(34, 211, 238, 0));
                g2.setPaint(gp);
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
                g2.dispose();
            }
        };
        line.setOpaque(false);
        line.setPreferredSize(new Dimension(0, 2));
        hdr.add(line, BorderLayout.SOUTH);
        p.add(hdr, BorderLayout.NORTH);
        return p;
    }

    private JPanel card() {
        JPanel c = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(BORDER_COL);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        c.setOpaque(false);
        c.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        return c;
    }

    private JTextField field() {
        JTextField f = new JTextField();
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT2);
        f.setFont(FONT_LABEL);
        f.setPreferredSize(new Dimension(180, 36));
        applyFB(f, false);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { applyFB(f, true); }
            @Override public void focusLost(FocusEvent e)   { applyFB(f, false); }
        });
        return f;
    }

    private void applyFB(JTextField f, boolean focused) {
        Color c = focused ? ACCENT : BORDER_COL;
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(c, 1),
                BorderFactory.createEmptyBorder(7, 11, 7, 11)
        ));
    }

    private JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_FIELD_LBL);
        l.setForeground(TEXT_DIM);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return l;
    }

    private JPanel fieldGroup(String labelText, JTextField f) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        p.add(lbl(labelText), BorderLayout.NORTH);
        p.add(f, BorderLayout.CENTER);
        return p;
    }

    private JButton btn(String text, Color color) {
        JButton b = new JButton(text) {
            private boolean hov = false;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = hov ? color.brighter() : color;
                GradientPaint gp = new GradientPaint(0, 0, base, 0, getHeight(), base.darker());
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 9, 9);
                if (hov) {
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 9, 9);
                }
                g2.dispose();
                super.paintComponent(g);
            }
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hov = false; repaint(); }
                });
            }
        };
        b.setFont(FONT_BTN);
        b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTable makeTable(DefaultTableModel model) {
        JTable t = new JTable(model) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        t.setBackground(SURFACE);
        t.setForeground(TEXT);
        t.setFont(FONT_TABLE);
        t.setRowHeight(40);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(99, 102, 241, 70));
        t.setSelectionForeground(TEXT);
        t.setFillsViewportHeight(true);
        JTableHeader th = t.getTableHeader();
        th.setBackground(SURFACE2);
        th.setForeground(new Color(148, 163, 184));
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setPreferredSize(new Dimension(0, 42));
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COL));
        ((DefaultTableCellRenderer) th.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, focus, row, col);
                setBackground(sel ? new Color(99, 102, 241, 70) : (row % 2 == 0 ? SURFACE : new Color(22, 31, 46)));
                setForeground(TEXT);
                setFont(FONT_TABLE);
                setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
                return this;
            }
        });
        return t;
    }

    private JScrollPane scroll(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBackground(SURFACE);
        sp.getViewport().setBackground(SURFACE);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        sp.getVerticalScrollBar().setBackground(SURFACE2);
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(55, 65, 81);
                trackColor = SURFACE2;
            }
            @Override protected JButton createDecreaseButton(int o) { return zeroBtn(); }
            @Override protected JButton createIncreaseButton(int o) { return zeroBtn(); }
            private JButton zeroBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
        return sp;
    }

    private JPanel btnRow(JButton... btns) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setOpaque(false);
        for (JButton b : btns) p.add(b);
        return p;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
    }

    // =========================================================
    // DIALOG HELPERS
    // =========================================================

    private void ok(String msg)      { JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE); }
    private void err(String msg)     { JOptionPane.showMessageDialog(this, msg, "Error",   JOptionPane.ERROR_MESSAGE); }
    private boolean ask(String msg)  { return JOptionPane.showConfirmDialog(this, msg, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; }
    private String input(String msg) { return JOptionPane.showInputDialog(this, msg); }

    // =========================================================
    // STUDENT PANEL
    // =========================================================

    private JPanel buildStudentPanel() {
        JPanel shell = sectionShell("\uD83D\uDC64  Students", "Add, update, delete and view student records");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField idF   = field(); JTextField nameF = field();
        JTextField ageF  = field(); JTextField clsF  = field();
        JTextField adrF  = field(); adrF.setPreferredSize(new Dimension(280, 36));

        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("STUDENT ID", idF),   g);
        g.gridx=1;             formCard.add(fieldGroup("FULL NAME",  nameF), g);
        g.gridx=2;             formCard.add(fieldGroup("AGE",        ageF),  g);
        g.gridx=0; g.gridy=1; formCard.add(fieldGroup("CLASS",      clsF),  g);
        g.gridx=1; g.gridwidth=2; formCard.add(fieldGroup("ADDRESS", adrF), g); g.gridwidth=1;

        studentTM = new DefaultTableModel(new String[]{"ID","Name","Age","Class","Address","Bus"}, 0);
        JTable tbl = makeTable(studentTM);

        // Click row → prefill form
        tbl.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                int r = tbl.getSelectedRow();
                idF.setText(tbl.getValueAt(r, 0).toString());
                nameF.setText(tbl.getValueAt(r, 1).toString());
                ageF.setText(tbl.getValueAt(r, 2).toString());
                clsF.setText(tbl.getValueAt(r, 3).toString());
                adrF.setText(tbl.getValueAt(r, 4).toString());
            }
        });

        JButton addBtn = btn("\uFF0B  Add Student", SUCCESS);
        JButton updBtn = btn("\u270E  Update",      ACCENT);
        JButton delBtn = btn("\u2715  Delete",      DANGER);
        JButton refBtn = btn("\u21BB  Refresh",     STEEL);

        Runnable refresh = () -> {
            studentTM.setRowCount(0);
            for (Student s : Main.getStudents())
                studentTM.addRow(new Object[]{
                    s.getStudentId(), s.getStudentName(), s.getAge(),
                    s.getClassName(), s.getAddress(),
                    s.getBusNumber() == -1 ? "Not Assigned" : "Bus #" + s.getBusNumber()
                });
        };

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                String n = nameF.getText().trim(); int age = Integer.parseInt(ageF.getText().trim());
                String cls = clsF.getText().trim(); String adr = adrF.getText().trim();
                if (!Main.validateStudent(n, age, cls, adr)) { err("Fill all fields with valid values."); return; }
                Student s = new Student(id, n, age, cls, adr);
                if (Main.addStudent(s)) { ok("Student added!"); clearFields(idF, nameF, ageF, clsF, adrF); refresh.run(); setStatus("Added: " + n); }
                else err("Student ID " + id + " already exists.");
            } catch (NumberFormatException ex) { err("ID and Age must be numbers."); }
        });

        updBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim()); String n = nameF.getText().trim();
                int age = Integer.parseInt(ageF.getText().trim()); String cls = clsF.getText().trim(); String adr = adrF.getText().trim();
                if (Main.updateStudent(id, n, age, cls, adr)) { ok("Student updated!"); refresh.run(); setStatus("Updated student ID: " + id); }
                else err("Student ID " + id + " not found.");
            } catch (NumberFormatException ex) { err("Enter valid numeric ID and Age."); }
        });

        delBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                if (ask("Delete student ID " + id + "?")) {
                    if (Main.deleteStudent(id)) { ok("Student deleted."); refresh.run(); setStatus("Deleted student ID: " + id); }
                    else err("Student not found.");
                }
            } catch (NumberFormatException ex) { err("Enter a valid Student ID."); }
        });

        refBtn.addActionListener(e -> { refresh.run(); setStatus("Refreshed students."); });

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER); top.add(btnRow(addBtn, updBtn, delBtn, refBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH); center.add(scroll(tbl), BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);
        refresh.run();
        return shell;
    }

    // =========================================================
    // BUS PANEL
    // =========================================================

    private JPanel buildBusPanel() {
        JPanel shell = sectionShell("\uD83D\uDE8C  Buses", "Manage bus fleet, capacity and driver assignments");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField numF = field(); JTextField nameF = field(); JTextField capF = field();
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("BUS NUMBER", numF), g);
        g.gridx=1;             formCard.add(fieldGroup("BUS NAME",   nameF), g);
        g.gridx=2;             formCard.add(fieldGroup("CAPACITY",   capF), g);

        busTM = new DefaultTableModel(new String[]{"Bus #","Name","Capacity","Passengers","Driver"}, 0);
        JTable tbl = makeTable(busTM);

        // Click row → prefill form
        tbl.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                int r = tbl.getSelectedRow();
                numF.setText(tbl.getValueAt(r, 0).toString());
                nameF.setText(tbl.getValueAt(r, 1).toString());
                capF.setText(tbl.getValueAt(r, 2).toString());
            }
        });

        JButton addBtn = btn("\uFF0B  Add Bus",     SUCCESS);
        JButton updBtn = btn("\u270E  Update Name", ACCENT);
        JButton delBtn = btn("\u2715  Delete",      DANGER);
        JButton refBtn = btn("\u21BB  Refresh",     STEEL);

        Runnable refresh = () -> {
            busTM.setRowCount(0);
            for (Bus b : Main.getBuses())
                busTM.addRow(new Object[]{
                    b.getBusNumber(), b.getBusName(), b.getCapacity(),
                    b.getStudentCount() + " / " + b.getCapacity(),
                    b.getDriver() == null ? "\u2014 Unassigned" : b.getDriver().getDriverName()
                });
        };

        addBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim()); String nm = nameF.getText().trim();
                int cap = Integer.parseInt(capF.getText().trim());
                if (!Main.validateBus(num, nm, cap)) { err("Fill all fields with valid values."); return; }
                Bus b = new Bus(num, nm, cap);
                if (Main.addBus(b)) { ok("Bus #" + num + " added!"); clearFields(numF, nameF, capF); refresh.run(); setStatus("Added bus #" + num); }
                else err("Bus number " + num + " already exists.");
            } catch (NumberFormatException ex) { err("Bus number and capacity must be numbers."); }
              catch (IllegalArgumentException ex) { err(ex.getMessage()); }
        });

        updBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim()); String nm = nameF.getText().trim();
                if (Main.updateBus(num, nm)) { ok("Bus updated!"); refresh.run(); setStatus("Updated bus #" + num); }
                else err("Bus #" + num + " not found.");
            } catch (NumberFormatException ex) { err("Enter a valid bus number."); }
        });

        delBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim());
                if (ask("Delete Bus #" + num + "? All students will be unassigned.")) {
                    if (Main.deleteBus(num)) { ok("Bus deleted."); refresh.run(); setStatus("Deleted bus #" + num); }
                    else err("Bus not found.");
                }
            } catch (NumberFormatException ex) { err("Enter a valid bus number."); }
        });

        refBtn.addActionListener(e -> { refresh.run(); setStatus("Refreshed buses."); });

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER); top.add(btnRow(addBtn, updBtn, delBtn, refBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH); center.add(scroll(tbl), BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);
        refresh.run();
        return shell;
    }

    // =========================================================
    // DRIVER PANEL
    // =========================================================

    private JPanel buildDriverPanel() {
        JPanel shell = sectionShell("\uD83D\uDE97  Drivers", "Manage drivers, contact info and bus assignments");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField idF = field(); JTextField nameF = field();
        JTextField phoneF = field(); JTextField licF = field();
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("DRIVER ID",      idF),   g);
        g.gridx=1;             formCard.add(fieldGroup("FULL NAME",      nameF), g);
        g.gridx=0; g.gridy=1; formCard.add(fieldGroup("PHONE NUMBER",   phoneF),g);
        g.gridx=1;             formCard.add(fieldGroup("LICENSE NUMBER", licF),  g);

        driverTM = new DefaultTableModel(new String[]{"Driver ID","Name","Phone","License"}, 0);
        JTable tbl = makeTable(driverTM);

        // Click row → prefill form
        tbl.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                int r = tbl.getSelectedRow();
                idF.setText(tbl.getValueAt(r, 0).toString());
                nameF.setText(tbl.getValueAt(r, 1).toString());
                phoneF.setText(tbl.getValueAt(r, 2).toString());
                licF.setText(tbl.getValueAt(r, 3).toString());
            }
        });

        JButton addBtn    = btn("\uFF0B  Add Driver",    SUCCESS);
        JButton updBtn    = btn("\u270E  Update",        ACCENT);
        JButton delBtn    = btn("\u2715  Delete",        DANGER);
        JButton assignBtn = btn("\uD83D\uDE8C  Assign to Bus", new Color(14, 165, 233));
        JButton refBtn    = btn("\u21BB  Refresh",       STEEL);

        Runnable refresh = () -> {
            driverTM.setRowCount(0);
            for (Driver d : Main.getDrivers())
                driverTM.addRow(new Object[]{d.getDriverId(), d.getDriverName(), d.getPhoneNumber(), d.getLicenseNumber()});
        };

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim()); String nm = nameF.getText().trim();
                if (nm.isEmpty()) { err("Driver name cannot be empty."); return; }
                Driver d = new Driver(id, nm, phoneF.getText().trim(), licF.getText().trim());
                if (Main.addDriver(d)) { ok("Driver added!"); clearFields(idF, nameF, phoneF, licF); refresh.run(); setStatus("Added driver: " + nm); }
                else err("Driver ID " + id + " already exists.");
            } catch (NumberFormatException ex) { err("Driver ID must be a number."); }
        });

        updBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                if (Main.updateDriver(id, nameF.getText().trim(), phoneF.getText().trim(), licF.getText().trim()))
                    { ok("Driver updated!"); refresh.run(); setStatus("Updated driver ID: " + id); }
                else err("Driver ID " + id + " not found.");
            } catch (NumberFormatException ex) { err("Enter a valid Driver ID."); }
        });

        delBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                if (ask("Delete Driver ID " + id + "? They will be unassigned from their bus.")) {
                    if (Main.deleteDriver(id)) { ok("Driver deleted."); refresh.run(); setStatus("Deleted driver ID: " + id); }
                    else err("Driver not found.");
                }
            } catch (NumberFormatException ex) { err("Enter a valid Driver ID."); }
        });

        assignBtn.addActionListener(e -> {
            String bi = input("Enter Bus Number to assign this driver:");
            if (bi == null) return;
            try {
                int busNum = Integer.parseInt(bi.trim()); int drvId = Integer.parseInt(idF.getText().trim());
                if (Main.assignDriverToBus(busNum, drvId)) { ok("Driver assigned to Bus #" + busNum + "!"); setStatus("Driver " + drvId + " \u2192 Bus #" + busNum); }
                else err("Bus or Driver not found.");
            } catch (NumberFormatException ex) { err("Enter valid numbers."); }
        });

        refBtn.addActionListener(e -> { refresh.run(); setStatus("Refreshed drivers."); });

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER); top.add(btnRow(addBtn, updBtn, delBtn, assignBtn, refBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH); center.add(scroll(tbl), BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);
        refresh.run();
        return shell;
    }

    // =========================================================
    // ROUTE PANEL
    // =========================================================

    private JPanel buildRoutePanel() {
        JPanel shell = sectionShell("\uD83D\uDDFA  Routes", "Define transport routes and manage stop assignments");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField numF = field(); JTextField nameF = field();
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("ROUTE NUMBER", numF), g);
        g.gridx=1;             formCard.add(fieldGroup("ROUTE NAME",   nameF), g);

        routeTM = new DefaultTableModel(new String[]{"Route #","Name","Stops"}, 0);
        JTable tbl = makeTable(routeTM);

        // Click row → prefill form
        tbl.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                int r = tbl.getSelectedRow();
                numF.setText(tbl.getValueAt(r, 0).toString());
                nameF.setText(tbl.getValueAt(r, 1).toString());
            }
        });

        JButton addBtn  = btn("\uFF0B  Add Route",       SUCCESS);
        JButton updBtn  = btn("\u270E  Update",          ACCENT);
        JButton delBtn  = btn("\u2715  Delete",          DANGER);
        JButton sortBtn = btn("\u21D5  Sort by Number",  STEEL);
        JButton refBtn  = btn("\u21BB  Refresh",         STEEL);

        Runnable refresh = () -> {
            routeTM.setRowCount(0);
            for (Map.Entry<Integer, Route> entry : Main.getRoutes().entrySet()) {
                Route r = entry.getValue();
                routeTM.addRow(new Object[]{r.getRouteNumber(), r.getRouteName(), r.getStops().size()});
            }
        };

        addBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim()); String nm = nameF.getText().trim();
                if (!Main.validateRoute(num, nm)) { err("Fill all fields with valid values."); return; }
                Route r = new Route(num, nm);
                if (Main.addRoute(r)) { ok("Route added!"); clearFields(numF, nameF); refresh.run(); setStatus("Added route #" + num); }
                else err("Route #" + num + " already exists.");
            } catch (NumberFormatException ex) { err("Route number must be a number."); }
        });

        updBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim());
                if (Main.updateRoute(num, nameF.getText().trim())) { ok("Route updated!"); refresh.run(); setStatus("Updated route #" + num); }
                else err("Route #" + num + " not found.");
            } catch (NumberFormatException ex) { err("Enter a valid route number."); }
        });

        delBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(numF.getText().trim());
                if (ask("Delete Route #" + num + "?")) {
                    if (Main.deleteRoute(num)) { ok("Route deleted."); refresh.run(); setStatus("Deleted route #" + num); }
                    else err("Route not found.");
                }
            } catch (NumberFormatException ex) { err("Enter a valid route number."); }
        });

        sortBtn.addActionListener(e -> {
            routeTM.setRowCount(0);
            for (Route r : Main.sortRoutesByNumber())
                routeTM.addRow(new Object[]{r.getRouteNumber(), r.getRouteName(), r.getStops().size()});
            setStatus("Sorted routes by number.");
        });

        refBtn.addActionListener(e -> { refresh.run(); setStatus("Refreshed routes."); });

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER); top.add(btnRow(addBtn, updBtn, delBtn, sortBtn, refBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH); center.add(scroll(tbl), BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);
        refresh.run();
        return shell;
    }

    // =========================================================
    // STOP PANEL
    // =========================================================

    private JPanel buildStopPanel() {
        JPanel shell = sectionShell("\uD83D\uDCCD  Stops", "Manage bus stops and assign them to routes");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField idF = field(); JTextField nameF = field();
        JTextField locF = field(); locF.setPreferredSize(new Dimension(240, 36));
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("STOP ID",   idF),   g);
        g.gridx=1;             formCard.add(fieldGroup("STOP NAME", nameF), g);
        g.gridx=2;             formCard.add(fieldGroup("LOCATION",  locF),  g);

        stopTM = new DefaultTableModel(new String[]{"Stop ID","Name","Location"}, 0);
        JTable tbl = makeTable(stopTM);

        // Click row → prefill form
        tbl.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                int r = tbl.getSelectedRow();
                idF.setText(tbl.getValueAt(r, 0).toString());
                nameF.setText(tbl.getValueAt(r, 1).toString());
                locF.setText(tbl.getValueAt(r, 2).toString());
            }
        });

        JButton addBtn       = btn("\uFF0B  Add Stop",         SUCCESS);
        JButton delBtn       = btn("\u2715  Delete",           DANGER);
        JButton toRouteBtn   = btn("\uD83D\uDDFA  Add to Route",    new Color(14, 165, 233));
        JButton fromRouteBtn = btn("\u2715  Remove from Route", WARN);
        JButton refBtn       = btn("\u21BB  Refresh",          STEEL);

        Runnable refresh = () -> {
            stopTM.setRowCount(0);
            for (Stop s : Main.getStops())
                stopTM.addRow(new Object[]{s.getStopId(), s.getStopName(), s.getLocation()});
        };

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim()); String nm = nameF.getText().trim(); String loc = locF.getText().trim();
                if (nm.isEmpty() || loc.isEmpty()) { err("All fields are required."); return; }
                Stop stop = new Stop(id, nm, loc);
                if (Main.addStop(stop)) { ok("Stop added!"); clearFields(idF, nameF, locF); refresh.run(); setStatus("Added stop #" + id); }
                else err("Stop ID " + id + " already exists.");
            } catch (NumberFormatException ex) { err("Stop ID must be a number."); }
        });

        delBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                if (ask("Delete Stop #" + id + "?")) {
                    if (Main.deleteStop(id)) { ok("Stop deleted."); refresh.run(); setStatus("Deleted stop #" + id); }
                    else err("Stop not found.");
                }
            } catch (NumberFormatException ex) { err("Enter a valid Stop ID."); }
        });

        toRouteBtn.addActionListener(e -> {
            String ri = input("Enter Route Number:");
            if (ri == null) return;
            try {
                int rNum = Integer.parseInt(ri.trim()); int sId = Integer.parseInt(idF.getText().trim());
                if (Main.addStopToRoute(rNum, sId)) { ok("Stop added to Route #" + rNum + "!"); setStatus("Stop " + sId + " \u2192 Route #" + rNum); }
                else err("Route or Stop not found.");
            } catch (NumberFormatException ex) { err("Enter valid numbers."); }
        });

        fromRouteBtn.addActionListener(e -> {
            String ri = input("Enter Route Number:");
            if (ri == null) return;
            try {
                int rNum = Integer.parseInt(ri.trim()); int sId = Integer.parseInt(idF.getText().trim());
                if (Main.removeStopFromRoute(rNum, sId)) { ok("Stop removed from Route #" + rNum + "!"); setStatus("Stop " + sId + " removed from Route #" + rNum); }
                else err("Route or Stop not found.");
            } catch (NumberFormatException ex) { err("Enter valid numbers."); }
        });

        refBtn.addActionListener(e -> { refresh.run(); setStatus("Refreshed stops."); });

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER); top.add(btnRow(addBtn, delBtn, toRouteBtn, fromRouteBtn, refBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH); center.add(scroll(tbl), BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);
        refresh.run();
        return shell;
    }

    // =========================================================
    // ALLOCATION PANEL
    // =========================================================

    private JPanel buildAllocPanel() {
        JPanel shell = sectionShell("\uD83D\uDD17  Allocation", "Assign students to buses with automatic capacity checking");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField stuF = field(); stuF.setPreferredSize(new Dimension(200, 36));
        JTextField busF = field(); busF.setPreferredSize(new Dimension(200, 36));
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("STUDENT ID", stuF), g);
        g.gridx=1;             formCard.add(fieldGroup("BUS NUMBER", busF), g);

        JPanel resultCard = card();
        resultCard.setLayout(new BorderLayout());
        resultCard.setPreferredSize(new Dimension(0, 120));
        JLabel resultLbl = new JLabel(
            "<html><center><span style='color:#6b7280;font-size:13px;'>"
            + "Enter a Student ID and Bus Number, then click Allocate"
            + "</span></center></html>", SwingConstants.CENTER);
        resultLbl.setFont(FONT_LABEL);
        resultCard.add(resultLbl, BorderLayout.CENTER);

        JButton allocBtn = btn("\uD83D\uDD17  Allocate Student to Bus", ACCENT);
        allocBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        center.add(formCard,    BorderLayout.NORTH);
        center.add(resultCard,  BorderLayout.CENTER);
        center.add(btnRow(allocBtn), BorderLayout.SOUTH);
        shell.add(center, BorderLayout.CENTER);

        allocBtn.addActionListener(e -> {
            try {
                int stuId  = Integer.parseInt(stuF.getText().trim());
                int busNum = Integer.parseInt(busF.getText().trim());
                if (Main.allocateStudentToBus(stuId, busNum)) {
                    Student s = Main.searchStudent(stuId);
                    Bus b     = Main.searchBus(busNum);
                    String html = "<html><center>"
                        + "<span style='color:#10b981;font-size:16px;'>\u2713 Allocated Successfully!</span><br><br>"
                        + "<span style='color:#f9fafb;'>Student: <b>" + (s != null ? s.getStudentName() : stuId) + "</b></span><br>"
                        + "<span style='color:#f9fafb;'>Bus: <b>" + (b != null ? b.getBusName() : "") + "  (Bus #" + busNum + ")</b></span><br><br>"
                        + "<span style='color:#6b7280;'>Seats: " + (b != null ? b.getStudentCount() + " / " + b.getCapacity() : "?") + "</span>"
                        + "</center></html>";
                    resultLbl.setText(html);
                    setStatus("Allocated student " + stuId + " to bus #" + busNum);
                } else {
                    resultLbl.setText("<html><center><span style='color:#ef4444;font-size:14px;'>\u274C Student or Bus not found.</span></center></html>");
                }
            } catch (TransportationException ex) {
                resultLbl.setText("<html><center><span style='color:#ef4444;font-size:14px;'>\u274C " + ex.getMessage() + "</span></center></html>");
                setStatus("Allocation failed \u2014 capacity exceeded.");
            } catch (NumberFormatException ex) {
                err("Enter valid numeric IDs.");
            }
        });

        return shell;
    }

    // =========================================================
    // SEARCH & SORT PANEL
    // =========================================================

    private JPanel buildSearchPanel() {
        JPanel shell = sectionShell("\uD83D\uDD0D  Search & Sort", "Find records by ID or name, and sort collections");
        JPanel formCard = card();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8); g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        JTextField stuIdF = field(); JTextField stuNmF = field();
        JTextField busF   = field(); JTextField routeF = field();
        g.gridx=0; g.gridy=0; formCard.add(fieldGroup("STUDENT ID",   stuIdF), g);
        g.gridx=1;             formCard.add(fieldGroup("STUDENT NAME", stuNmF), g);
        g.gridx=0; g.gridy=1; formCard.add(fieldGroup("BUS NUMBER",   busF),   g);
        g.gridx=1;             formCard.add(fieldGroup("ROUTE NUMBER", routeF), g);

        JTextArea resultArea = new JTextArea("Search results will appear here...");
        resultArea.setEditable(false);
        resultArea.setBackground(INPUT_BG);
        resultArea.setForeground(TEXT);
        resultArea.setFont(FONT_MONO);
        resultArea.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane resScroll = new JScrollPane(resultArea);
        resScroll.setBorder(BorderFactory.createLineBorder(BORDER_COL));
        resScroll.getViewport().setBackground(INPUT_BG);

        JButton byIdBtn    = btn("\uD83D\uDD0D  By Student ID", ACCENT);
        JButton byNmBtn    = btn("\uD83D\uDD0D  By Name",       new Color(99, 102, 241));
        JButton busBtn     = btn("\uD83D\uDE8C  Search Bus",    new Color(14, 165, 233));
        JButton routeBtn   = btn("\uD83D\uDDFA  Search Route",  new Color(245, 158, 11));
        JButton sortBusBtn = btn("\u21D5  Sort Buses",          STEEL);
        JButton sortRtBtn  = btn("\u21D5  Sort Routes",         STEEL);

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout(0, 12)); top.setOpaque(false);
        top.add(formCard, BorderLayout.CENTER);
        top.add(btnRow(byIdBtn, byNmBtn, busBtn, routeBtn, sortBusBtn, sortRtBtn), BorderLayout.SOUTH);
        center.add(top, BorderLayout.NORTH);
        center.add(resScroll, BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);

        byIdBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(stuIdF.getText().trim());
                Student s = Main.searchStudent(id);
                resultArea.setText(s != null ? "STUDENT FOUND\n" + "─".repeat(40) + "\n" + s : "No student with ID " + id + " found.");
                setStatus("Searched student ID: " + id);
            } catch (NumberFormatException ex) { resultArea.setText("Enter a valid Student ID."); }
        });

        byNmBtn.addActionListener(e -> {
            String nm = stuNmF.getText().trim();
            if (nm.isEmpty()) { resultArea.setText("Enter a student name."); return; }
            Student s = Main.searchStudentByName(nm);
            resultArea.setText(s != null ? "STUDENT FOUND\n" + "─".repeat(40) + "\n" + s : "No student named '" + nm + "' found.");
            setStatus("Searched student name: " + nm);
        });

        busBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(busF.getText().trim());
                Bus b = Main.searchBus(num);
                if (b != null) {
                    StringBuilder sb = new StringBuilder("BUS FOUND\n" + "─".repeat(40) + "\n" + b + "\n\nPASSENGERS:\n");
                    Student[] ss = b.getStudents();
                    if (ss.length == 0) sb.append("  (No students assigned)");
                    else for (Student s : ss) sb.append("  \u2022 ").append(s.getStudentName()).append(" (ID ").append(s.getStudentId()).append(")\n");
                    resultArea.setText(sb.toString());
                } else resultArea.setText("Bus #" + num + " not found.");
                setStatus("Searched bus #" + num);
            } catch (NumberFormatException ex) { resultArea.setText("Enter a valid Bus Number."); }
        });

        routeBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(routeF.getText().trim());
                Route r = Main.searchRoute(num);
                if (r != null) {
                    StringBuilder sb = new StringBuilder("ROUTE FOUND\n" + "─".repeat(40) + "\n" + r + "\n\nSTOPS:\n");
                    if (r.getStops().isEmpty()) sb.append("  (No stops added)");
                    else for (Stop s : r.getStops()) sb.append("  \u2022 ").append(s.getStopName()).append("  [").append(s.getLocation()).append("]\n");
                    resultArea.setText(sb.toString());
                } else resultArea.setText("Route #" + num + " not found.");
                setStatus("Searched route #" + num);
            } catch (NumberFormatException ex) { resultArea.setText("Enter a valid Route Number."); }
        });

        sortBusBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("BUSES SORTED BY CAPACITY (ascending)\n" + "─".repeat(50) + "\n\n");
            ArrayList<Bus> sorted = Main.sortBusesByCapacity();
            if (sorted.isEmpty()) sb.append("No buses registered.");
            else for (int i = 0; i < sorted.size(); i++) sb.append((i + 1)).append(".  ").append(sorted.get(i)).append("\n");
            resultArea.setText(sb.toString()); setStatus("Sorted buses by capacity.");
        });

        sortRtBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("ROUTES SORTED BY NUMBER\n" + "─".repeat(50) + "\n\n");
            ArrayList<Route> sorted = Main.sortRoutesByNumber();
            if (sorted.isEmpty()) sb.append("No routes registered.");
            else for (int i = 0; i < sorted.size(); i++) sb.append((i + 1)).append(".  ").append(sorted.get(i)).append("\n");
            resultArea.setText(sb.toString()); setStatus("Sorted routes by number.");
        });

        return shell;
    }

    // =========================================================
    // HISTORY & REPORT PANEL
    // =========================================================

    private JPanel buildHistoryPanel() {
        JPanel shell = sectionShell("\uD83D\uDCCB  History & Report", "View transportation history and generate system reports");
        JTextArea area = new JTextArea("Click a button below to view history or generate a report...");
        area.setEditable(false);
        area.setBackground(INPUT_BG);
        area.setForeground(TEXT);
        area.setFont(FONT_MONO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JScrollPane sp = new JScrollPane(area);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COL));
        sp.getViewport().setBackground(INPUT_BG);

        JButton histBtn   = btn("\uD83D\uDCCB  Travel History",   ACCENT);
        JButton reportBtn = btn("\uD83D\uDCCA  Transport Report", new Color(14, 165, 233));
        JButton clearBtn  = btn("\uD83D\uDDD1  Clear",            STEEL);

        JPanel center = new JPanel(new BorderLayout(0, 16)); center.setOpaque(false);
        center.add(btnRow(histBtn, reportBtn, clearBtn), BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        shell.add(center, BorderLayout.CENTER);

        histBtn.addActionListener(e -> {
            java.util.LinkedList<String> hist = Main.getTravelHistory();
            StringBuilder sb = new StringBuilder("=== TRANSPORTATION HISTORY ===\n" + "\u2500".repeat(45) + "\n\n");
            if (hist.isEmpty()) sb.append("No history recorded yet.\nAllocate students to buses to generate history.");
            else for (int i = 0; i < hist.size(); i++) sb.append((i + 1)).append(".  ").append(hist.get(i)).append("\n");
            area.setText(sb.toString()); setStatus("Loaded travel history \u2014 " + hist.size() + " record(s).");
        });

        reportBtn.addActionListener(e -> { area.setText(Main.generateTransportReport()); setStatus("Generated transport report."); });
        clearBtn.addActionListener(e ->  { area.setText(""); setStatus("Cleared display."); });

        return shell;
    }
}
