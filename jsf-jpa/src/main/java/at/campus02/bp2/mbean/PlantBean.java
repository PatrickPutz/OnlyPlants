package at.campus02.bp2.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import at.campus02.bp2.model.Category;
import at.campus02.bp2.model.Plant;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped
public class PlantBean {

    private EntityManager entityManager;

    private Plant newPlant = new Plant();
    private List<Plant> plantList = new ArrayList<Plant>();
    private Integer selectedCategoryId;
    private Integer selectedPlantId;

    public PlantBean(){

    }

    // Save a new Plant
    
    public void save() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, getSelectedCategoryId());
        newPlant.setCategory(category);
        category.addPlant(newPlant);
        entityManager.persist(newPlant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "The plant " + newPlant.getName() + " has been saved."));
        setNewPlant(new Plant());
        setSelectedCategoryId(null);
    }
    
    // Update a saved Plant
    
    public void updatePlant(Plant plant) {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, getSelectedCategoryId());
        plant.setCategory(category);
        category.addPlant(plant);
        entityManager.merge(plant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Pflanze " + plant.getName() + " wurde gespeichert"));
        setSelectedCategoryId(null);
    }
    
    // Delete a saved Plant
    
    public void deletePlant(Plant plant) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        plant.getCategory().removePlant(plant);
        entityManager.remove(plant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Pflanze " + plant.getName() + " wurde gelöscht"));
        loadPlantsFromDB();
    }

    // Load saved Plants from database
    
    public void loadPlantsFromDB() {
        plantList = entityManager.createQuery("from Plant", Plant.class).getResultList();
    }
    
    // Required Entity Manager Methods
    
    @PostConstruct
    public void createEntityManager(){
        entityManager = EntityManagerFactoryProvider.get().createEntityManager();
    }

    @PreDestroy
    public void closeEntityManager() {
        entityManager.close();
    }
    
    // Getter and Setter Methods

    public List<Plant> getPlantList(){
        loadPlantsFromDB();
        return plantList;
    }

    public void setPlantList(List<Plant> plantList){
        this.plantList = plantList;
    }

    public Plant getNewPlant() {
        return newPlant;
    }

    public void setNewPlant(Plant newPlant) {
        this.newPlant = newPlant;
    }

	public Integer getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Integer selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}

	public Integer getSelectedPlantId() {
		return selectedPlantId;
	}

	public void setSelectedPlantId(Integer selectedPlantId) {
		this.selectedPlantId = selectedPlantId;
	}
}