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

import at.campus02.bp2.model.Article;
import at.campus02.bp2.model.Plant;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped
public class PlantBean {

    private EntityManager entityManager;

    private Plant newPlant = new Plant();
    private List<Plant> plantList = new ArrayList<Plant>();

    public PlantBean(){

    }

    @PostConstruct
    public void createEntityManager(){
        entityManager = EntityManagerFactoryProvider.get().createEntityManager();
    }

    @PreDestroy
    public void closeEntityManager() {
        entityManager.close();
    }

    public void loadPlantsFromDB() {
        plantList = entityManager.createQuery("from Plant", Plant.class).getResultList();
    }

    public void save() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(newPlant);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Pflanze " + newPlant.getName() + " wurde gespeichert"));
    }

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
}