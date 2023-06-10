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
    
    public CategoryBean(){

    }

    // Associate a plant with a category
    
    public void associatePlantWithCategory(Plant plant, Category category) {
        category.addPlant(plant);
    }
    
    // Disassociate a plant from a category
    
    public void disassociatePlantFromCategory(Plant plant, Category category) {
        category.removePlant(plant);
    }
    
    // Save a new Category
    
    public void save() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category mergedCategory = entityManager.merge(getNewCategory());
        for (Plant plant : mergedCategory.getPlants()) {
            plant.getCategories().add(mergedCategory);
            entityManager.merge(plant);
        }
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + getNewCategory().getName() + " wurde gespeichert"));
        setNewCategory(new Category());
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
        for (Plant plant : category.getPlants()) {
            plant.getCategories().remove(category);
            entityManager.merge(plant);
        }
        entityManager.remove(category);
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + category.getName() + " wurde gelöscht"));
        loadCategoriesFromDB();
    }
    
    // Load saved Categories from Database
    
    public void loadCategoriesFromDB() {
        categoryList = entityManager.createQuery("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.plants", Category.class).getResultList();
    }

    // Load a Category by its ID
    
    public Category loadCategoryById(int categoryId) {
    	return entityManager.find(Category.class, categoryId);
    }
    
    // Required Entity Manger Methods
    
    @PostConstruct
    public void createEntityManager(){
        entityManager = EntityManagerFactoryProvider.get().createEntityManager();
    }

    @PreDestroy
    public void closeEntityManager() {
        entityManager.close();
    }

    // Getter and Setter Methods
    
    public List<Category> getCategoryList(){
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

}