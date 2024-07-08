package Components;

import Logic.EnrollmentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillingPanel extends JPanel {
    private final JTextField studentIdField;
    private final JCheckBox accommodationCheckBox;
    private final JTextArea billingArea;
    private final EnrollmentManager enrollmentManager;

    public BillingPanel(CardLayout cardLayout, JPanel cardPanel, EnrollmentManager enrollmentManager) {
        this.enrollmentManager = enrollmentManager;
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridLayout(1, 4));
        inputPanel.add(new JLabel("Actors.Student ID:"));
        studentIdField = new JTextField(10);
        inputPanel.add(studentIdField);

        accommodationCheckBox = new JCheckBox("Accommodation");
        inputPanel.add(accommodationCheckBox);

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateBill();
            }
        });
        inputPanel.add(generateBillButton);

        add(inputPanel, BorderLayout.NORTH);


        billingArea = new JTextArea();
        billingArea.setEditable(false);
        add(new JScrollPane(billingArea), BorderLayout.CENTER);


        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Dashboard");
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private void generateBill() {
        String studentIdText = studentIdField.getText();
        boolean hasAccommodation = accommodationCheckBox.isSelected();
        try {
            int studentId = Integer.parseInt(studentIdText);
            enrollmentManager.setStudentAccommodationStatus(studentId, hasAccommodation);
            String bill = enrollmentManager.generateBill(studentId);
            billingArea.setText(bill);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Actors.Student ID", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            billingArea.setText("Error generating bill: " + e.getMessage());
        }
    }
}

