package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;

public class Anh {
    public static String luuAnhSP(String maSp, JFileChooser fileChooser) {
        String duongDanDuAn = System.getProperty("user.dir");

        Path thuMucDich = Paths.get(duongDanDuAn, "src", "assets", "img");

        try {

            if (!Files.exists(thuMucDich)) {
                Files.createDirectories(thuMucDich);
            }

            File fileNguon = fileChooser.getSelectedFile();

            if (fileNguon == null) {
                fileNguon = new File(duongDanDuAn + "/src/assets/img/douongmd.png");
            }

            String tenFileGoc = fileNguon.getName();
            String duoiFile = tenFileGoc.substring(tenFileGoc.lastIndexOf("."));

            String tenFileMoi = maSp + duoiFile;
            Path duongDanDich = thuMucDich.resolve(tenFileMoi);

            Files.copy(fileNguon.toPath(), duongDanDich, StandardCopyOption.REPLACE_EXISTING);

            return duongDanDich.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String luuAnhNV(String maNV, JFileChooser fileChooser) {
        String duongDanDuAn = System.getProperty("user.dir");

        Path thuMucDich = Paths.get(duongDanDuAn, "src", "assets", "img", "nhanvien");

        try {
            if (!Files.exists(thuMucDich)) {
                Files.createDirectories(thuMucDich);
            }

            File fileNguon = fileChooser.getSelectedFile();
            if (fileNguon == null) {

                fileNguon = new File(duongDanDuAn + "/src/assets/img/nhanvien/default_user.png");
            }

            String tenFileGoc = fileNguon.getName();
            String duoiFile = tenFileGoc.substring(tenFileGoc.lastIndexOf("."));

            String tenFileMoi = maNV + duoiFile;
            Path duongDanDich = thuMucDich.resolve(tenFileMoi);

            Files.copy(fileNguon.toPath(), duongDanDich, StandardCopyOption.REPLACE_EXISTING);

            return duongDanDich.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
