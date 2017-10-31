/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Chamados;
import persist.ChamadoPersist;

/**
 *
 * @author DTI
 */
public class ChamadoController {
    private final ChamadoPersist chamadoPersist;

    public ChamadoController(ChamadoPersist chamadoPersist) {
        this.chamadoPersist = chamadoPersist;
    }
    
    public void save(Chamados c){
        this.chamadoPersist.save(c);
    }
    
    
}
