package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import exceptions.UserAlreadyExists;
import exceptions.UserPasswordException;
import model.Usuario;
import persist.UsuarioPersist;

public class UserController {
    
    private final UsuarioPersist usuarioPersist;
    
    public UserController(UsuarioPersist up){
        this.usuarioPersist = up;
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
    
    public Usuario login(String user, String pass) {
    	String password= passToHash(pass);
    	
    	if(password != null) {
    		return this.usuarioPersist.verificaLogin(user,password);
    	}else {
    		new UserPasswordException();
    		return null;
    	}
    }
    
    private String passToHash(String pass) {
    	String hashed = null;
    	MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes(),0,pass.length());    
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
			hashed = new BigInteger(1,md.digest()).toString(16);
		}
		return hashed;
    }
}
