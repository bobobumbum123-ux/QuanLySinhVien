
package view;

public class SinhVienForm extends CrudFrame {

    public SinhVienForm() {
        super(
            "Quản lý sinh viên",
            "SinhVien",

            new String[]{
                "MaSV",
                "HoTen",
                "NgaySinh",
                "GioiTinh",
                "DiaChi",
                "MaLop",
                "Email"
            },

            new String[]{
                "Mã sinh viên",
                "Họ tên",
                "Ngày sinh (yyyy-MM-dd)",
                "Giới tính",
                "Địa chỉ",
                "Mã lớp",
                "Email"
            },

            false
        );
    }
}

