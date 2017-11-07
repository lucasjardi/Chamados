/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;

import model.Chamados;
import persist.ChamadoPersist;

public class ChamadoController {
    private final ChamadoPersist chamadoPersist;

    public ChamadoController(ChamadoPersist chamadoPersist) {
        this.chamadoPersist = chamadoPersist;
    }
    
    public void save(Chamados c){
        this.chamadoPersist.save(c);
    }
    
    public List<Chamados> waitList(){
    	return this.chamadoPersist.verificaNovosChamados();
    }
    
    public void changeStatus(Chamados chamado) {
    	this.chamadoPersist.update(chamado);
    }
    
}
