package persist;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Chamados;
import util.EntityManagerUtil;

public class ChamadoPersist extends AbstractRepository<Chamados, Integer>{
    EntityManager em;
    
    public ChamadoPersist() {
        super(Chamados.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Chamados> verificaNovosChamados(){
        List<Chamados> lista = null;
        try {
            em.getTransaction().begin();
            
            Query query = em.createQuery("from Chamados where recebido is false");
            lista = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return lista;        
    }
    
    public List<Chamados> verificaChamadosRecebidos(){
        List<Chamados> lista = null;
        try {
            em.getTransaction().begin();
            
            Query query = em.createQuery("from Chamados where recebido is true");
            lista = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return lista;        
    }
    
    
}
