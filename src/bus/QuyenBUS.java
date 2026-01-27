package bus;

import dao.QuyenDAO;
import dto.Quyen;

public class QuyenBUS {
    QuyenDAO quyenDao = new QuyenDAO();
    //them quyen
    public boolean themQuyen_BUS(Quyen quyen){
        if(quyen == null || quyen.getMaNQ() == null || quyen.getMaNQ().isEmpty() ||
            quyen.getMaQuyen() == null || quyen.getMaQuyen().isEmpty() ||
            quyen.getTenQuyen() == null || quyen.getTenQuyen().isEmpty()
            ){
            return false;
        }
        return quyenDao.themQuyen_Dao(quyen);
    }
    //xoa quyen
    public boolean xoaQuyen_BUS(String maQuyen){
        if(maQuyen == null || maQuyen.isEmpty()){
            return false;
        }
        return quyenDao.xoaQuyen_Dao(maQuyen);
    }
    //tim kiem 
    public boolean timQuyen_BUS(String tenQuyen){
        if(tenQuyen == null || tenQuyen.trim().isEmpty()){
            return false;
        }
        return quyenDao.timQuyen_Dao(tenQuyen.trim());
    }
}
