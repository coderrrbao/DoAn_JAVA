package ui.component;

import java.util.ArrayList;
import dto.SanPham;

public interface BoLocListener {
    void onLoc(ArrayList<SanPham> ds);

    void onLamMoi();
}
