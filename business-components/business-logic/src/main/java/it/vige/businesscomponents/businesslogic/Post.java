package it.vige.businesscomponents.businesslogic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String message;
	
	private int day;
	
	public Post() {
		
	}
	
	public Post(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Integer getId() {
		return id;
	}

}
