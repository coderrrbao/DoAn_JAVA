package bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.NhaCungCapDAO;
import dao.conection.DBConnection;
import dto.NhaCungCap;

public class NhaCungCapBUS {
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    public NhaCungCap timNhaCungCap(String ma) {
        return nhaCungCapDAO.timNhaCungCap(ma);
    }public NhaCungCap timNhaCungCapTheoTen(String ten) {
        return nhaCungCapDAO.timNhaCungCapTheoTen(ten);
    }
}
