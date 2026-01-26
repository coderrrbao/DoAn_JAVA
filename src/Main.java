import dao.conection.DatabaseInit;
import ui.login.LoginUI;

public class Main {
    public static void main(String[] args) {
        DatabaseInit.initDatabase();
        new LoginUI();
    }
}
