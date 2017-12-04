package persist;

import model.Permissions;

public class PermissionsPersist extends AbstractRepository<Permissions, Integer>{
    
    public PermissionsPersist() {
        super(Permissions.class);
    }
    
}
