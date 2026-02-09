package util;

public class Luong {
    public static void handleDatabaseTask(Runnable dbTask, Runnable uiTask) {
        new Thread(() -> {
            try {
                if (dbTask != null) {
                    dbTask.run();
                }
                if (uiTask != null) {
                    javax.swing.SwingUtilities.invokeLater(uiTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
