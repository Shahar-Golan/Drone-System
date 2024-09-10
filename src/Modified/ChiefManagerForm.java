package Modified;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import HW2.ChiefManager;
import HW2.Manager;
import HW2.systemDataBase;

class ChiefManagerForm extends ManagerForm {
        private JTextField userNameField, passwordField;
    
        public ChiefManagerForm(systemDataBase system) {
            super(system, "Chief Manager");
    
            // Create labels
            JLabel codeLabel = new JLabel("Enter manager code:");
            JLabel firstNameLabel = new JLabel("Enter first name:");
            JLabel lastNameLabel = new JLabel("Enter last name:");
            JLabel userNameLabel = new JLabel("Enter UserName:");
            JLabel passwordLabel = new JLabel("Enter Password:");

    
            
            userNameField = new JTextField(10);
            passwordField = new JTextField(10);
    
            
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    
            
            formPanel.add(createRow(codeLabel, codeField));
            formPanel.add(createRow(firstNameLabel, firstNameField));
            formPanel.add(createRow(lastNameLabel, lastNameField));
            formPanel.add(createRow(userNameLabel, userNameField));
            formPanel.add(createRow(passwordLabel, passwordField));
    
            
            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
                String code = codeField.getText();
                try {
                    if (code.isEmpty()) {
                        throw new IllegalArgumentException("Code cannot be empty");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChiefManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }


                // בדיקות קלט 

                String firstName = firstNameField.getText();
                try {
                    if (firstName.isEmpty()) {
                        throw new IllegalArgumentException("First name cannot be empty");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChiefManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                String lastName = lastNameField.getText();
                try {
                    if (lastName.isEmpty()) {
                        throw new IllegalArgumentException("Last name cannot be empty");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChiefManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                String userName = userNameField.getText();try {
                    if (userName.isEmpty()) {
                        throw new IllegalArgumentException("Last name cannot be empty");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChiefManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                String passWord = passwordField.getText();
                try {
                    if (passWord.isEmpty()) {
                        throw new IllegalArgumentException("Last name cannot be empty");
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChiefManagerForm.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }


                Manager chief = findChiefById(system, code);
                if(chief != null) {
                    JOptionPane.showMessageDialog(this, "The Chief manager already exists", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    chief = new ChiefManager(code, firstName, lastName, userName, passWord);
                    // adding cheif to the managers collection.
                    system.addManager(chief);
                    JOptionPane.showMessageDialog(this, "Chief Manager Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                dispose();
            });

            // יציאה מהבנאי

            formPanel.add(addButton);
    
            // Add form panel to the frame
            add(formPanel);
        }


        // Helper method to create a row with a label and a text field
    private JPanel createRow(JLabel label, JTextField textField) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.add(label);
        row.add(textField);
        return row;
    }
 

    public static ChiefManager findChiefById(systemDataBase s, String adminId) {
        for (Manager admin : s.getManagers()) {
            if(admin instanceof ChiefManager) {
                if (admin.getId().equals(adminId)) {
                    return (ChiefManager)admin;
                }
            }
        }
        return null;
    }

    }