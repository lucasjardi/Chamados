package persist;

import javax.persistence.EntityManager;
import model.Locals;
import util.EntityManagerUtil;

public class LocalPersist extends AbstractRepository<Locals, Integer>{
    EntityManager em;
    
    public LocalPersist() {
        super(Locals.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public Locals procuraLocalPorNome(String l){
        Locals local;
        try {
            em.getTransaction().begin();
            
            String q = "from Locals where nomeLocal = ?1";
            local = (Locals) em.createQuery(q).setParameter(1,l).getSingleResult();
            
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            return null;
        }
        
        
        return local;
    }
    
}
