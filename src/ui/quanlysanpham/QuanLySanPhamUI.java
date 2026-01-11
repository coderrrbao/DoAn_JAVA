
package ui.quanlysanpham;

import java.awt.BorderLayout;
import java.awt.Button;
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

import dto.SanPham;
import util.TaoUI;

public class QuanLySanPhamUI extends JPanel {

    private JPanel taoItemSanPham(SanPham sanPham) {
        JPanel sanPhamPanel = new JPanel();
        Dimension size = new Dimension(150, 200);
        sanPhamPanel.setPreferredSize(size);
        sanPhamPanel.setBackground(Color.WHITE);

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/assets/img/pepsi.png"));
        Image img1 = icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon avata = new ImageIcon(img1);
        JLabel anh = new JLabel(avata);
        anh.setAlignmentX(CENTER_ALIGNMENT);

        sanPhamPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sanPhamPanel.setLayout(new BoxLayout(sanPhamPanel, BoxLayout.Y_AXIS));
        sanPhamPanel.add(anh);
        JLabel tenSp = new JLabel("Pepsi");
        tenSp.setAlignmentX(CENTER_ALIGNMENT);

        JLabel gia = new JLabel("9999999đ");
        gia.setAlignmentX(CENTER_ALIGNMENT);
        gia.setForeground(Color.red);
        sanPhamPanel.add(tenSp);
        sanPhamPanel.add(gia);

        sanPhamPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JDialog chiTietDialog = new JDialog((JFrame) null, "Chi tiết sản phẩm", true);

                chiTietDialog.setSize(400, 650);
                chiTietDialog.setLocationRelativeTo(null);
                chiTietDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel chitietPanel = TaoUI.taoPanelBoxLayoutDoc(400, 650);
                chitietPanel = TaoUI.suaBorderChoPanel(chitietPanel, 0, 10, 10, 10);
                JLabel anh = TaoUI.taoJlabelAnh(sanPham.getAnh(), 200, 200);
                anh.setAlignmentX(CENTER_ALIGNMENT);
                JPanel buttons = TaoUI.taoPanelCanGiua(400, 30);
                buttons.add(new Button("Chọn ảnh"));
                buttons.add(new Button("Xóa ảnh"));

                // Hàng 1: Mã sản phẩm - Tên sản phẩm
                JPanel thongTin1 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                JTextField tfMaSanPham = new JTextField();
                JTextField tfTenSanPham = new JTextField();
                thongTin1.add(TaoUI.taoFieldText("Mã sản phẩm", 80, 80, 30, 5, tfMaSanPham));
                thongTin1.add(Box.createHorizontalGlue());
                thongTin1.add(TaoUI.taoFieldText("Tên sản phẩm", 80, 100, 30, 5, tfTenSanPham));

                // Hàng 2: Loại nước - Nhà cung cấp
                JPanel thongTin2 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                JTextField tfLoaiNuoc = new JTextField();
                JTextField tfNhaCungCap = new JTextField();
                thongTin2.add(TaoUI.taoFieldText("Loại nước", 80, 80, 30, 5, tfLoaiNuoc));
                thongTin2.add(Box.createHorizontalGlue());
                thongTin2.add(TaoUI.taoFieldText("Nhà cung cấp", 80, 100, 30, 5, tfNhaCungCap));

                // Hàng 3: Giá nhập - Giá bán
                JPanel thongTin3 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                JTextField tfGiaNhap = new JTextField();
                JTextField tfGiaBan = new JTextField();
                thongTin3.add(TaoUI.taoFieldText("Giá nhập", 80, 80, 30, 5, tfGiaNhap));
                thongTin3.add(Box.createHorizontalGlue());
                thongTin3.add(TaoUI.taoFieldText("Giá bán", 80, 100, 30, 5, tfGiaBan));

                // Hàng 4: Số lượng tồn - Dung tích
                JPanel thongTin4 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                JTextField tfSoLuongTon = new JTextField();
                JTextField tfDungTich = new JTextField();
                thongTin4.add(TaoUI.taoFieldText("Số lượng tồn", 80, 80, 30, 5, tfSoLuongTon));
                thongTin4.add(Box.createHorizontalGlue());
                thongTin4.add(TaoUI.taoFieldText("Dung tích (ml)", 80, 100, 30, 5, tfDungTich));

                // Hàng 5: Hạn sản xuất - Hạn sử dụng
                JPanel thongTin5 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                JTextField tfHanSanXuat = new JTextField();
                JTextField tfHanSuDung = new JTextField();
                thongTin5.add(TaoUI.taoFieldText("Hạn sản xuất", 80, 80, 30, 5, tfHanSanXuat));
                thongTin5.add(Box.createHorizontalGlue());
                thongTin5.add(TaoUI.taoFieldText("Hạn sử dụng", 80, 100, 30, 5, tfHanSuDung));

                // Hàng 6: Trạng thái - Đường dẫn ảnh
                JPanel thongTin6 = TaoUI.taoPanelBoxLayoutNgang(400, 35);
                String[] loai = { "Còn hàng", "Hết hàng", "Ngừng kinh doanh" };
                JComboBox<String> chonLoai = new JComboBox<>(loai);
                chonLoai.setPreferredSize(new Dimension(100, 35));
                chonLoai.setMaximumSize(new Dimension(200, 35));
                thongTin6.add(chonLoai);

                // // Hàng 7: Giới thiệu (Dàn ngang rộng hơn)
                JPanel thongTin7 = TaoUI.taoFieldArea("Giới thiệu sản phẩm", 400, 40, 60, 0);

                chitietPanel.add(anh);
                chitietPanel.add(buttons);
                chitietPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                chitietPanel.add(thongTin1);
                chitietPanel.add(thongTin2);
                chitietPanel.add(thongTin3);
                chitietPanel.add(thongTin4);
                chitietPanel.add(thongTin5);

                chitietPanel.add(thongTin6);

                chitietPanel.add(thongTin7);

                chiTietDialog.add(chitietPanel);
                chiTietDialog.setVisible(true);

            }
        });

        return sanPhamPanel;
    }

    public QuanLySanPhamUI() {

        setLayout(new BorderLayout());
        JPanel topContent = new JPanel();
        topContent.setPreferredSize(new Dimension(0, 45));
        topContent.setLayout(new FlowLayout(FlowLayout.LEFT));
        topContent.setBackground(Color.WHITE);
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
        danhSachSp.setBackground(new Color(238, 238, 238));

        for (int i = 0; i < 18; i++) {
            SanPham sanPham = new SanPham();
            sanPham.setAnh("/assets/img/pepsi.png");
            sanPham.setTen("pepsi");
            danhSachSp.add(taoItemSanPham(sanPham));
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
