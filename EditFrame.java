import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class EditFrame {
    String lastName;
    int lastYear;
    String lastCountry;
    String lastProd;
    
    JFrame eframe;
    JTextField tf1;
    JTextField tf2;
    JTextField tf3;
    JTextField tf4;
    
    public EditFrame(String l, int y, String c, String p){
        lastName = l;
        lastYear = y;
        lastCountry = c;
        lastProd = p;
    }
    
    public void createEditFrame(){
        
        eframe = new JFrame();
        eframe.setSize(300, 200);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        
        tf1 = new JTextField(22);
        tf2 = new JTextField(22);
        tf3 = new JTextField(22);
        tf4 = new JTextField(22);
        
        JButton but1 = new JButton("Сохранить");
        JButton but2 = new JButton("Отмена");
        
        eframe.add(panel);
        
        panel.add(tf1);
        panel.add(tf2);
        panel.add(tf3);
        panel.add(tf4);
        
        panel.add(but1);
        panel.add(but2);
        
        but1.addActionListener(new But1());
        but2.addActionListener(new But2());
        
        eframe.setVisible(true);
        
    }
  //         КНОПКА РЕДАКТИРОВАНИЯ
    class But1 implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            String name = tf1.getText();
            String year = tf2.getText();
            String country = tf3.getText();
            String prod = tf4.getText();
            int yearInt;
            try{
                yearInt = Integer.parseInt(year);
            }catch(NumberFormatException ex){
                ex.printStackTrace();
                tf2.requestFocus();
                return;
            }
            if(!name.equals(lastName) || (yearInt != lastYear) || 
              (!country.equals(lastCountry)) || (!prod.equals(lastProd))){
                System.out.println("Цикл if пошел");
                
                
                for(Film f: GUI.db.filmset){
                    String n = f.name;
                    int y = f.year;
                    if((n.contains(lastName)) & (lastYear == y)){
                        GUI.db.filmset.remove(f);
                    }
                }
                System.out.println("Цикл for закончен");
                Film newFilm = new Film(name, yearInt, country, prod);
                GUI.db.filmset.add(newFilm);
                System.out.println("Создание фильма закончено");
            }
            eframe.dispose();
            
        }
    }
//           КНОПКА ОТМЕНЫ
    class But2 implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            eframe.dispose();
        }
    }
    

}
