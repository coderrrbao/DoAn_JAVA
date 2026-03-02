package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;

public class Anh {
    public static String luuAnhSP(String maSp, JFileChooser fileChooser) {
        String duongDanMoi;
        try {
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                file = new File(System.getProperty("user.dir") + "/src/assets/img/douongmd.png");
            }
            Path path = Paths.get("src/assets/img/");
            duongDanMoi = maSp;
            duongDanMoi += file.getName().substring(file.getName().lastIndexOf("."));
            Path pathDich = path.resolve(duongDanMoi);
            Files.copy(file.toPath(), pathDich, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "/assets/img/" + duongDanMoi;
    }

    public static String luuAnhNV(String maSp, JFileChooser fileChooser) {
        String duongDanMoi;
        try {
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                file = new File(System.getProperty("user.dir") + "/src/assets/img/douongmd.png");
            }
            Path path = Paths.get("src/assets/img/nhanvien/");
            duongDanMoi = maSp;
            duongDanMoi += file.getName().substring(file.getName().lastIndexOf("."));
            Path pathDich = path.resolve(duongDanMoi);
            Files.copy(file.toPath(), pathDich, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "/assets/img/nhanvien/" + duongDanMoi;
    }
}
