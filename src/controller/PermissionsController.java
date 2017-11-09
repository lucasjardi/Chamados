/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Permissoes;
import persist.PermissoesPersist;

/**
 *
 * @author DTI
 */
public class PermissionsController {
    private final PermissoesPersist permissionsPersist;
    
    public PermissionsController(PermissoesPersist pp){
        this.permissionsPersist = pp;
    }
    
    public void save(Permissoes p){
        this.permissionsPersist.save(p);
    }
    
    public Permissoes getById(Integer id) {
    	return this.permissionsPersist.findById(id);
    }
    
    
}
