package Modified;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorWindow extends JFrame {
    private JTextField inputField;
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;

    public CalculatorWindow() {
        setTitle("Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);
        add(inputField, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        // Add digit buttons
        for (int i = 1; i <= 9; i++) {
            addButton(buttonPanel, String.valueOf(i));
        }

        // Operation buttons
        addButton(buttonPanel, "+");
        addButton(buttonPanel, "-");
        addButton(buttonPanel, "*");
        addButton(buttonPanel, "/");
        addButton(buttonPanel, "0");
        addButton(buttonPanel, "=");
        addButton(buttonPanel, "Clear");

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String label) {
        JButton button = new JButton(label);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (label.equals("=")) {
                    calculateResult();
                } else if (label.equals("Clear")) {
                    inputField.setText("");
                    firstNumber = 0;
                    operator = "";
                    isNewOperation = true;
                } else if (label.matches("[+\\-*/]")) {
                    performOperation(label);
                } else {                    // The user press digit booton
                    if (isNewOperation) {   // If we need to scan another value
                        inputField.setText(label);
                        isNewOperation = false;
                    } else {                // Show the digit on the text feild
                        inputField.setText(inputField.getText() + label);
                    }
                }
            }
        });
    }

    private void performOperation(String op) {
            firstNumber = Double.parseDouble(inputField.getText());
            operator = op;
            isNewOperation = true;
    }

    private void calculateResult() {
        try {
            double secondNumber = Double.parseDouble(inputField.getText());
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) throw new ArithmeticException("Cannot divide by zero");
                    result = firstNumber / secondNumber;
                    break;
            }
            inputField.setText(String.valueOf(result));
            isNewOperation = true;
        } catch (ArithmeticException ex) {
            inputField.setText(ex.getMessage());
            isNewOperation = true;
        }
    }
   
}



