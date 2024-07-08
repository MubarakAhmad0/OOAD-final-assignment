package Components;

import Logic.EnrollmentManager;
import Actors.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudentManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, emailField;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final EnrollmentManager enrollmentManager;

    public StudentManagementPanel(CardLayout cardLayout, JPanel cardPanel, EnrollmentManager enrollmentManager) {
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

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(10);
        formPanel.add(emailField);

        JButton addButton = new JButton("Add Actors.Student");
        addButton.addActionListener(this::addStudent);
        formPanel.add(addButton);

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(this::uploadFile);
        formPanel.add(uploadButton);

        return formPanel;
    }

    private JScrollPane createTable() {
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Email"});
        table.setModel(model);
        return new JScrollPane(table);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton saveToFileButton = new JButton("Save to File");
        saveToFileButton.addActionListener(this::saveStudentsToFile);
        bottomPanel.add(saveToFileButton, BorderLayout.WEST);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        bottomPanel.add(backButton, BorderLayout.EAST);

        return bottomPanel;
    }

    private void addStudent(ActionEvent e) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String email = emailField.getText();
        Student student = new Student(id, name, email);
        enrollmentManager.addStudent(student);
        model.addRow(new Object[]{id, name, email});
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
    }

    private void uploadFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        String email = data[2].trim();
                        Student student = new Student(id, name, email);
                        enrollmentManager.addStudent(student);
                        model.addRow(new Object[]{id, name, email});
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

    private void saveStudentsToFile(ActionEvent e) {
        try (FileWriter writer = new FileWriter("students.csv")) {
            writer.append("ID,Name,Email\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.append(model.getValueAt(i, 0).toString()).append(",")
                      .append(model.getValueAt(i, 1).toString()).append(",")
                      .append(model.getValueAt(i, 2).toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, "Students saved to students.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving students to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
