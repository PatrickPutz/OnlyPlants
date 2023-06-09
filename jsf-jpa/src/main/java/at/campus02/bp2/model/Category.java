package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category implements Serializable{

	private static final long serialVersionUID = -3614772835102426726L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Plant> plants;

	public void addPlant(Plant plant){
		plants.add(plant);
	}

	public void removePlant(Plant plant){
		plants.remove(plant);
	}

	// Getter and Setter Methods
	
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

	public List<Plant> getPlants() {
		return plants;
	}

	public void setPlants(List<Plant> plants) {
		this.plants = plants;
	}
}