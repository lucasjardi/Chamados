package persist;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import org.hibernate.HibernateException;

import util.EntityManagerUtil;

public class AbstractRepository<T, ID extends Serializable> implements IPersistMetodos<T,ID>{

	private final EntityManager em;
	private Class<T> persistentClass;

	public AbstractRepository(Class<T> pPersistentClass) {
		this.em = EntityManagerUtil.getEntityManager();
		this.persistentClass = pPersistentClass;
	}

        @Override
	public < T > void save(T entity) {
		try {
			this.em.getTransaction().begin();
			this.em.persist(entity);
			this.em.getTransaction().commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "saving error");
//			e.printStackTrace();
			this.em.getTransaction().rollback();
		}
	}

        @Override
	public < T > void update(T entity){
		try{
			this.em.getTransaction().begin();
			this.em.merge(entity);
			this.em.getTransaction().commit();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "update error");
			this.em.getTransaction().rollback();
		}
	}

        @Override
	public < T > void delete(T entity) {
		try {
			this.em.getTransaction().begin();
			this.em.remove(entity);
			this.em.getTransaction().commit();
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "delete error");
			this.em.getTransaction().rollback();
		}
	}

        @Override
	public < T > T findById(ID id) {
		T fEntity = null;
		try {
                    fEntity = em.find((Class<T>) persistentClass, id);
		} catch (NoResultException e) {
			JOptionPane.showMessageDialog(null, "No result error");
			return null;
		} 	
		return fEntity;
	}

	public < T > List<T> listAllEntity(Class<T> entity) {
		List<T> objects = null;
		try {
			Query query = em.createQuery("from " + entity.getName());
			objects = query.getResultList();
		} catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, "hibernate exception");
//			e.printStackTrace();
		} 
		return objects;
	}

        @Override
	public < T > List<T> listAllEntityOrderBy(Class<T> entity, String fieldName) {
		List<T> objects = null;
		try {
			Query query = em.createQuery("from " + entity.getName() + " order by "
					+ fieldName);
			objects = query.getResultList();
		} catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, "hibernate exception");
//			e.printStackTrace();
		} 
		return objects;
	}
        
}

