import dao.conection.DatabaseInit;
import ui.main.MainFrame;

public class Main {
    public static void main(String[] args) {
        DatabaseInit.initDatabase();
        MainFrame main = new MainFrame();
        main.loadDuLieu();
        
    }
}
