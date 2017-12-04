/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;

import model.Calls;
import persist.CallPersist;

public class CallController {
    private final CallPersist callPersist;

    public CallController(CallPersist callPersist) {
        this.callPersist = callPersist;
    }
    
    public void save(Calls c){
        this.callPersist.save(c);
    }
    
    public List<Calls> waitList(){
    	return this.callPersist.verificaNovosChamados();
    }
    
    public List<Calls> listAll(){
    	return this.callPersist.listAllEntity(Calls.class);
    }
    
    public void changeStatus(Calls chamado) {
    	this.callPersist.update(chamado);
    }
    
    public void deleteCall(Calls c) {
    	this.callPersist.delete(c);
    }
    
}
