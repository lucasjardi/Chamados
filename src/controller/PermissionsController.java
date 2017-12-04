/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Permissions;
import persist.PermissionsPersist;

/**
 *
 * @author DTI
 */
public class PermissionsController {
    private final PermissionsPersist permissionsPersist;
    
    public PermissionsController(PermissionsPersist pp){
        this.permissionsPersist = pp;
    }
    
    public void save(Permissions p){
        this.permissionsPersist.save(p);
    }
    
    public Permissions getById(Integer id) {
    	return this.permissionsPersist.findById(id);
    }
    
    
}
