package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
    private int id;
    
    private String nome;
    private String nick;
    private String senha;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dataCriacao", nullable = false,
    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date dataCriacao = new Date();
    
    @OneToOne
    @JoinColumn(name="PERMISSAO_ID",foreignKey = @ForeignKey(name="FK_Permissao_Usuario"))
    private Permissions permissao;

    public Users(String nome, String nick, String senha, Permissions permissao) {
        this.nome = nome;
        this.nick = nick;
        this.senha = senha;
        this.permissao = permissao;
    }
    
    public Users(){
        
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Permissions getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissions permissao) {
        this.permissao = permissao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	
    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
