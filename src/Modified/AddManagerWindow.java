package Modified;

import javax.swing.*;

import HW2.systemDataBase;
import java.awt.event.*;

public class AddManagerWindow extends JFrame {
    private systemDataBase system;

    public AddManagerWindow(systemDataBase system) {
        this.system = system;
        setTitle("Add Manager");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu
        JMenu addManagerMenu = new JMenu("Add Manager");

        // Create menu items for Chief Manager and General Manager
        JMenuItem chiefManagerItem = new JMenuItem("Chief Manager");
        JMenuItem generalManagerItem = new JMenuItem("General Manager");

        // Add action listeners to menu items
        chiefManagerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openManagerForm("Chief Manager");
            }
        });

        generalManagerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openManagerForm("General Manager");
            }
        });

        addManagerMenu.add(chiefManagerItem);
        addManagerMenu.add(generalManagerItem);

        
        menuBar.add(addManagerMenu);
        setJMenuBar(menuBar);
    }

    // הפונקציה הזו פותחת את אחד החלונות לפי מה שהמשתמש לחץ

    private void openManagerForm(String managerType) {      
        JFrame managerForm;
        if ("Chief Manager".equals(managerType)) {
            managerForm = new ChiefManagerForm(this.system);
        } else {
            managerForm = new ManagerForm(this.system, managerType);
        }
        managerForm.setVisible(true);
    }        
}
