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
import at.campus02.bp2.model.Category;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

@ManagedBean
@SessionScoped
public class CategoryBean {

    private EntityManager entityManager;

    private Category newCategory = new Category();
    private List<Category> categoryList = new ArrayList<Category>();

    public CategoryBean(){

    }

    @PostConstruct
    public void createEntityManager(){
        entityManager = EntityManagerFactoryProvider.get().createEntityManager();
    }

    @PreDestroy
    public void closeEntityManager() {
        entityManager.close();
    }

    public void loadCategoriesFromDB() {
    	categoryList = entityManager.createQuery("from Category", Category.class).getResultList();
    }

    public void save() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(getNewCategory());
        transaction.commit();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Die Kategorie " + getNewCategory().getName() + " wurde gespeichert"));
    }

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