import dao.conection.DatabaseInit;
import ui.login.LoginUI;
import ui.main.MainFrame;

public class Main {
    public static void main(String[] args) {
        DatabaseInit.initDatabase();
        // new LoginUI();
        MainFrame mainFrame= new MainFrame();
        mainFrame.setVisible(true);
    }
}
