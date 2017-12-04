package model;

/*
 * Classe que auxilia no Login de Usuarios. 
 * Implementada com padrão Singleton.
 */

public class SessionUser {
	
	private static SessionUser session = null;
	
	private String sessionName;
	private Users user = null;
	
	public static SessionUser getInstancia() {
		if(session == null) {
			session = new SessionUser();
		}
		return session;
	}
	
	private SessionUser() {}
	
	public void setSession(String sessionName, Users user) {
		this.sessionName = sessionName;
		this.user = user;
	}
	
	public Users getUser(String sessionName) {
		if(this.sessionName.equals(sessionName)) {
			return this.user;
		}
		return null;
	}

}
