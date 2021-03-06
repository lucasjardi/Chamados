package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.JOptionPane;

public class EntityManagerUtil {
	
	private static final String PERSISTENCE_UNIT = "hsqldb";
	private static final EntityManagerFactory factory;
	
	public static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();


	static {
		try {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

		} catch (Throwable ex) {
			JOptionPane.showMessageDialog(null, "Exception in Hibernate Initialize!");
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static CriteriaBuilder getCriteriaBuilder() {
		return factory.getCriteriaBuilder();
	}

	public static EntityManager getEntityManager() {
		EntityManager em = entitymanager.get();
		if (em == null || !em.isOpen()) {
			em = factory.createEntityManager();
			entitymanager.set(em);
		}
		return em;
	}

	public static void closeEntityManager() {
		EntityManager em = entitymanager.get();
		entitymanager.set(null);
		if (em != null && em.isOpen()) {
			em.close();
			em = null;
		}
	}
}
