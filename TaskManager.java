import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Task {
    String title;
    String description;
    String dueDate;
    String priority;
    boolean completed;
    String completedOn;

    Task(String title, String description, String dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
        this.completedOn = "Not completed";
    }

    void markCompleted() {
        this.completed = true;
        this.completedOn = new Date().toString();
    }

    Object[] toRow() {
        return new Object[]{title, description, dueDate, priority, (completed ? "Completed" : "Pending"), completedOn};
    }
}

public class TaskManager extends JFrame {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final DefaultTableModel model;

    public TaskManager() {
        setTitle("Task Management System");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"Title", "Description", "Due Date", "Priority", "Status", "Completed On"};
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form Inputs
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        JTextField dateField = new JTextField();
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        JButton addButton = new JButton("Add Task");

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(new JLabel("Due Date:"));
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(new JLabel()); // empty

        inputPanel.add(titleField);
        inputPanel.add(descField);
        inputPanel.add(dateField);
        inputPanel.add(priorityBox);
        inputPanel.add(addButton);

        // Buttons
        JButton completeButton = new JButton("Mark as Completed");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(completeButton);

        // Add components
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add Task Action
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descField.getText();
            String date = dateField.getText();
            String priority = (String) priorityBox.getSelectedItem();

            if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            Task task = new Task(title, desc, date, priority);
            tasks.add(task);
            model.addRow(task.toRow());

            titleField.setText("");
            descField.setText("");
            dateField.setText("");
        });

        // Complete Task Action
        completeButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a task to mark as completed.");
                return;
            }

            Task task = tasks.get(row);
            if (!task.completed) {
                task.markCompleted();
                model.setValueAt("Completed", row, 4);
                model.setValueAt(task.completedOn, row, 5);
            } else {
                JOptionPane.showMessageDialog(this, "Task already completed.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManager::new);
    }
}
