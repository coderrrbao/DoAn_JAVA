
package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class QuanLySanPhamUI extends JPanel {

    private JPanel taoItemSanPham() {
        JPanel sanPham = new JPanel();
        Dimension size = new Dimension(150, 200);
        sanPham.setPreferredSize(size);
        sanPham.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/assets/img/pepsi.png"));
        Image img1 = icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        JLabel anh = new JLabel(avata);
        anh.setAlignmentX(CENTER_ALIGNMENT);

        sanPham.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sanPham.setLayout(new BoxLayout(sanPham, BoxLayout.Y_AXIS));
        sanPham.add(anh);
        JLabel tenSp = new JLabel("Pepsi");
        tenSp.setAlignmentX(CENTER_ALIGNMENT);

        JLabel gia = new JLabel("9999999đ");
        gia.setAlignmentX(CENTER_ALIGNMENT);
        gia.setForeground(Color.red);
        sanPham.add(tenSp);
        sanPham.add(gia);

        sanPham.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JDialog chiTiet = new JDialog((JFrame) null, "Chi tiết sản phẩm", true);

                chiTiet.setSize(500, 700);
                chiTiet.setLocationRelativeTo(null);
                chiTiet.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                chiTiet.setVisible(true);

            }
        });

        return sanPham;
    }

    public QuanLySanPhamUI() {

        setLayout(new BorderLayout());
        JPanel topContent = new JPanel();
        topContent.setPreferredSize(new Dimension(0, 45));
        topContent.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton loc = new JButton("Lọc");
        loc.setPreferredSize(new Dimension(loc.getPreferredSize().width, 35));
        topContent.add(loc);
        JTextField timKiem = new JTextField();

        timKiem.setToolTipText("Nhấn vào đây để tìm kiếm sản phẩm");
        timKiem.setPreferredSize(new Dimension(300, 35));
        timKiem.setFont(new Font("Arial", Font.PLAIN, 17));
        topContent.add(timKiem);
        JButton lamMoi = new JButton("làm mới");
        lamMoi.setPreferredSize(new Dimension(lamMoi.getPreferredSize().width, 35));
        topContent.add(lamMoi);
        add(topContent, BorderLayout.NORTH);
        JButton tao = new JButton("Tạo mới");
        tao.setPreferredSize(new Dimension(tao.getPreferredSize().width, 35));
        topContent.add(tao);
        JButton xoa = new JButton("Xóa");
        xoa.setPreferredSize(new Dimension(xoa.getPreferredSize().width, 35));
        topContent.add(xoa);
        JPanel danhSachSp = new JPanel();

        danhSachSp.setLayout(new GridLayout(0, 5, 10, 10));
        danhSachSp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        danhSachSp.setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < 18; i++) {
            danhSachSp.add(taoItemSanPham());
        }

        // bọc trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(danhSachSp);
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

        // thêm vào panel chính
        add(scrollPane, BorderLayout.CENTER);

    }


    
}
