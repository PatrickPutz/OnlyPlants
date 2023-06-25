package at.campus02.bp2.mbean;

import java.time.LocalDateTime;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import at.campus02.bp2.model.Category;
import at.campus02.bp2.model.Plant;
import at.campus02.bp2.model.Protocol;
import at.campus02.bp2.model.ProtocolEntry;
import at.campus02.bp2.model.Need;
import at.campus02.bp2.model.NeedList;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped
public class PlantBean {

    private EntityManager entityManager;

    private Plant newPlant = new Plant();
    private List<Plant> plantList = new ArrayList<Plant>();
    private Integer selectedCategoryId;
    private Integer selectedPlantId;
    private String protocolEntryText;
    private String needTitle;
    private LocalDateTime reminder;

    public PlantBean(){

    }

    public void handleImageUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        newPlant.setImageData(uploadedFile.getContents());
    }
    
    // Create a ProtocolEntry
    
    private ProtocolEntry createProtocolEntry(String entryText) {
        ProtocolEntry entry = new ProtocolEntry();
        entry.setEntryText(entryText);
        entry.setTimestamp(LocalDateTime.now());
        return entry;
    }
    
    // Add the ProtocolEntry to the Protocol
    
    public void addProtocolEntry(Plant plant) {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Protocol protocol = plant.getProtocol();
        if (protocol == null) {
            protocol = new Protocol();
            plant.setProtocol(protocol);
        }
        protocol.addEntry(createProtocolEntry(getProtocolEntryText()));

        entityManager.merge(plant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "Die Pflanze " + plant.getName() + " wurde gespeichert"));
        setSelectedCategoryId(null);
    }
    
    // Create a ProtocolEntry
    
    private Need createNeed(String needTitle, LocalDateTime reminder) {
        Need entry = new Need();
        entry.setNeedTitle(needTitle);
        entry.setReminder(reminder);
        return entry;
    }
    
    // Add the ProtocolEntry to the Protocol
    
    public void addNeed(Plant plant) {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        NeedList needs = plant.getNeeds();
        if (needs == null) {
            needs = new NeedList();
            plant.setNeeds(needs);
        }

        needs.addNeed(createNeed(getNeedTitle(), LocalDateTime.now().plusMinutes(1)));

        entityManager.merge(plant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reminder", "You have a new reminder: " + needTitle));
        setSelectedCategoryId(null);
    }
    
    // Save a new Plant
    
    public void save() {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, getSelectedCategoryId());
        newPlant.setCategory(category);

        Protocol protocol = new Protocol();
        newPlant.setProtocol(protocol);
        protocol.addEntry(createProtocolEntry("Plant created"));
        
        category.addPlant(newPlant);
        entityManager.persist(newPlant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "The plant " + newPlant.getName() + " has been saved."));
        setNewPlant(new Plant());
        setSelectedCategoryId(null);
    }
    
    // Update a saved Plant
    
    public void updatePlant(Plant plant) {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, getSelectedCategoryId());
        plant.setCategory(category);

        Protocol protocol = plant.getProtocol();
        if (protocol == null) {
            protocol = new Protocol();
            plant.setProtocol(protocol);
        }
        protocol.addEntry(createProtocolEntry("Plant updated"));

        category.addPlant(plant);
        entityManager.merge(plant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "Die Pflanze " + plant.getName() + " wurde gespeichert"));
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

	public String getProtocolEntryText() {
		return protocolEntryText;
	}

	public void setProtocolEntryText(String protocolEntryText) {
		this.protocolEntryText = protocolEntryText;
	}

	public String getNeedTitle() {
		return needTitle;
	}

	public void setNeedTitle(String needTitle) {
		this.needTitle = needTitle;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
}