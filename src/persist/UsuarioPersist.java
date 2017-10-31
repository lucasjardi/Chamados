package persist;

import javax.persistence.EntityManager;
import model.Usuario;
import util.EntityManagerUtil;

public class UsuarioPersist extends AbstractRepository<Usuario, Integer>{
    EntityManager em;
    
    public UsuarioPersist() {
        super(Usuario.class);
        em = EntityManagerUtil.getEntityManager();
    }
    
    public Usuario verificaLogin(String nick, String senha){
        Usuario u = null;
        try {
            em.getTransaction().begin();
            
            String q = "from Usuario where nick = ?1 and senha = ?2";
            u = (Usuario) em.createQuery(q).setParameter(1,nick).setParameter(2, senha).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        
        
        return u;
    }
    
    public boolean existsLogin(){
        return false;
    }
    
}
