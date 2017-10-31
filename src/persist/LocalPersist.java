package persist;

import javax.persistence.EntityManager;
import model.Local;
import util.EntityManagerUtil;

public class LocalPersist extends AbstractRepository<Local, Integer>{
    EntityManager em;
    
    public LocalPersist() {
        super(Local.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public Local procuraLocalPorNome(String l){
        Local local;
        try {
            em.getTransaction().begin();
            
            String q = "from Local where nomeLocal = ?1";
            local = (Local) em.createQuery(q).setParameter(1,l).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            return null;
        }
        
        
        return local;
    }
    
}
