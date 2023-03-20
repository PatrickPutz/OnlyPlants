package at.campus02.bp2.jpa;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import at.campus02.bp2.model.Address;
import at.campus02.bp2.model.Article;
import at.campus02.bp2.model.Customer;
import at.campus02.bp2.model.Order;
import at.campus02.bp2.model.OrderItem;
import at.campus02.bp2.utils.EntityManagerFactoryProvider;

/**
 * java -cp ~/Downloads/h2-latest.jar org.h2.tools.Shell
 */
public class JpaTest {

	private static List<Article> articles = new ArrayList<>();
	private static List<Customer> customers = new ArrayList<>();

	@BeforeClass
	public static void createTestData() {
		createCustomers();
		createArticles();
		createOrders();
	}

	private static void createCustomers() {
	    EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for (int i = 1; i <= 3; ++i) {
			Customer c = new Customer();
			c.setFirstName("Thomas");
			c.setLastName("Tester " + i);
			Address a = new Address();
			a.setCity("Graz");
			a.setCountry("Österreich");
			a.setStreet("Holzweg");
			a.setStreetNumber(i);
			a.setZipCode(8010);
			c.getAddresses().add(a);
			a = new Address();
			a.setCity("St. Radegund");
			a.setCountry("Österreich");
			a.setStreet("Hauptstraße");
			a.setStreetNumber(i);
			a.setZipCode(8061);
			c.getAddresses().add(a);
			c = entityManager.merge(c);
			customers.add(c);
		}
		transaction.commit();
		entityManager.close();
	}

	private static void createArticles() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for (int i = 1; i <= 10; ++i) {
			Article article = new Article();
			article.setName("Test Artikel " + i);
			article = entityManager.merge(article);
			articles.add(article);
		}
		transaction.commit();
		entityManager.close();
	}

	private static void createOrders() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for (int i = 0, j = 0; i < 10; ++i) {
			Order order = new Order();
			OrderItem item = new OrderItem();
			item.setArticle(articles.get(i));
			item.setQuantity(1);
			order.getItems().add(item);
			item = new OrderItem();
			item.setArticle(articles.get(i + 1 == articles.size() ? 1 : i + 1));
			item.setQuantity(1);
			order.getItems().add(item);
			order = entityManager.merge(order);
			order.setCustomer(customers.get(j));
			j = ++j == customers.size() ? 0 : j;
			entityManager.merge(order);
		}
		transaction.commit();
		entityManager.close();
	}


	@Test
	public void queryOrdersOfACustomer() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		List<Order> orders = entityManager
				.createQuery("from Order _order where _order.customer.lastName = 'Tester 2'", Order.class)
				.getResultList();
		for (Order order : orders) {
			assertTrue(order.getCustomer().getLastName().equals("Tester 2"));
		}
		entityManager.close();
	}

	@Test
	public void queryOrdersByArticle() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		List<Order> orders = entityManager
				.createQuery("select _order from Order _order join _order.items items where items.article.name = 'Test Artikel 2'", Order.class)
				.getResultList();
		for (Order order : orders) {
			boolean hasArticle = false;
			for (OrderItem item : order.getItems()) {
				hasArticle |= item.getArticle().getName().equals("Test Artikel 2");
			}
			assertTrue(hasArticle);
		}
		entityManager.close();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void queryOrdersByArticleUsingSQL() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		List<Order> orders = (List<Order>) entityManager
				.createNativeQuery("select _order.* from ORDERS _order join ORDER_ITEM items on items.ORDER_ID = _order.ID join ARTICLE article on items.ARTICLE_ID = article.ID where article.NAME = 'Test Artikel 2'", Order.class)
				.getResultList();
		for (Order order : orders) {
			boolean hasArticle = false;
			for (OrderItem item : order.getItems()) {
				hasArticle |= item.getArticle().getName().equals("Test Artikel 2");
			}
			assertTrue(hasArticle);
		}
		entityManager.close();
	}

	@Test
	public void updateOrder() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		Order order = entityManager.createQuery("from Order", Order.class).getResultList().get(0);
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		final Long orderId = order.getId();
		final int numberOfItems = order.getItems().size();
		Iterator<OrderItem> eraser = order.getItems().iterator();
		eraser.next();
		eraser.remove();
		entityManager.merge(order);
		transaction.commit();

		order = entityManager.find(Order.class, orderId);
		assertTrue(numberOfItems - 1 == order.getItems().size());
		entityManager.close();
	}

	@Test
	public void deleteOrder() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		Order order = entityManager.createQuery("from Order", Order.class).getResultList().get(0);
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		final Long orderId = order.getId();
		entityManager.remove(order);
		transaction.commit();

		order = entityManager.find(Order.class, orderId);
		assertTrue(order == null);
		entityManager.close();
	}

	@AfterClass
	public static void cleanupDatabase() {
		EntityManager entityManager = EntityManagerFactoryProvider.get().createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createNativeQuery("delete from ORDER_ITEM").executeUpdate();
		entityManager.createNativeQuery("delete from ORDERS").executeUpdate();
		entityManager.createNativeQuery("delete from CUSTOMER_ADDRESS").executeUpdate();
		entityManager.createNativeQuery("delete from ADDRESS").executeUpdate();
		entityManager.createNativeQuery("delete from CUSTOMER").executeUpdate();
		entityManager.flush();
		articles.forEach(a -> {
			a = entityManager.merge(a);
			entityManager.remove(a);
		});
		articles.clear();
		customers.clear();
		transaction.commit();
		entityManager.close();
	}
}
