package persist;

import model.Permissoes;

public class PermissoesPersist extends AbstractRepository<Permissoes, Integer>{
    
    public PermissoesPersist() {
        super(Permissoes.class);
    }
    
}
