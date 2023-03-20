package at.campus02.bp2.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

	private static EntityManagerFactory entityManagerFactory;

	public static EntityManagerFactory get() {
		if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
			entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
		}
		return entityManagerFactory;
	}
}
