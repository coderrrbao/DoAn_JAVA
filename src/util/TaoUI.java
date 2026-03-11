package util;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;

public class TaoUI {

    public static JLabel taoJlabelAnh(String src, int rong, int dai) {
        if (src == null) {
            src = "/assets/img/douongmd.png";
        }
        ImageIcon icon1 = new ImageIcon(TaoUI.class.getResource(src));

        Image img1 = icon1.getImage().getScaledInstance(rong, dai, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        JLabel lb = new JLabel(avata);
        setFixSize(lb, rong, dai);
        return lb;
    }

    public static ImageIcon taoImageIcon(String src, int rong, int dai) {
        if (src == null) {
            src = "";
        }
        URL url = TaoUI.class.getResource(src);
        if (url == null) {
            url = TaoUI.class.getResource("/assets/img/douongmd.png");
        }
        ImageIcon icon1 = new ImageIcon(url);
        Image img1 = icon1.getImage().getScaledInstance(rong, dai, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        return avata;
    }

    public static JLabel taoJlabelAnh_Svg(String src, int rong, int dai) {
        FlatSVGIcon icon = new FlatSVGIcon(TaoUI.class.getResource(src));
        FlatSVGIcon resizedIcon = icon.derive(rong, dai);

        return new JLabel(resizedIcon);
    }

    public static JButton taoJButton_Svg(String src, int size, int iconSize) {
        URL url = TaoUI.class.getResource(src);

        JButton button;
        if (url != null) {
            FlatSVGIcon icon = new FlatSVGIcon(url).derive(iconSize, iconSize);
            button = new JButton(icon);
        } else {
            System.err.println("Lỗi: Không tìm thấy file tại " + src);
            button = new JButton("?");
        }
        button.setPreferredSize(new Dimension(size, size));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static JPanel taoFieldText(String textLabel, int widthLabel, int widthInput, int height, int gap,
            JTextField input) {

        JPanel ctn = new JPanel();
        ctn.setLayout(new BoxLayout(ctn, BoxLayout.X_AXIS));

        ctn.setPreferredSize(new Dimension(widthInput + widthLabel + gap, height));

        JLabel label = new JLabel(textLabel);

        label.setPreferredSize(new Dimension(widthLabel, height));

        label.setMaximumSize(new Dimension(widthLabel, height));

        label.setMinimumSize(new Dimension(widthLabel, height));

        ctn.add(label);

        ctn.add(Box.createRigidArea(new Dimension(gap, 0)));

        input.setPreferredSize(new Dimension(widthInput, height));

        input.setMaximumSize(new Dimension(widthInput, height));

        input.setMinimumSize(new Dimension(widthInput, height));

        ctn.add(input);

        return ctn;

    }

    public static ImageIcon taoAnhBoTron(ImageIcon icon) {
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        int size = Math.min(width, height);

        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(0, 0, size, size));

        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(icon.getImage(), 0, 0, size, size, null);

        g2.dispose();
        return new ImageIcon(bi);
    }

    public static JPanel taoPanelBoxLayoutNgang(int width, int height) {

        JPanel ctn = new JPanel();

        ctn.setLayout(new BoxLayout(ctn, BoxLayout.X_AXIS));

        Dimension size = new Dimension(width, height);

        ctn.setPreferredSize(size);

        ctn.setMaximumSize(size);

        ctn.setMinimumSize(size);

        return ctn;

    }

    public static JPanel taoPanelBoxLayoutDoc(int width, int height) {

        JPanel ctn = new JPanel();

        Dimension size = new Dimension(width, height);

        ctn.setLayout(new BoxLayout(ctn, BoxLayout.Y_AXIS));

        ctn.setPreferredSize(size);

        ctn.setMaximumSize(size);

        ctn.setMinimumSize(size);

        return ctn;

    }

    public static void setFixSize(JComponent c, int width, int height) {
        Dimension d = new Dimension(width, height);

        c.setPreferredSize(d);

        c.setMaximumSize(d);

        c.setMinimumSize(d);
    }

    public static JPanel taoPanelFlowLayout(int width, int height, int align, int hgap, int vgap) {

        JPanel ctn = new JPanel();

        Dimension size = new Dimension(width, height);

        ctn.setLayout(new FlowLayout(align, hgap, vgap));

        ctn.setPreferredSize(size);

        ctn.setMaximumSize(size);

        ctn.setMinimumSize(size);

        return ctn;

    }

    public static JPanel taoPanelFlowLayout(int width, int height, int hgap, int vgap) {

        JPanel ctn = new JPanel();

        Dimension size = new Dimension(width, height);

        ctn.setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));

        ctn.setPreferredSize(size);

        ctn.setMaximumSize(size);

        ctn.setMinimumSize(size);

        return ctn;

    }

