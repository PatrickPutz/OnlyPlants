package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="category")
public class Category implements Serializable{

	private static final long serialVersionUID = -3614772835102426726L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "category_plant",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id"))
    private List<Plant> plants = new ArrayList<>();

	 public void addPlant(Plant plant) {
	        plants.add(plant);
	        plant.getCategories().add(this);
	    }

	    public void removePlant(Plant plant) {
	        plants.remove(plant);
	        plant.getCategories().remove(this);
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