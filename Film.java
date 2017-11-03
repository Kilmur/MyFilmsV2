import java.io.Serializable;

public class Film implements Serializable{
	private static final long serialVersionUID = 2222;
	String name;
	int year;
	String country;
	String prod;
	
	
	public Film(String n, int y, String c, String p){
		name = n;
		year = y;
		country = c;
		prod = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
	public boolean equals(Object film){
		Film f = (Film) film;
		if (getYear() == f.getYear()){
			return true;
		}else{
			return false;
		}
	}

	public String toString() {
		return name + ", " + year + "г., " + country + ", режиссер - " + prod;
	}
	
	
	

}
