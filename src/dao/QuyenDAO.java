package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.conection.DBConnection;
import dto.Quyen;

public class QuyenDAO {
    //them 
    public boolean themQuyen_Dao(Quyen quyen){
        String sql = "INSERT INTO Quyen (MaQuyen, MaNQ, TenQuyen) VALUES(?,?,?)";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, quyen.getMaQuyen());
            ps.setString(2, quyen.getMaNQ());
            ps.setString(3, quyen.getTenQuyen());

            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //xoa 
    public boolean xoaQuyen_Dao(String maQuyen){
        String sql = "DELETE FROM Quyen WHERE maQuyen = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, maQuyen);

            return ps.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //tim kiem theo ten
    public boolean timQuyen_Dao(String tenQuyen){
        String sql = "SELECT 1 FROM Quyen WHERE TenQuyen LIKE ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, "%" + tenQuyen + "%");

            return ps.executeQuery().next(); // có dòng => true
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }   
}
