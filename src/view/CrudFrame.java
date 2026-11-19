
package view;

import connect.DatabaseHelper;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class CrudFrame extends JFrame {
    private final String tableName;
    private final String[] columns;
    private final String[] labels;
    private final boolean identityFirst;
    private final JTextField[] inputs;
    private final JTextField txtSearch = new JTextField(22);
    private final DefaultTableModel model;
    private final JTable grid;

    public CrudFrame(String title, String tableName, String[] columns,
                     String[] labels, boolean identityFirst) {
        this.tableName = tableName;
        this.columns = columns;
        this.labels = labels;
        this.identityFirst = identityFirst;
        this.inputs = new JTextField[columns.length];

        setTitle(title);
        setSize(1100, 650);
        setMinimumSize(new Dimension(900, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        JLabel heading = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(new Color(28, 75, 125));
        heading.setBorder(BorderFactory.createEmptyBorder(18, 10, 8, 10));
        add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));

        JPanel form = new JPanel(new GridLayout(0, 4, 10, 9));
        form.setBorder(BorderFactory.createTitledBorder("THÔNG TIN"));

        for (int i = 0; i < columns.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(new Font("Arial", Font.BOLD, 13));
            label.setForeground(Color.BLACK);
            form.add(label);

            inputs[i] = new JTextField();
            inputs[i].setFont(new Font("Arial", Font.PLAIN, 14));
            inputs[i].setForeground(Color.BLACK);
            inputs[i].setBackground(Color.WHITE);
            inputs[i].setCaretColor(Color.BLACK);
            inputs[i].setDisabledTextColor(Color.BLACK);

            if (identityFirst && i == 0) {
                inputs[i].setEditable(false);
                inputs[i].setBackground(new Color(238, 238, 238));
            }

            form.add(inputs[i]);
        }

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton add = button("THÊM", new Color(35, 145, 85));
        JButton update = button("SỬA", new Color(40, 110, 180));
        JButton delete = button("XÓA", new Color(190, 65, 65));
        JButton clear = button("LÀM MỚI", new Color(100, 105, 115));
        JButton search = button("TÌM KIẾM", new Color(225, 145, 35));

        JLabel searchLabel = new JLabel("Từ khóa:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchLabel.setForeground(Color.BLACK);

        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setForeground(Color.BLACK);
        txtSearch.setBackground(Color.WHITE);
        txtSearch.setCaretColor(Color.BLACK);

        actions.add(add);
        actions.add(update);
        actions.add(delete);
        actions.add(clear);
        actions.add(searchLabel);
        actions.add(txtSearch);
        actions.add(search);

        model = new DefaultTableModel(labels, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        // Cấu hình bảng dữ liệu
        grid = new JTable(model);

        grid.setFont(new Font("Arial", Font.PLAIN, 13));
        grid.setForeground(Color.BLACK);
        grid.setBackground(Color.WHITE);
        grid.setSelectionForeground(Color.BLACK);
        grid.setSelectionBackground(new Color(200, 220, 245));
        grid.setRowHeight(28);
        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid.setAutoCreateRowSorter(true);

        // Ép chữ trong các ô dữ liệu thành màu đen
        grid.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
                );

                c.setForeground(Color.BLACK);

                if (isSelected) {
                    c.setBackground(new Color(200, 220, 245));
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        // CẤU HÌNH TIÊU ĐỀ 7 CỘT - CHỮ MÀU ĐEN
        JTableHeaderRenderer();

        center.add(form, BorderLayout.NORTH);
        center.add(new JScrollPane(grid), BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        add.addActionListener(e -> insertRecord());
        update.addActionListener(e -> updateRecord());
        delete.addActionListener(e -> deleteRecord());
        clear.addActionListener(e -> clearForm());
        search.addActionListener(e -> loadData(txtSearch.getText().trim()));
        txtSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));

        grid.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && grid.getSelectedRow() >= 0) {
                int row = grid.convertRowIndexToModel(grid.getSelectedRow());

                for (int i = 0; i < columns.length; i++) {
                    Object value = model.getValueAt(row, i);
                    inputs[i].setText(value == null ? "" : value.toString());
                }
            }
        });

        loadData("");
    }

    // Chỉnh màu tiêu đề các cột
    private void JTableHeaderRenderer() {
        grid.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        grid.getTableHeader().setForeground(Color.BLACK);
        grid.getTableHeader().setBackground(new Color(220, 230, 240));
        grid.getTableHeader().setOpaque(true);
        grid.getTableHeader().setReorderingAllowed(false);
    }

    // Tạo nút chức năng
    private JButton button(String text, Color color) {
        JButton b = new JButton(text);

        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(color);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(110, 38));
        b.setMargin(new Insets(5, 10, 5, 10));

        return b;
    }

    // Tải dữ liệu từ SQL Server
    private void loadData(String keyword) {
        model.setRowCount(0);

        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", columns))
           .append(" FROM dbo.")
           .append(tableName);

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" WHERE ");

            for (int i = 0; i < columns.length; i++) {
                if (i > 0) sql.append(" OR ");

                sql.append("CONVERT(NVARCHAR(255), ")
                   .append(columns[i])
                   .append(") LIKE ?");
            }
        }

        sql.append(" ORDER BY ").append(columns[0]);

        try (Connection c = DatabaseHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.isEmpty()) {
                for (int i = 1; i <= columns.length; i++) {
                    ps.setString(i, "%" + keyword + "%");
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[columns.length];

                    for (int i = 0; i < columns.length; i++) {
                        row[i] = rs.getObject(columns[i]);
                    }

                    model.addRow(row);
                }
            }

        } catch (SQLException ex) {
            showError("Không tải được dữ liệu.\n" + ex.getMessage());
        }
    }

    // Kiểm tra dữ liệu nhập
    private boolean validateInput() {
        for (int i = identityFirst ? 1 : 0; i < inputs.length; i++) {
            if (inputs[i].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập " + labels[i] + "."
                );

                inputs[i].requestFocus();
                return false;
            }
        }

        return true;
    }

    // Thêm dữ liệu
    private void insertRecord() {
        if (!validateInput()) return;

        java.util.List<String> insertCols = new ArrayList<>();

        for (int i = identityFirst ? 1 : 0; i < columns.length; i++) {
            insertCols.add(columns[i]);
        }

        String sql = "INSERT INTO dbo." + tableName
                   + " (" + String.join(",", insertCols) + ")"
                   + " VALUES ("
                   + String.join(",", Collections.nCopies(insertCols.size(), "?"))
                   + ")";

        executeWrite(sql, false);
    }

    // Sửa dữ liệu
    private void updateRecord() {
        if (grid.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Hãy chọn một dòng cần sửa trong bảng."
            );
            return;
        }

        if (!validateInput()) return;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE dbo.")
           .append(tableName)
           .append(" SET ");

        for (int i = 1; i < columns.length; i++) {
            if (i > 1) sql.append(", ");
            sql.append(columns[i]).append("=?");
        }

        sql.append(" WHERE ").append(columns[0]).append("=?");

        executeWrite(sql.toString(), true);
    }

    // Xóa dữ liệu
    private void deleteRecord() {
        if (grid.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Hãy chọn một dòng cần xóa trong bảng."
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa bản ghi này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM dbo." + tableName
                   + " WHERE " + columns[0] + "=?";

        try (Connection c = DatabaseHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, inputs[0].getText().trim());

            int n = ps.executeUpdate();

            JOptionPane.showMessageDialog(
                this,
                n > 0 ? "Đã xóa thành công." : "Không tìm thấy bản ghi."
            );

            clearForm();
            loadData("");

        } catch (SQLException ex) {
            showError(
                "Không xóa được. Có thể dữ liệu đang được bảng khác tham chiếu.\n"
                + ex.getMessage()
            );
        }
    }

    // Thực hiện thêm hoặc sửa
    private void executeWrite(String sql, boolean update) {
        try (Connection c = DatabaseHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int p = 1;
            int first = update ? 1 : (identityFirst ? 1 : 0);

            for (int i = first; i < columns.length; i++) {
                ps.setString(p++, inputs[i].getText().trim());
            }

            if (update) {
                ps.setString(p, inputs[0].getText().trim());
            }

            int n = ps.executeUpdate();

            JOptionPane.showMessageDialog(
                this,
                n > 0
                    ? (update ? "Cập nhật thành công." : "Thêm thành công.")
                    : "Không có dữ liệu được thay đổi."
            );

            clearForm();
            loadData("");

        } catch (SQLException ex) {
            showError(
                "Thao tác thất bại. Hãy kiểm tra dữ liệu và ràng buộc SQL.\n"
                + ex.getMessage()
            );
        }
    }

    // Xóa nội dung các ô nhập
    private void clearForm() {
        for (int i = 0; i < inputs.length; i++) {
            if (!(identityFirst && i == 0)) {
                inputs[i].setText("");
            }
        }

        grid.clearSelection();
        txtSearch.setText("");
    }

    // Hiển thị thông báo lỗi
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
