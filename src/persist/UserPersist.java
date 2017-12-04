package persist;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import model.Users;
import util.EntityManagerUtil;

public class UserPersist extends AbstractRepository<Users, Integer>{
    EntityManager em;
    
    public UserPersist() {
        super(Users.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public Users verificaLogin(String nick, String senha){
        Users u = null;
        try {
            em.getTransaction().begin();
            
            String q = "from Users where nick = ?1 and senha = ?2";
            u = (Users) em.createQuery(q).setParameter(1,nick).setParameter(2, senha).getSingleResult();
            
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        
        
        return u;
    }
    
    public boolean existsLogin(String nick){
    	
        Users u = null;
               
    	try {            
            String q = "from Users where nick = ?1";
            u = (Users) em.createQuery(q).setParameter(1,nick).getSingleResult();
        } catch (NoResultException n) {
			
		}
    	
    	return u!=null;
    }
    
}
