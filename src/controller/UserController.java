package controller;

import exceptions.UserAlreadyExists;
import helper.Helpers;
import model.SessionUser;
import model.Users;
import persist.FilePersist;
import persist.UserPersist;

public class UserController {
    
    private final UserPersist userPersist;
    private final FilePersist filePersist;
    
    public UserController(UserPersist up, FilePersist fp){
        this.userPersist = up;
        this.filePersist = fp;
    }
    
    public boolean save(Users u) {    
    	boolean success = false;
    	String password= Helpers.passToHash(u.getSenha()); // faz o hash da senha
    	
    	if(password != null) {
    		u.setSenha(password); //sobrescreve a senha com ela agora criptografada

    		if(!this.existsLogin(u.getNick())){ // se nao existir nick com o que o usuario digitou ele salva
	            this.userPersist.save(u);
	            success = true;
	        }else{
	        	new UserAlreadyExists("Nick already exists.");
	        }
    	}
    	
    	return success;
    }
    
    public boolean existsLogin(String nick){
        return this.userPersist.existsLogin(nick);
    }
    
    public boolean login(String user, String pass, boolean remember, boolean isEncrypt) {
    	String password = "";
    	if(isEncrypt) {
    		password = pass;
    	}else { //se nao tiver criptografada, criptografa a senha
    		password = Helpers.passToHash(pass); //String para MD5
    	}
    	
    	Users us = this.userPersist.verificaLogin(user,password); //persist retorna usuario caso tudo certo, caso contrario usuario null
    	
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
    	}
		return false;
    }
    
    // --------------------------- ARQUIVO ----------------------------------------
    
    public boolean saveCredentials(Users user) {
    	return this.filePersist.saveUser(user);
    }
    
    public boolean existsCredentials() {
    	return this.filePersist.existsFile();
    }
    
    public Users getCredentials() {
    	return this.filePersist.readUser();
    }
    
    public boolean deleteCredentials() {
    	return this.filePersist.deleteFile();
    }
    
    // -------------------------- FIM ARQUIVO -------------------------------------
    
    
    
}