    public static JPanel suaBorderChoPanel(JPanel ctn, int top, int left, int bottom, int right) {

        ctn.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

        return ctn;

    }

    public static JPanel taoPanelCanGiua(int width, int height) {

        JPanel ctn = new JPanel();

        Dimension size = new Dimension(width, height);

        ctn.setLayout(new GridBagLayout());

        ctn.setPreferredSize(size);

        ctn.setMaximumSize(size);

        ctn.setMinimumSize(size);

        return ctn;

    }

    public static void addItem(JPanel container, Component component, int gap, boolean isHorizontal) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;

        int count = container.getComponentCount();
        if (isHorizontal) {
            gbc.gridx = count;
            gbc.gridy = 0;

            if (count > 0)
                gbc.insets = new Insets(0, gap, 0, 0);
        } else {
            gbc.gridx = 0;
            gbc.gridy = count;

            if (count > 0)
                gbc.insets = new Insets(gap, 0, 0, 0);
        }

        container.add(component, gbc);
        container.revalidate();
        container.repaint();
    }

    public static JPanel taoFieldArea(String labelText, int width, int heightLabel, int heightArea, int gap,
            JTextArea area) {

        JPanel ctn = new JPanel();

        JPanel fixCtn = TaoUI.taoPanelBoxLayoutNgang(width, heightLabel + gap + heightArea);

        fixCtn.add(ctn);

        ctn.setLayout(new BoxLayout(ctn, BoxLayout.Y_AXIS));

        Dimension pref = new Dimension(width, heightLabel + heightArea + gap);

        ctn.setPreferredSize(pref);

        ctn.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));

        ctn.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(labelText);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setPreferredSize(new Dimension(width, heightLabel));
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, heightLabel));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(width, heightArea));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, heightArea));
        ctn.add(lbl);
        ctn.add(Box.createVerticalStrut(gap));
        ctn.add(scrollPane);
        return fixCtn;

    }

    public static void taoPanelBoxLayoutDoc(JPanel ctn, int width, int height) {
        ctn.setLayout(new BoxLayout(ctn, BoxLayout.Y_AXIS));
        Dimension size = new Dimension(width, height);
        ctn.setPreferredSize(size);
        ctn.setMaximumSize(size);
        ctn.setMinimumSize(size);
    }

    public static void taoPanelFlowLayout(JPanel ctn, int width, int height, int align, int hgap, int vgap) {
        ctn.setLayout(new FlowLayout(align, hgap, vgap));
        Dimension size = new Dimension(width, height);
        ctn.setPreferredSize(size);
        ctn.setMaximumSize(size);
        ctn.setMinimumSize(size);
    }

    public static void taoPanelCanGiua(JPanel ctn, int width, int height) {
        ctn.setLayout(new GridBagLayout());
        Dimension size = new Dimension(width, height);
        ctn.setPreferredSize(size);
        ctn.setMaximumSize(size);
        ctn.setMinimumSize(size);
    }

    public static void taoPanelBoxLayoutNgang(JPanel ctn, int width, int height) {
        ctn.setLayout(new BoxLayout(ctn, BoxLayout.X_AXIS));
        Dimension size = new Dimension(width, height);
        ctn.setPreferredSize(size);
        ctn.setMaximumSize(size);
        ctn.setMinimumSize(size);
    }

    public static JPanel taoPanelBorderLayout(int width, int height) {
        JPanel ctn = new JPanel();
        taoPanelBorderLayout(ctn, width, height);
        return ctn;
    }

    public static void taoPanelBorderLayout(JPanel ctn, int width, int height) {
        Dimension size = new Dimension(width, height);
        ctn.setLayout(new BorderLayout());
        ctn.setPreferredSize(size);
        ctn.setMaximumSize(size);
        ctn.setMinimumSize(size);
    }

    public static JScrollPane taoScrollPane(Component item) {
        JScrollPane scrollPane = new JScrollPane(item);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected Dimension getMinimumThumbSize() {
                return new Dimension(5, 30);
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(5, Integer.MAX_VALUE));
        return scrollPane;
    }

    public static ChartPanel taoBieuDoCot(String tenBieuDo, String tenTrucDoc, String tenTrucNgang,
            DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(tenBieuDo, tenTrucNgang, tenTrucDoc, dataset);

        Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 18);
        Font fontTruc = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontTick = new Font("Segoe UI", Font.PLAIN, 10);

        chart.getTitle().setFont(fontTieuDe);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(fontTick);
        }

        CategoryPlot plot = chart.getCategoryPlot();
        plot.getDomainAxis().setLabelFont(fontTruc);
        plot.getDomainAxis().setTickLabelFont(fontTick);
        plot.getRangeAxis().setLabelFont(fontTruc);
        plot.getRangeAxis().setTickLabelFont(fontTick);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(220, 220, 220));
        plot.setDomainGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.12);
        renderer.setItemMargin(0.15);
        renderer.setDrawBarOutline(false);

        plot.getDomainAxis().setCategoryMargin(0.25);

        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        ChartPanel chartPanel = new ChartPanel(
                chart,
                800, 400,
                10, 10,
                3000, 3000,
                false,
                true, true, true, true, true);

        chartPanel.setMouseWheelEnabled(false);
        return chartPanel;
    }

    public static ChartPanel taoBieuDoTron(String tenBieuDo, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                tenBieuDo,
                dataset,
                true,
                true,
                false);

        Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 18);
        Font fontChu = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontLegend = new Font("Segoe UI", Font.PLAIN, 13);

        chart.getTitle().setFont(fontTieuDe);

        if (chart.getLegend() != null) {
            LegendTitle legend = chart.getLegend();
            legend.setItemFont(fontLegend);
            legend.setPosition(org.jfree.ui.RectangleEdge.RIGHT);
            legend.setBorder(0, 0, 0, 0);
        }

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);

        plot.setLabelFont(fontChu);
        plot.setLabelPaint(Color.DARK_GRAY);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {2}",
                new DecimalFormat("0"),
                new DecimalFormat("0.0%")));

        plot.setInteriorGap(0.15);
        plot.setCircular(true);

        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        ChartPanel chartPanel = new ChartPanel(
                chart,
                650, 350,
                10, 10,
                3000, 3000,
                false,
                true, true, true, true, true);

        chartPanel.setMouseWheelEnabled(false);
        return chartPanel;
    }

    public static JScrollPane taoTableScroll(DefaultTableModel model) {

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setFillsViewportHeight(true);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(new Color(230, 240, 250));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = TaoUI.taoScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(800, 400));
        table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
        return scrollPane;
    }

    public static JScrollPane taoTableScroll(DefaultTableModel model, HashSet<Integer> set) {

        JTable table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setBackground(new Color(230, 240, 250));
        table.getTableHeader().setOpaque(false);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (set.contains(i)) {
                continue;
            }
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = TaoUI.taoScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(800, 400));
        table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
        return scrollPane;
    }

    public static ChartPanel taoBieuDoMien(String tenBieuDo, String tenTrucDoc, String tenTrucNgang,
            DefaultCategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createAreaChart(
                tenBieuDo,
                tenTrucNgang,
                tenTrucDoc,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 18);
        Font fontTruc = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontTick = new Font("Segoe UI", Font.PLAIN, 13);

        chart.getTitle().setFont(fontTieuDe);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(fontTick);
        }

        CategoryPlot plot = chart.getCategoryPlot();
        plot.getDomainAxis().setLabelFont(fontTruc);
        plot.getDomainAxis().setTickLabelFont(fontTick);
        plot.getRangeAxis().setLabelFont(fontTruc);
        plot.getRangeAxis().setTickLabelFont(fontTick);

        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(220, 220, 220));
        plot.setDomainGridlinePaint(new Color(220, 220, 220));
        plot.setOutlineVisible(false);

        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();

        Color colorArea = new Color(76, 175, 80, 150);
        renderer.setSeriesPaint(0, colorArea);

        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);

        ChartPanel chartPanel = new ChartPanel(
                chart,
                800, 400,
                10, 10,
                3000, 3000,
                false,
                true, true, true, true, true);

        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setBackground(Color.WHITE);

        return chartPanel;
    }

    public static void setDisabled(JComponent comp) {
        comp.setEnabled(false);

        comp.setBackground(Color.WHITE);
        if (comp instanceof javax.swing.text.JTextComponent) {
            JTextComponent textComp = (javax.swing.text.JTextComponent) comp;
            textComp.setDisabledTextColor(Color.BLACK);
        }
    }
}