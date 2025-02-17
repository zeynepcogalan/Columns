package utilities;

public class Player {
	 private Object name;
	 private Object surname;
	 private Object score;
	 
	public Player(Object name, Object surname, Object score) {
		super();
		this.name = name;
		this.score = score;
		this.surname = surname;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getSurname() {
		return surname;
	}

	public void setSurname(Object surname) {
		this.surname = surname;
	}

	public Object getScore() {
		return score;
	}

	public void setScore(Object score) {
		this.score = score;
	} 
}