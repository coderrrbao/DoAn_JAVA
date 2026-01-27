package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.conection.DBConnection;
import dto.NhomQuyen;

public class NhomQuyenDAO {
    //lay list nhom quyen
    public ArrayList<NhomQuyen> layDanhSachNhomQuyen_Dao(){
        ArrayList<NhomQuyen> ds = new ArrayList<>();
        String sql = "SELECT MaNQ, TenNhomQuyen FROM NhomQuyen";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                NhomQuyen nq = new NhomQuyen();
                nq.setMaNQ(rs.getString("MaNQ"));
                nq.setTenNhomQuyen(rs.getString("TenNhomQuyen"));
                ds.add(nq);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ds;
    }
}
