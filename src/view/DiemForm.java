package view;

public class DiemForm extends CrudFrame {
    public DiemForm() {
        super("Quản lý điểm", "Diem",
            new String[]{"IDDiem","MaSV","MaMH","DiemQT","DiemThi"},
            new String[]{"ID điểm","Mã SV","Mã môn học","Điểm quá trình","Điểm thi"},
            true);
    }
}
