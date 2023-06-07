package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="plant")
public class Plant implements Serializable{

	private static final long serialVersionUID = 6429611515624913655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String name;
	private String type;
	private String description;
	private ArrayList<Demand> demands;

	public void addDemand(Demand demand){
		demands.add(demand);
	}

	public void removeDemand(Demand demand){
		demands.remove(demand);
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