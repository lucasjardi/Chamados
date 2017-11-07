/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucas
 */
@Entity
public class Chamados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAMADOS_ID")
    private int id;
    private String tipoChamado;
    private String descricaoChamado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dataChamado", nullable = false,
    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date dataChamado = new Date();
    
    @Column(columnDefinition = "boolean default false")
    private boolean recebido;
    
    @ManyToOne
    @JoinColumn(name="USUARIO_ID", foreignKey = @ForeignKey(name = "FK_usuario_Chamados"))
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="LOCAL_ID",foreignKey = @ForeignKey(name="FK_Local_Chamados"))
    private Local localChamado;
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(String tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public String getDescricaoChamado() {
        return descricaoChamado;
    }

    public void setDescricaoChamado(String descricaoChamado) {
        this.descricaoChamado = descricaoChamado;
    }

    public Date getDataChamado() {
        return dataChamado;
    }

    public void setDataChamado(Date dataChamado) {
        this.dataChamado = dataChamado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }

    public Local getLocal() {
        return localChamado;
    }

    public void setLocal(Local local) {
        this.localChamado = local;
    }

    
    @Override
    public String toString() {
           return "  id=" + id + "\n"
                + "  tipo:" + tipoChamado + "\n"
                + "  descricao:" + descricaoChamado + "\n"
                + "  data:" + dataChamado + "\n"
                + "  recebido:" + recebido + "\n"
                + "  local: " + localChamado.getNomeLocal() + "\n"
                + "  usuario: " + usuario + "\n";
    }
    
    public String toString2(){
        return "id: "+ this.id + " - " + 
                this.tipoChamado + "; " +
                this.descricaoChamado+"; " +
                this.localChamado.getNomeLocal()+"; " +
                this.dataChamado;
    }
    
    
}
