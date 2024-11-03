package library.management.DAO;

import library.management.database.KetNoiCSDL;
import library.management.entity.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO implements DAOInterface<Manager> {
    private ManagerDAO() {}

    public static ManagerDAO getInstance() {
        return new ManagerDAO();
    }

    @Override
    public int them(Manager manager) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "INSERT INTO manager (managerName, password, position, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, manager.getManagerName());
            stmt.setString(2, manager.getPassword());
            stmt.setString(3, manager.getPosition());
            stmt.setString(4, manager.getEmail());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int xoa(Manager manager) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "DELETE FROM manager WHERE managerID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, manager.getManagerID());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int capNhat(Manager manager) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "UPDATE manager SET managerName = ?, password = ?, position = ?, email = ? WHERE managerID = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, manager.getManagerName());
            stmt.setString(2, manager.getPassword());
            stmt.setString(3, manager.getPosition());
            stmt.setString(4, manager.getEmail());
            stmt.setString(5, manager.getManagerID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkManager(String managerName, String password) {
        Connection con = KetNoiCSDL.getConnection();
        String query = "SELECT * FROM manager WHERE managerName = ? AND password = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, managerName);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true; // Nếu có kết quả, tức là thông tin đăng nhập hợp lệ
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu không tìm thấy kết quả
    }

}
