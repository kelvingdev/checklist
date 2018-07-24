package application;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class Person implements Serializable{
	
	private SimpleStringProperty name, age;

	public Person(String name, String age) {
		this.name = new SimpleStringProperty(name);
		this.age = new SimpleStringProperty(age);
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) { 
		this.name = new SimpleStringProperty(name);
	}
	public String getAge() {
		return age.get();
	}
	public void setAge(String age) {
		this.age = new SimpleStringProperty(age);
	}
	
	
	
}
