import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    String getGrade() {
        if (marks >= 90) return "A";
        else if (marks >= 75) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 40) return "D";
        else return "F";
    }

    boolean isPass() {
        return marks >= 40;
    }
}

public class studentmarksheet extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;

    public studentmarksheet() {
        setTitle("Student Marksheet");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JTextField nameField = new JTextField();
        JTextField marksField = new JTextField();
        JButton addButton = new JButton("Add Student");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Marks:"));
        inputPanel.add(marksField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table for displaying students
        String[] columns = { "Name", "Marks", "Grade", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel for stats
        JLabel statsLabel = new JLabel("Average: N/A | Highest: N/A | Lowest: N/A");
        add(statsLabel, BorderLayout.SOUTH);

        // Add Button Listener
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String marksText = marksField.getText().trim();
            try {
                int marks = Integer.parseInt(marksText);
                Student s = new Student(name, marks);
                students.add(s);
                tableModel.addRow(new Object[]{
                    s.name, s.marks, s.getGrade(), s.isPass() ? "Pass" : "Fail"
                });
                updateStats(statsLabel);
                nameField.setText("");
                marksField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for marks.");
            }
        });

        setVisible(true);
    }

    private void updateStats(JLabel label) {
        if (students.isEmpty()) {
            label.setText("Average: N/A | Highest: N/A | Lowest: N/A");
            return;
        }

        int total = students.stream().mapToInt(s -> s.marks).sum();
        int max = students.stream().max(Comparator.comparingInt(s -> s.marks)).get().marks;
        int min = students.stream().min(Comparator.comparingInt(s -> s.marks)).get().marks;
        double avg = (double) total / students.size();

        label.setText(String.format("Average: %.2f | Highest: %d | Lowest: %d", avg, max, min));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(studentmarksheet::new);
    }
}
