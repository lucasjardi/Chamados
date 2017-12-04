/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;

import model.Locals;
import persist.LocalPersist;

/**
 *
 * @author DTI
 */
public class LocalController {
    private final LocalPersist localPersist;

    public LocalController(LocalPersist localPersist) {
        this.localPersist = localPersist;
    }
    
    public void save(Locals l){
        this.localPersist.save(l);
    }
    
    public List<Locals> listAllLocals(){
    	return this.localPersist.listAllEntity(Locals.class);
    }
}
