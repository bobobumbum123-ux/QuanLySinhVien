package view;

public class MonHocForm extends CrudFrame {
    public MonHocForm() {
        super("Quản lý môn học", "MonHoc",
            new String[]{"MaMH","TenMH","SoTinChi"},
            new String[]{"Mã môn học","Tên môn học","Số tín chỉ"},
            false);
    }
}
