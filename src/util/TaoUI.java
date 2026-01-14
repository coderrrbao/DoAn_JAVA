package util;

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
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;

public class TaoUI {

    public static JLabel taoJlabelAnh(String src, int rong, int dai) {
        ImageIcon icon1 = new ImageIcon(TaoUI.class.getResource(src));
        Image img1 = icon1.getImage().getScaledInstance(rong, dai, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        return new JLabel(avata);
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
        button.setFocusPainted(false); // Xóa viền xanh khi click
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hiện bàn tay khi di chuột vào

        return button;
    }

    public static ImageIcon taoAnhBoTron(ImageIcon icon, int radius) {
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        // Tạo một ảnh đệm có độ trong suốt
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();

        // Bật chế độ khử răng cưa để đường tròn mượt mà
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ khuôn hình tròn (hoặc hình vuông bo góc)
        g2.setClip(new Ellipse2D.Float(0, 0, width, height));
        g2.drawImage(icon.getImage(), 0, 0, width, height, null);

        g2.dispose();
        return new ImageIcon(bi);
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
        // Đặt kích thước ưu tiên (Dùng cho FlowLayout, BorderLayout,...)
        c.setPreferredSize(d);
        // Đặt kích thước tối đa (Dùng cho BoxLayout)
        c.setMaximumSize(d);
        // Đặt kích thước tối thiểu
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

        // Căn giữa thành phần
        gbc.anchor = GridBagConstraints.CENTER;

        // Thiết lập vị trí:
        // Nếu là hàng ngang (Horizontal): x sẽ tự tăng, y giữ nguyên 0
        // Nếu là hàng dọc (Vertical): y sẽ tự tăng, x giữ nguyên 0
        int count = container.getComponentCount();
        if (isHorizontal) {
            gbc.gridx = count;
            gbc.gridy = 0;
            // Thêm khoảng cách bên trái từ item thứ 2 trở đi
            if (count > 0)
                gbc.insets = new Insets(0, gap, 0, 0);
        } else {
            gbc.gridx = 0;
            gbc.gridy = count;
            // Thêm khoảng cách bên trên từ item thứ 2 trở đi
            if (count > 0)
                gbc.insets = new Insets(gap, 0, 0, 0);
        }

        container.add(component, gbc);
        container.revalidate();
        container.repaint();
    }

    public static JPanel taoFieldArea(String labelText, int width, int heightLabel, int heightArea, int gap) {

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
        JTextArea area = new JTextArea();
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
                return new Dimension(5, 30); // chiều rộng 3, chiều cao tối thiểu 30
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

        // ===== NỀN + GRID (NHẸ MẮT) =====
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(220, 220, 220));
        plot.setDomainGridlinePaint(new Color(220, 220, 220));

        // ===== CỘT (GỌN + ĐẸP) =====
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.12); // mỏng hơn
        renderer.setItemMargin(0.15); // khoảng cách giữa các cột
        renderer.setDrawBarOutline(false);

        // ===== KHOẢNG CÁCH TRỤC X =====
        plot.getDomainAxis().setCategoryMargin(0.25);

        // ===== CHỐNG RĂNG CƯA =====
        chart.setAntiAlias(true);

