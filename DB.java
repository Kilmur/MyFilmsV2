import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DB {
	HashSet<Film> filmset = new HashSet<Film>();
	int cf;
	
	public DB(){
		File file = new File("database.ser");
		try{
			if (file.exists()){
				deserialize();
				cf = filmset.size();        
			}else{
				file.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
            e.printStackTrace();
		}
	}
	
	//   МЕТОД ПОСИКА ПО String ПОЛЯМ
    public ArrayList<Film> search(String value){
		ArrayList<Film> films= new ArrayList<Film>();
		for(Film f: filmset){
			String x = f.name;
			String y = f.country;
			String z = f.prod;
			if(x.contains(value) || y.contains(value) || z.contains(value)){
				films.add(f);
			}
		}
		return films;
	}
	//   МЕТОД ПОИСКА ПО ИНТ ПОЛЮ
    public ArrayList<Film> searchYear(int value){
	    ArrayList<Film> films = new ArrayList<Film>();
		for(Film f: filmset){
			int x = f.year;
			if(value == x){
				films.add(f);
			}
		}
		return films;
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
	
	public void deserialize() throws IOException, ClassNotFoundException{
			
		    FileInputStream fis = new FileInputStream("database.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			while(fis.available() > 0){
				Object objectFilm = ois.readObject();
				Film film = (Film) objectFilm;
				filmset.add(film);
			}
			ois.close();
	}
	
	public void serialize(){
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
