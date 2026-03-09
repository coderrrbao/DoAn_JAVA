import dao.conection.DatabaseInit;
import java.awt.Color;admin
import javax.swing.UIManager;
import ui.login.LoginUI;

public class Main {
  public static void main(String[] args) {
    DatabaseInit.initDatabase();

    System.setProperty("flatlaf.uiScale", "1.0");
    UIManager.put("Table.selectionBackground", new Color(220, 220, 220));
    UIManager.put("Table.selectionForeground", Color.BLACK);
    com.formdev.flatlaf.FlatLightLaf.setup();
    new LoginUI();
    // EventQueue.invokeLater(
    // () -> {
    // new MainFrame(null).setVisible(true);
    // });
    // }
  }
}