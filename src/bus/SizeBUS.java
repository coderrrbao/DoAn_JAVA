package bus;

import java.util.ArrayList;

import dao.SizeDAO;
import dto.Size;

public class SizeBUS {
    private ArrayList<Size> listSize = null;

    private SizeDAO sizeDAO = new SizeDAO();
    
    private boolean canUpdate = false;

    public SizeBUS(){
        listSize=sizeDAO.layListSize();
    }

    public ArrayList<Size>  laySizeChoSP(String ma){
        if (canUpdate || listSize==null){
            listSize=sizeDAO.layListSize();
            canUpdate=false;
        }
        ArrayList<Size> list=new ArrayList<>();
        for (Size size :listSize){
            if (size.getMaSP().equals(ma)){
                list.add(size);
            }
        }
        return list;
    }
    public Size timSize(String maSize){
        if (canUpdate || listSize==null){
            listSize=sizeDAO.layListSize();
            canUpdate=false;
        }
        for (Size size : listSize){
            if (size.getMaSize().equals(maSize)){
                return size;
            }
        }
        return null;
    }

    public Boolean themSize(Size size){
        canUpdate=true;
        return sizeDAO.themSize(size);
    }
    public Boolean xoaSize(Size  size){
        canUpdate=true;
        return sizeDAO.xoaSize(size);
    }
    public Boolean  capNhapSize(Size  size){
        canUpdate=true;
        return sizeDAO.capNhapSize(size);
    }
}
