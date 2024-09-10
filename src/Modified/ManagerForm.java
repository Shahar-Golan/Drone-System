package Modified;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import HW2.Manager;
import HW2.systemDataBase;

class ManagerForm extends JFrame {
    protected JTextField codeField, firstNameField, lastNameField;
    protected systemDataBase system;

    public ManagerForm(systemDataBase system, String managerType) {
        this.system = system;
        setTitle("Add " + managerType);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and text fields
        codeField = new JTextField(10);
        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);

        // Create button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String code = codeField.getText();
            try {
                if (code.isEmpty()) {
                    throw new IllegalArgumentException("Code cannot be empty");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }


            String firstName = firstNameField.getText();
            try {
                if (firstName.isEmpty()) {
                    throw new IllegalArgumentException("First name cannot be empty");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            String lastName = lastNameField.getText();
            try {
                if (lastName.isEmpty()) {
                    throw new IllegalArgumentException("Last name cannot be empty");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            Manager manager = findAdminById(system, code);
            if(manager != null) {
                JOptionPane.showMessageDialog(this, "The manager already exists", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                manager = new Manager(code, firstName, lastName);
                // Add manager to system here
                system.addManager(manager);
                JOptionPane.showMessageDialog(this, "Manager Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        });

        // Create panel for form components
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridLayout(4, 2));
            formPanel.add(new JLabel("Enter manager code:"));
            formPanel.add(codeField);
            formPanel.add(new JLabel("Enter first name:"));
            formPanel.add(firstNameField);
            formPanel.add(new JLabel("Enter last name:"));
            formPanel.add(lastNameField);
            formPanel.add(new JLabel()); // Empty label for spacing
            formPanel.add(addButton);

            
            add(formPanel);

    }


    public static Manager findAdminById(systemDataBase s, String adminId) {
        for (Manager admin : s.getManagers()) {
            if (admin.getId().equals(adminId)) {
                return admin;
            }
        }
        return null;
    }
}
