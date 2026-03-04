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
        // Thư mục đích: /src/assets/img/
        Path thuMucDich = Paths.get(duongDanDuAn, "src", "assets", "img");

        try {
            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(thuMucDich)) {
                Files.createDirectories(thuMucDich);
            }

            File fileNguon = fileChooser.getSelectedFile();
            // Nếu không chọn file, lấy file mặc định
            if (fileNguon == null) {
                fileNguon = new File(duongDanDuAn + "/src/assets/img/douongmd.png");
            }

            // Lấy phần mở rộng (ví dụ: .jpg, .png)
            String tenFileGoc = fileNguon.getName();
            String duoiFile = tenFileGoc.substring(tenFileGoc.lastIndexOf("."));

            // Tên file mới = Mã SP + đuôi file
            String tenFileMoi = maSp + duoiFile;
            Path duongDanDich = thuMucDich.resolve(tenFileMoi);

            // Copy đè nếu đã tồn tại
            Files.copy(fileNguon.toPath(), duongDanDich, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn tuyệt đối
            return duongDanDich.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String luuAnhNV(String maNV, JFileChooser fileChooser) {
        String duongDanDuAn = System.getProperty("user.dir");
        // Thư mục đích cho nhân viên
        Path thuMucDich = Paths.get(duongDanDuAn, "src", "assets", "img", "nhanvien");

        try {
            if (!Files.exists(thuMucDich)) {
                Files.createDirectories(thuMucDich);
            }

            File fileNguon = fileChooser.getSelectedFile();
            if (fileNguon == null) {
                // File mặc định cho nhân viên nếu không chọn
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
