package Modified;

import javax.swing.*;

import HW2.Subscription;
import HW2.systemDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePersonalData extends JFrame {
    private JTextField addressField, phoneField;
    public UpdatePersonalData(systemDataBase system, Subscription subscriber) {
        setTitle("Update Personal Data");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(3, 2));

        // Create labels and text fields for address and phone
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(20);

        // Add components to the frame
        add(addressLabel);
        add(addressField);
        add(phoneLabel);
        add(phoneField);

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText();
                String phone = phoneField.getText();

                // Update subscriber data
                subscriber.setAddress(address);
                subscriber.setPhone(phone);

                JOptionPane.showMessageDialog(UpdatePersonalData.this, "Information updated successfully.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(new JLabel()); // Empty label for alignment
        add(submitButton);
    }

}