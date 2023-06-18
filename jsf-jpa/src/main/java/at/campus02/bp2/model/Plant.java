package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;

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

	@ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
	
	@Lob
	@Column(name = "image_data", columnDefinition = "BLOB")
	private byte[] imageData;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;
	
	public void addDemand(Demand demand){
		demands.add(demand);
	}

	public void removeDemand(Demand demand){
		demands.remove(demand);
	}

	// Getter and Setter Methods
	
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
	
	public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

}