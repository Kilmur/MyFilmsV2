import java.awt.Color;

import javax.swing.*;


public class EditFrame {
    
    JFrame frame;
    JTextField tf1;
    JTextField tf2;
    JTextField tf3;
    JTextField tf4;
    
    
    
    public void createEditFrame(){
        
        frame = new JFrame();
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        
        tf1 = new JTextField(22);
        tf2 = new JTextField(22);
        tf3 = new JTextField(22);
        tf4 = new JTextField(22);
        
        JButton but1 = new JButton("Сохранить");
        JButton but2 = new JButton("Отмена");
        
        frame.add(panel);
        
        panel.add(tf1);
        panel.add(tf2);
        panel.add(tf3);
        panel.add(tf4);
        
        panel.add(but1);
        panel.add(but2);
        
        
        frame.setVisible(true);
        
    }
    

}
