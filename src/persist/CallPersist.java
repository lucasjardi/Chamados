package persist;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import helper.Helpers;
import model.Calls;
import util.EntityManagerUtil;

public class CallPersist extends AbstractRepository<Calls, Integer>{
    EntityManager em;
    
    public CallPersist() {
        super(Calls.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Calls> verificaNovosChamados(){
        List<Calls> lista = null;
        try {
            em.getTransaction().begin();
            
            Query query = em.createQuery("from Calls where recebido is false order by dataChamado DESC");
            lista = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        return lista;        
    }
    
    
}
