package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import config.Config;
import exceptions.UserAlreadyExists;
import exceptions.UserPasswordException;
import model.SessionUser;
import model.Usuario;
import persist.FilePersist;
import persist.UsuarioPersist;

public class UserController {
    
    private final UsuarioPersist usuarioPersist;
    private final FilePersist filePersist;
    
    public UserController(UsuarioPersist up, FilePersist fp){
        this.usuarioPersist = up;
        this.filePersist = fp;
    }
    
    public void save(Usuario u) {
    	String password= passToHash(u.getSenha());
    	
    	if(password != null) {
    		u.setSenha(password);

    		if(!this.existsLogin()){
	            this.usuarioPersist.save(u);
	        }else{
	            try {
					throw new UserAlreadyExists("Usuario ja existente.");
				} catch (UserAlreadyExists e) {
					e.printStackTrace();
				}
	        }
    	}else {
    		try {
				throw new UserPasswordException();
			} catch (UserPasswordException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public boolean existsLogin(){
        return this.usuarioPersist.existsLogin();
    }
    
    public boolean login(String user, String pass, boolean remember, boolean isEncrypt) {
    	String password = "";
    	if(isEncrypt) {
    		password = pass;
    	}else { //se nao tiver criptografada, criptografa a senha
    		password = passToHash(pass); //String para MD5
    	}
    	
    	Usuario us = this.usuarioPersist.verificaLogin(user,password); //persist retorna usuario caso tudo certo, caso contrario usuario null
    	
    	if(password != null) {
    		if(us != null){
    			//Login correto, inicia sessao e retorna true
    			
    			if(remember) { //checkbox remember foi selecionada, salvar arquivo com user
    				this.saveCredentials(us);
    			}
    			
    			//inicia session e salva usuario na session
    			SessionUser session = SessionUser.getInstancia();
    			
    			session.setSession("login", us);
    			
    			return true;
    		}
    	}else {
    		new UserPasswordException();
    	}
		return false;
    }
    
    // --------------------------- ARQUIVO ----------------------------------------
    
    public boolean saveCredentials(Usuario user) {
    	return this.filePersist.saveUser(user);
    }
    
    public boolean existsCredentials() {
    	return this.filePersist.existsFile();
    }
    
    public Usuario getCredentials() {
    	return this.filePersist.readUser();
    }
    
    // -------------------------- FIM ARQUIVO -------------------------------------
    
    //metodo helper para fazer o Hash da Senha
    private String passToHash(String pass) {
    	String hashed = null;
    	MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(Config.PASSWORD_ENCRYPTION);
            md.update(pass.getBytes(),0,pass.length());    
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
			hashed = new BigInteger(1,md.digest()).toString(16);
		}
		return hashed;
    }
}
