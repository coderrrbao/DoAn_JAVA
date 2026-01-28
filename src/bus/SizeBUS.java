package bus;

import java.util.ArrayList;

import dao.SizeDAO;
import dto.Size;

public class SizeBUS {
    private SizeDAO sizeDAO = new SizeDAO();
    public ArrayList<Size>  laySizeChoSP(String ma){
        return sizeDAO.layListSizeChoSP(ma);
    }
    public Boolean themSize(Size size){
        return sizeDAO.themSize(size);
    }
    public Boolean xoaSize(Size  size){
        return sizeDAO.xoaSize(size);
    }
    public Boolean  capNhapSize(Size  size){
        return sizeDAO.capNhapSize(size);
    }
}
