package hibernatetest;

import facade.Facade;
import model.Locals;
import model.Permissions;
import model.Users;

public class PersistObjects {

    public static void main(String[] args) {
        
        //criacao de alguns objetos
        
        Permissions p1 = new Permissions();
        p1.setNome("admin");
        
        Permissions p2 = new Permissions();
        p2.setNome("user");
        
        Locals local1 = new Locals();
        local1.setNomeLocal("LAB 1");
        
        Locals local2 = new Locals();
        local2.setNomeLocal("LAB 2");
        
        //usuario default
        Users u = new Users("Admin", "admin", "admin", p1);
        
        //persistindo objetos no banco
        
        Facade facade = Facade.getInstancia();
        
        facade.savePermission(p1);
        facade.savePermission(p2);
        facade.saveLocal(local1);
        facade.saveLocal(local2);
        facade.saveUser(u);
        
    }
}
