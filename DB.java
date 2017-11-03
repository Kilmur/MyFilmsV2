import java.io.*;
import java.util.HashSet;

public class DB {
	HashSet<Film> filmset = new HashSet<Film>();
	int cf;
	
	public void run(){
		File file = new File("database.ser");
		try{
			if (file.exists()){
				deserializable();
				cf = filmset.size();
			}else{
				file.createNewFile();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//   МЕТОД ПОСИКА ПО String ПОЛЯМ
	public String search(String value){
		String sFilm = ("");
		for(Film f: filmset){
			String x = f.name;
			String y = f.country;
			String z = f.prod;
			if(x.contains(value) || y.contains(value) || z.contains(value)){
				sFilm = sFilm + f.toString() + "\n";
			}
		}
		return sFilm;
	}
	//   МЕТОД ПОИСКА ПО ИНТ ПОЛЮ
	public String searchYear(int value){
		String sFilms = "";
		for(Film f: filmset){
			int x = f.year;
			if(value == x){
				sFilms = sFilms + f.toString() + "\n";
			}
		}
		return sFilms;
	}
	//   МЕТОД ДОБАЛЕНИЯ ФИЛЬМА
	public boolean addFilm(Film film){
		if (filmset.add(film)){
			cf++;
			return true;
		}else{
			return false;
		}
	}
	
	public void deserializable() throws IOException, ClassNotFoundException{
			
		    FileInputStream fis = new FileInputStream("database.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			while(fis.available() > 0){
				Object objectFilm = ois.readObject();
				Film film = (Film) objectFilm;
				filmset.add(film);
			}
			ois.close();
	}
	
	public void serializable(){
		try {
			FileOutputStream fos = new FileOutputStream("database.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for(Film film: filmset){
				oos.writeObject(film);
			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
