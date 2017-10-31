package teste;

import fachada.Fachada;
import model.Chamados;
import model.Local;
import model.Permissoes;
import model.Usuario;

public class CriacaoHibernate {

    public static void main(String[] args) {
        
        //criacao de alguns objetos
        
        Permissoes p1 = new Permissoes();
        p1.setNome("admin");
        
        Permissoes p2 = new Permissoes();
        p2.setNome("user");
        
        Local local = new Local();
        local.setNomeLocal("LAB 1");
        
        Usuario u = new Usuario("Lucas", "indio", "123", p1);
        
        
        Chamados c1 = new Chamados();
        c1.setTipoChamado("Defeito");
        c1.setDescricaoChamado("Aconteceu um estrago");
        c1.setLocal(local);
        c1.setUsuario(u);
        
        //criacao da facade e persistindo objetos
        
        Fachada facade = Fachada.getInstancia();
        
        facade.savePermission(p1);
        facade.savePermission(p2);
        facade.saveLocal(local);
        facade.saveUser(u);
        facade.saveChamado(c1);
        
    }
}
