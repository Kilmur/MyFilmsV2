import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class GUI {
	JFrame frame;
	JPanel panel;
	JTextArea area;
	JTextField nameTF;
	JTextField yearTF;
	JTextField countryTF;
	JTextField prodTF;
	JTextField searchTF;
	JLabel labelCount;
	DefaultListModel<Film> dlm;
	JList list;
	Film listValue;
	static DB db;
	
	public void createGUI(){
		
		db = new DB();
		
		frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Сборник просмотренных фильмов");
//		frame.setResizable(false);
		
		panel = new JPanel();
		Color bg = new Color(0, 189, 152);
		panel.setBackground(bg);
		panel.setLayout(null);
		
		dlm = new DefaultListModel<Film>();
		list = new JList(dlm);
		JScrollPane scrList = new JScrollPane(list);
		
		area = new JTextArea(10, 28);
		JScrollPane scr = new JScrollPane(area);
		area.setLineWrap(true);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		area.setText("Информация для пользователя");
		Font fontArea = new Font("arial", Font.BOLD, 13);
		area.setFont(fontArea);
		
		JLabel labelName = new JLabel("Название");
		JLabel labelYear = new JLabel("Год");
		JLabel labelCountry = new JLabel("Страна");
		JLabel labelProd = new JLabel("Режиссер");
		JLabel labelSearch = new JLabel("Поиск");
		labelCount = new JLabel();
		
		nameTF = new JTextField();
		yearTF = new JTextField();
		countryTF = new JTextField();
		prodTF = new JTextField();
		searchTF = new JTextField();
		
		JButton butAdd = new JButton("Добавить");
		JButton butSearch = new JButton("Поиск");
		
		JButton but = new JButton("Редакт");
		
		frame.add(panel);
		
		panel.add(scrList);
        scrList.setBounds(10, 10, 480, 85);
		
		panel.add(scr);
		scr.setBounds(10, 105, 480, 68);
		
		panel.add(labelName);
		labelName.setBounds(10, 178, 100, 25);
		
		panel.add(nameTF);
		nameTF.setBounds(100, 180, 380, 22);
		
		panel.add(labelYear);
		labelYear.setBounds(27, 215, 100, 25);
		
		panel.add(yearTF);
		yearTF.setBounds(100, 217, 80, 22);
		
		panel.add(labelCountry);
		labelCountry.setBounds(17, 252, 100, 25);
		
		panel.add(countryTF);
		countryTF.setBounds(100, 254, 380, 22);
		
		panel.add(labelProd);
		labelProd.setBounds(10, 291, 100, 25);
		
		panel.add(prodTF);
		prodTF.setBounds(100, 293, 380, 22);
		
		panel.add(butAdd);
		butAdd.setBounds(190, 330, 120, 25);
		
		panel.add(labelSearch);
		labelSearch.setBounds(21, 380, 100, 25);
		
		panel.add(searchTF);
		searchTF.setBounds(100, 382, 380, 22);
		
		panel.add(butSearch);
		butSearch.setBounds(190, 419, 120, 25);
		
		panel.add(but);
		but.setBounds(10, 450, 40, 40);
		
		panel.add(labelCount);
		Font fontLabelCount = new Font("arial", Font.ITALIC, 12);
		labelCount.setFont(fontLabelCount);
		labelCount.setBounds(330, 460, 200, 30);
		
		butAdd.addActionListener(new ButAdd());
		butSearch.addActionListener(new ButSearch());
		but.addActionListener(new But());
		frame.addWindowListener(new WindowClosing());
		list.addListSelectionListener(new ListListener());
		
		frame.setVisible(true);
	}
	
	//       КНОПКА ДОБАВЛЕНИЯ ФИЛЬМА
	class ButAdd implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String name = nameTF.getText();
			String year = yearTF.getText();
			String country = countryTF.getText();
			String prod = prodTF.getText();
			int y;
			String pt = "([\\w]+[\\s]?)*([\\-][\\s])?([\\w]+[\\s]?)*";
			Pattern pattern = Pattern.compile(pt, Pattern.UNICODE_CHARACTER_CLASS);
			try{
			    Matcher m = pattern.matcher(nameTF.getText());
			    boolean ft = m.matches();
			    if(!ft){
			        area.setText("Введите корректное название");
			        nameTF.requestFocus();
			        return;
			    }
				y = Integer.parseInt(year);
			}catch(NumberFormatException ex){
				ex.printStackTrace();
				area.setText("Введите корректный год, состоящий из цифр");
				yearTF.requestFocus();
				return;
			}
			Film film = new Film(name, y, country, prod);
			boolean result;
			try{
				result = db.addFilm(film);
				if(result){
					area.setText("Фильм: " + film.name + " - " + film.year + "г., добавлен");
					String varCount = "" + db.cf;
					labelCount.setText("Фильмов в списке - " + varCount);
				}else{
					area.setText("Фильм: " + film.name + " - " + film.year + "г. уже в списке");
				}
				nameTF.setText("");
				yearTF.setText("");
				countryTF.setText("");
				prodTF.setText("");
			}catch(Exception ex){
				area.setText("Фильм НЕ добавлен");
				ex.printStackTrace();
			}
		}
	}
	
	//           КНОПКА ПОИСКА
	class ButSearch implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		    area.setText("");
		    dlm.removeAllElements();
		    ArrayList<Film> films;
			try{
			    if(searchTF.getText().equals("")){
			        area.setText("Введен пустой поисковый запрос\nВыведены все фильмы в списке");
			    }
				int x = Integer.parseInt(searchTF.getText());
				films = db.searchYear(x);
				for(Film f: films){
				    dlm.addElement(f);
				}
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}finally{
			    String search = searchTF.getText();
			    
                films = db.search(search);
                for(Film f: films){
                    dlm.addElement(f);
                }
                searchTF.setText("");
			}
			if(dlm.isEmpty()){
			    area.setText("Фильм не найден");
			}
		}
	}
	
	//             ПРОБНАЯ КНОПКА
	class But implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            if(listValue != null){
                
                String n = listValue.name;
                int y = listValue.year;
                String c = listValue.country;
                String p = listValue.prod;
                
                EditFrame ef = new EditFrame(n, y, c, p);
                ef.createEditFrame();
                ef.tf1.setText(n);
                ef.tf2.setText("" + y);
                ef.tf3.setText(c);
                ef.tf4.setText(p);
            }else{
                area.setText("Для редактирования записей:\n1. Найдите фильм с помощью Поиска\n2. Выделите его\n3. Жмите кнопку Редактирование");
            }
        }
	}
	
	// ЗАНОСИМ В ПЕРЕМЕННУЮ РЕЗУЛЬТАТ ВЫДЕЛЕНИЯ НА JList
	class ListListener implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                Object v;
                v = list.getSelectedValue();
                listValue = (Film) v;
                
                System.out.println(listValue);
            }
        }
	}
	
	//    ВЫЗОВ СЕРИАЛИЗАЦИИ ПРИ ЗАКРЫТИИ ФРЕЙМА
	class WindowClosing implements WindowListener{
        public void windowClosing(WindowEvent e) {
			db.serialize();
        	System.exit(0);
		}

		public void windowOpened(WindowEvent e) {
			labelCount.setText("Фильмов в списке - " + db.cf);
		}

		public void windowClosed(WindowEvent e) {}

		public void windowIconified(WindowEvent e) {}

		public void windowDeiconified(WindowEvent e) {}

		public void windowActivated(WindowEvent e) {}

		public void windowDeactivated(WindowEvent e) {}
		
	}
	

}
