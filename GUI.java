import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


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
	
	DB db;
	
	public void createGUI(){
		
		db = new DB();
		try{
			db.run();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Сборник просмотренных фильмов");
		frame.setResizable(false);
		
		panel = new JPanel();
		Color bg = new Color(0, 189, 152);
		panel.setBackground(bg);
		panel.setLayout(null);
		
		area = new JTextArea(10, 28);
		JScrollPane scr = new JScrollPane(area);
		area.setLineWrap(true);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		area.setText("Информация для пользователя");
		Font fontArea = new Font("arial", Font.BOLD, 16);
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
		
		frame.add(panel);
		
		panel.add(scr);
		scr.setBounds(10, 10, 480, 150);
		
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
		
		panel.add(labelCount);
		Font fontLabelCount = new Font("arial", Font.ITALIC, 12);
		labelCount.setFont(fontLabelCount);
		labelCount.setBounds(330, 460, 200, 30);
		
		butAdd.addActionListener(new ButAdd());
		butSearch.addActionListener(new ButSearch());
		frame.addWindowListener(new WindowClosing());
		
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
			try{
				y = Integer.parseInt(year);
			}catch(Exception ex){
				y = 0;
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
			try{
				int x = Integer.parseInt(searchTF.getText());
				String film = db.searchYear(x);
				area.setText(film);
				String films = searchTF.getText();
				String res = db.search(films);
				area.append(res);
				searchTF.setText("");
			}catch(Exception ex){
				String search = searchTF.getText();
				String films = db.search(search);
				area.setText(films);
				searchTF.setText("");
			}
		}
	}
	
	//    ВЫЗОВ СЕРИАЛИЗАЦИИ ПРИ ЗАКРЫТИИ ФРЕЙМА
	class WindowClosing implements WindowListener{
        public void windowClosing(WindowEvent e) {
			db.serializable();
        	System.exit(0);
		}

		public void windowOpened(WindowEvent e) {
			String x = "" + (db.filmset.size());
			labelCount.setText("Фильмов в списке - " + x);
		}

		public void windowClosed(WindowEvent e) {}

		public void windowIconified(WindowEvent e) {}

		public void windowDeiconified(WindowEvent e) {}

		public void windowActivated(WindowEvent e) {}

		public void windowDeactivated(WindowEvent e) {}
		
	}
	

}