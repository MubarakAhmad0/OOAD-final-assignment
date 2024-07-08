package Components;

import Actors.Course;
import Logic.EnrollmentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CourseManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, levelField, feeField;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final EnrollmentManager enrollmentManager;

    public CourseManagementPanel(CardLayout cardLayout, JPanel cardPanel, EnrollmentManager enrollmentManager) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.enrollmentManager = enrollmentManager;
        setLayout(new BorderLayout());
        add(createForm(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField(10);
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Level:"));
        levelField = new JTextField(10);
        formPanel.add(levelField);

        formPanel.add(new JLabel("Base Actors.Fee:"));
        feeField = new JTextField(10);
        formPanel.add(feeField);

        JButton addButton = new JButton("Add Actors.Course");
        addButton.addActionListener(this::addCourse);
        formPanel.add(addButton);

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(this::uploadFile);
        formPanel.add(uploadButton);

        return formPanel;
    }

    private JScrollPane createTable() {
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Level", "Base Actors.Fee"});
        table.setModel(model);
        return new JScrollPane(table);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton saveToFileButton = new JButton("Save to File");
        saveToFileButton.addActionListener(this::saveCoursesToFile);
        bottomPanel.add(saveToFileButton, BorderLayout.WEST);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        bottomPanel.add(backButton, BorderLayout.EAST);

        return bottomPanel;
    }

    private void addCourse(ActionEvent e) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String level = levelField.getText();
        double fee = Double.parseDouble(feeField.getText());
        Course course = new Course(id, name, level, fee);
        enrollmentManager.addCourse(course);
        model.addRow(new Object[]{id, name, level, fee});
        idField.setText("");
        nameField.setText("");
        levelField.setText("");
        feeField.setText("");
    }

    private void uploadFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 4) {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        String level = data[2].trim();
                        double fee = Double.parseDouble(data[3].trim());
                        Course course = new Course(id, name, level, fee);
                        enrollmentManager.addCourse(course);
                        model.addRow(new Object[]{id, name, level, fee});
                    } else {
                        System.out.println("Invalid line: " + line);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid data format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveCoursesToFile(ActionEvent e) {
        try (FileWriter writer = new FileWriter("courses.csv")) {
            writer.append("ID,Name,Level,Base Actors.Fee\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.append(model.getValueAt(i, 0).toString()).append(",")
                      .append(model.getValueAt(i, 1).toString()).append(",")
                      .append(model.getValueAt(i, 2).toString()).append(",")
                      .append(model.getValueAt(i, 3).toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, "Courses saved to courses.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving courses to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
