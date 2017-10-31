/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;

import model.Local;
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
    
    public void save(Local l){
        this.localPersist.save(l);
    }
    
    public List<Local> listAllLocals(){
    	return this.localPersist.listAllEntity(Local.class);
    }
}