        // ===== ADD VÀO PANEL =====
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setRangeZoomable(false); // Không cho zoom trục Y
        chartPanel.setDomainZoomable(false); // Không cho zoom trục X
        chartPanel.setMouseWheelEnabled(false); // Tắt tính năng lăn chuột để zoom
        return chartPanel;
    }

    public static ChartPanel taoBieuDoTron(
            String tenBieuDo,
            DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                tenBieuDo,
                dataset,
                true, // legend
                true,
                false);

        // ===== FONT =====
        Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 18);
        Font fontChu = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontLegend = new Font("Segoe UI", Font.PLAIN, 13);

        chart.getTitle().setFont(fontTieuDe);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(fontLegend);
        }

        PiePlot plot = (PiePlot) chart.getPlot();

        // ===== NỀN =====
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);

        // ===== LABEL TRONG BIỂU ĐỒ =====
        plot.setLabelFont(fontChu);
        plot.setLabelPaint(Color.DARK_GRAY);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);

        // Hiển thị: Tên + %
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {2}", // {0}=tên, {1}=giá trị, {2}=%
                new DecimalFormat("0"),
                new DecimalFormat("0.0%")));

        // ===== KHOẢNG CÁCH =====
        plot.setInteriorGap(0.04); // khoảng trống giữa pie và viền
        plot.setShadowPaint(null); // bỏ bóng (nhẹ mắt)

        // ===== CHỐNG RĂNG CƯA =====
        chart.setAntiAlias(true);

        // ===== ADD VÀO PANEL =====
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setRangeZoomable(false); // Không cho zoom trục Y
        chartPanel.setDomainZoomable(false); // Không cho zoom trục X
        chartPanel.setMouseWheelEnabled(false); // Tắt tính năng lăn chuột để zoom
        return chartPanel;
    }

    public static JScrollPane taoTableScroll(DefaultTableModel model) {
        // 1. Khởi tạo JTable
        JTable table = new JTable(model);

        // 2. Cấu hình giao diện bảng (Sử dụng Segoe UI cho hiện đại)
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setFillsViewportHeight(true); // Luôn lấp đầy vùng nhìn thấy

        // 3. Tùy chỉnh Tiêu đề cột (Header)
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setReorderingAllowed(false); // Không cho kéo đổi cột

        // 4. Căn giữa dữ liệu cho tất cả các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 5. Bọc bảng vào JScrollPane bằng hàm taoScrollPane CÓ SẴN trong package util
        JScrollPane scrollPane = TaoUI.taoScrollPane(table);

        // 6. Thiết lập kích thước cố định bằng hàm setFixSize CÓ SẴN
        scrollPane.setPreferredSize(new Dimension(800, 400));
        return scrollPane;
    }

    public static void setHeightButton(JButton button, int height) {
        // Lấy chiều rộng lý tưởng dựa trên nội dung (text, icon, margin)
        int preferredWidth = button.getPreferredSize().width;

        // Thiết lập kích thước mới với chiều cao tùy chỉnh
        button.setPreferredSize(new Dimension(preferredWidth+30, height));
        button.setMaximumSize(new Dimension(preferredWidth+30, height));
        // Đảm bảo layout manager cập nhật lại giao diện
        button.revalidate();
    }




public static ChartPanel taoBieuDoMien(String tenBieuDo, String tenTrucDoc, String tenTrucNgang,
                                       DefaultCategoryDataset dataset) {
    // 1. Tạo biểu đồ miền (Area Chart)
    JFreeChart chart = ChartFactory.createAreaChart(
            tenBieuDo, 
            tenTrucNgang, 
            tenTrucDoc, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, true, false);

    // ===== ĐỊNH DẠNG FONT =====
    Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 18);
    Font fontTruc = new Font("Segoe UI", Font.PLAIN, 14);
    Font fontTick = new Font("Segoe UI", Font.PLAIN, 13);

    chart.getTitle().setFont(fontTieuDe);
    if (chart.getLegend() != null) {
        chart.getLegend().setItemFont(fontTick);
    }

    // ===== TÙY CHỈNH PLOT =====
    CategoryPlot plot = chart.getCategoryPlot();
    plot.getDomainAxis().setLabelFont(fontTruc);
    plot.getDomainAxis().setTickLabelFont(fontTick);
    plot.getRangeAxis().setLabelFont(fontTruc);
    plot.getRangeAxis().setTickLabelFont(fontTick);

    // ===== NỀN + GRID (GIỮ GIỐNG BẢN CŨ CỦA BẠN) =====
    plot.setBackgroundPaint(Color.WHITE);
    plot.setRangeGridlinePaint(new Color(220, 220, 220));
    plot.setDomainGridlinePaint(new Color(220, 220, 220));
    plot.setOutlineVisible(false); // Ẩn đường viền khung biểu đồ cho hiện đại

    // ===== TÙY CHỈNH MIỀN (RENDERER) =====
    AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
    
    // Đặt màu cho miền (Ví dụ: Màu xanh lá cây của Bao Store với độ trong suốt 150/255)
    Color colorArea = new Color(76, 175, 80, 150); 
    renderer.setSeriesPaint(0, colorArea);
    
    // Giúp đường kẻ mượt mà hơn
    chart.setAntiAlias(true);

    // ===== TẠO CHART PANEL =====
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 400));
    chartPanel.setMouseWheelEnabled(false); // Tắt zoom chuột
    chartPanel.setDomainZoomable(false);
    chartPanel.setRangeZoomable(false);
    chartPanel.setBackground(Color.WHITE);

    return chartPanel;
}
}