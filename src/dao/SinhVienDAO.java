
package dao;

import connect.DatabaseHelper;
import model.SinhVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {

    // 1. HIEN THI DANH SACH SINH VIEN
    public List<SinhVien> getAll() {

        List<SinhVien> list = new ArrayList<>();

        String sql = "SELECT * FROM SinhVien ORDER BY MaSV";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                SinhVien sv = new SinhVien();

                sv.setMaSV(rs.getString("MaSV"));
                sv.setHoTen(rs.getString("HoTen"));
                sv.setNgaySinh(rs.getDate("NgaySinh"));
                sv.setGioiTinh(rs.getString("GioiTinh"));
                sv.setDiaChi(rs.getString("DiaChi"));
                sv.setMaLop(rs.getString("MaLop"));

                list.add(sv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. THEM SINH VIEN
    public boolean insert(SinhVien sv) {

        String sql = "INSERT INTO SinhVien "
                + "(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());
            ps.setDate(3, sv.getNgaySinh());
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getDiaChi());
            ps.setString(6, sv.getMaLop());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Loi them sinh vien:");
            e.printStackTrace();
            return false;
        }
    }

    // 3. SUA THONG TIN SINH VIEN
    public boolean update(SinhVien sv) {

        String sql = "UPDATE SinhVien SET "
                + "HoTen = ?, "
                + "NgaySinh = ?, "
                + "GioiTinh = ?, "
                + "DiaChi = ?, "
                + "MaLop = ? "
                + "WHERE MaSV = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sv.getHoTen());
            ps.setDate(2, sv.getNgaySinh());
            ps.setString(3, sv.getGioiTinh());
            ps.setString(4, sv.getDiaChi());
            ps.setString(5, sv.getMaLop());
            ps.setString(6, sv.getMaSV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Loi sua sinh vien:");
            e.printStackTrace();
            return false;
        }
    }

    // 4. XOA SINH VIEN
    public boolean delete(String maSV) {

        String sql = "DELETE FROM SinhVien WHERE MaSV = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSV);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Loi xoa sinh vien:");
            e.printStackTrace();
            return false;
        }
    }

    // 5. TIM KIEM SINH VIEN
    public List<SinhVien> search(String keyword) {

        List<SinhVien> list = new ArrayList<>();

        String sql = "SELECT * FROM SinhVien "
                + "WHERE MaSV LIKE ? OR HoTen LIKE ? "
                + "ORDER BY MaSV";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String value = "%" + keyword + "%";

            ps.setString(1, value);
            ps.setString(2, value);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    SinhVien sv = new SinhVien();

                    sv.setMaSV(rs.getString("MaSV"));
                    sv.setHoTen(rs.getString("HoTen"));
                    sv.setNgaySinh(rs.getDate("NgaySinh"));
                    sv.setGioiTinh(rs.getString("GioiTinh"));
                    sv.setDiaChi(rs.getString("DiaChi"));
                    sv.setMaLop(rs.getString("MaLop"));

                    list.add(sv);
                }
            }

        } catch (SQLException e) {
            System.out.println("Loi tim kiem sinh vien:");
            e.printStackTrace();
        }

        return list;
    }
}
