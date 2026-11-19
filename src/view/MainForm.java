
package view;

import java.awt.*;
import javax.swing.*;

public class MainForm extends JFrame {

    public MainForm() {
        setTitle("PHAN MEM QUAN LY SINH VIEN");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(25, 67, 112));

        JLabel title = new JLabel(
            "HE THONG QUAN LY SINH VIEN",
            SwingConstants.CENTER
        );
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitle = new JLabel(
            "JAVA SWING - SQL SERVER",
            SwingConstants.CENTER
        );
        subtitle.setForeground(Color.WHITE);

        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        JButton btnSV = new JButton("QUAN LY SINH VIEN");
        JButton btnMH = new JButton("QUAN LY MON HOC");
        JButton btnDiem = new JButton("QUAN LY DIEM");

        panel.add(btnSV);
        panel.add(btnMH);
        panel.add(btnDiem);

        add(panel, BorderLayout.CENTER);

        btnSV.addActionListener(e ->
            new SinhVienForm().setVisible(true)
        );

        btnMH.addActionListener(e ->
            new MonHocForm().setVisible(true)
        );

        btnDiem.addActionListener(e ->
            new DiemForm().setVisible(true)
        );
    }
}
