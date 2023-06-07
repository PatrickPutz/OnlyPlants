package at.campus02.bp2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.*;

@Entity
@Table(name="demand")
public class Demand implements Serializable{

	private static final long serialVersionUID = 3881260624273755416L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int userId;
	private String name;
	private Period frequency;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Period getFrequency() {
		return frequency;
	}

	public void setFrequency(Period frequency) {
		this.frequency = frequency;
	}
}