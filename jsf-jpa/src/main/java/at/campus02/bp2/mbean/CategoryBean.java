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
public class CategoryBean {

    private EntityManager entityManager;
    private List<Category> categoryList = new ArrayList<Category>();

    private Category newCategory = new Category();
    private Plant newPlant = new Plant();
    private Integer selectedCategoryId;
    private PlantBean plantBean;
    
    public CategoryBean(){

    }

    public void assignPlantToCategory() {
        Plant selectedPlant = entityManager.find(Plant.class, getPlantBean().getSelectedPlantId());
        Category selectedCategory = entityManager.find(Category.class, getSelectedCategoryId());
        selectedCategory.addPlant(selectedPlant);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(selectedCategory);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Pflanze wurde der Kategorie zugewiesen"));
    }
    
    // Save a new Category
    
    public void save() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.merge(newCategory);
        getNewPlant().setCategory(category);
        entityManager.persist(getNewPlant());
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + newCategory.getName() + " wurde gespeichert"));
        setNewCategory(new Category());
        setNewPlant(new Plant());
    }
    
    // Update a saved Category
    
    public void updateCategory(Category category) {
    	EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(category);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + category.getName() + " wurde gespeichert"));
    }
    
    // Delete a saved Category
    
    public void deleteCategory(Category category) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(category);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + category.getName() + " wurde gelöscht"));
        loadCategoriesFromDB();
    }
    
    // Load saved Categories from Database
    
    public void loadCategoriesFromDB() {
    	categoryList = entityManager.createQuery("from Category", Category.class).getResultList();
    }

    // Load a Category by its ID
    
    public Category loadCategoryById(int categoryId) {
    	return entityManager.find(Category.class, categoryId);
    }
    
    // Required Entity Manager Methods
    
    @PostConstruct
    public void createEntityManager(){
        entityManager = EntityManagerFactoryProvider.get().createEntityManager();
        loadCategoriesFromDB();
    }

    @PreDestroy
    public void closeEntityManager() {
        entityManager.close();
    }

    // Getter and Setter Methods
    
    public List<Category> getCategoryList() {
        loadCategoriesFromDB();
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
    }

	public Category getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(Category newCategory) {
		this.newCategory = newCategory;
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

	public PlantBean getPlantBean() {
		return plantBean;
	}

	public void setPlantBean(PlantBean plantBean) {
		this.plantBean = plantBean;
	}

}