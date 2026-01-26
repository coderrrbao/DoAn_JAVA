package util;

import javax.swing.*;

public class TaoTinNhan {
    public static void showAutoCloseMessage(String message, String title, int seconds) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = optionPane.createDialog(title);
        Timer timer = new Timer(seconds * 1000, e -> {
            dialog.dispose(); // Tự động đóng dialog
        });

        timer.setRepeats(false); // Chỉ chạy 1 lần
        timer.start();

        dialog.setVisible(true); // Hiển thị dialog
    }
}