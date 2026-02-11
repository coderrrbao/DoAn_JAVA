import javax.swing.UIManager;

import dao.conection.DatabaseInit;
import ui.login.LoginUI;
import ui.main.MainFrame;
import java.awt.Color;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        DatabaseInit.initDatabase();
        // new LoginUI();
        System.setProperty("flatlaf.uiScale", "1.0"); 
        UIManager.put("Table.selectionBackground", new Color(184, 207, 229));
        UIManager.put("Table.selectionForeground", Color.BLACK);
        com.formdev.flatlaf.FlatLightLaf.setup();

        EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
