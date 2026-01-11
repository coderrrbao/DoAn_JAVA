package ui.banhang;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import util.TaoUI;

public class BoLocPanel extends JPanel {
    private JTextField searchTextField;

    public BoLocPanel() {
        searchTextField = new JTextField();
        TaoUI.taoPanelBoxLayoutDoc(this, Integer.MAX_VALUE, 150);
        TaoUI.suaBorderChoPanel(this, 0, 10, 10, 10);
        JPanel titleMain = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titleMain.add(new JLabel("Bộ lọc"));
        add(titleMain);

        JPanel topPanel = TaoUI.taoPanelFlowLayout(Integer.MAX_VALUE, 60, 5, 0);

        JPanel inputSearch = TaoUI.taoPanelBoxLayoutDoc(200, Integer.MAX_VALUE);
        JLabel titleSearch = new JLabel("Tên sản phẩm");
        JPanel titlePanel = TaoUI.taoPanelCanGiua(Integer.MAX_VALUE, 30);
        titlePanel.add(titleSearch);
        inputSearch.add(titlePanel);

        JPanel searchArea = TaoUI.taoPanelBorderLayout(200, 25);
        searchArea.add(searchTextField, BorderLayout.CENTER);

        inputSearch.add(searchArea);
        topPanel.add(inputSearch);

        JPanel buttonsPanel = TaoUI.taoPanelBoxLayoutNgang(Integer.MAX_VALUE, 30);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        buttonsPanel.add(new JButton("Áp dụng"));
        buttonsPanel.add(new JButton("Làm mới"));
        add(topPanel);
        add(buttonsPanel);

        JPanel loaiSanPhamPanel = TaoUI.taoPanelBoxLayoutDoc(100, Integer.MAX_VALUE);
        JPanel titleLoaiSp = TaoUI.taoPanelBoxLayoutNgang(Integer.MAX_VALUE, 30);
        titleLoaiSp.add(new JLabel("Loại sản phẩm"));
        loaiSanPhamPanel.add(titleLoaiSp);
        String[] loai = { "Nguyễn Hoài Bảo", "Bảo 123", "BẢO" };
        JComboBox<String> loaiSpComboBox = new JComboBox<>(loai);
        loaiSpComboBox.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
        loaiSpComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        loaiSanPhamPanel.add(loaiSpComboBox);
        loaiSpComboBox.setFont(new Font("Arial",Font.PLAIN,10));


        JPanel nhaCungCapPanel = TaoUI.taoPanelBoxLayoutDoc(100, Integer.MAX_VALUE);
        JPanel titleNCC = TaoUI.taoPanelBoxLayoutNgang(Integer.MAX_VALUE, 30);
        titleNCC.add(new JLabel("Nhà cung cấp"));
        nhaCungCapPanel.add(titleNCC);
        JComboBox<String> nhaCCComboBox = new JComboBox<>(loai);
        nhaCCComboBox.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
        nhaCCComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        nhaCungCapPanel.add(nhaCCComboBox);
        nhaCCComboBox.setFont(new Font("Arial",Font.PLAIN,10));

        topPanel.add(loaiSanPhamPanel);
        topPanel.add(nhaCungCapPanel);
    }
}
