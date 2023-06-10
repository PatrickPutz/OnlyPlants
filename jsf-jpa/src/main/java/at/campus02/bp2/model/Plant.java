package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import at.campus02.bp2.model.Category;


@Entity
@Table(name="plant")
public class Plant implements Serializable{

	private static final long serialVersionUID = 6429611515624913655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private int userId;
	private String name;
	private String type;
	private String description;
	private ArrayList<Demand> demands;

	 @ManyToMany(mappedBy = "plants")
	 private List<Category> categories = new ArrayList<>();
	 
	 public Plant() {
	        categories = new ArrayList<>();
	 }

	
	public void addDemand(Demand demand){
		demands.add(demand);
	}

	public void removeDemand(Demand demand){
		demands.remove(demand);
	}

	// Getter and Setter Methods
	
	public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
	
	public ArrayList<Demand> getDemands() {
		return demands;
	}

	public void setDemands(ArrayList<Demand> demands) {
		this.demands = demands;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}