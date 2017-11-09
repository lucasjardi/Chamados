package model;

public class SessionUser {
	
	private static SessionUser session = null;
	
	private String sessionName;
	private Usuario user = null;
	
	public static SessionUser getInstancia() {
		if(session == null) {
			session = new SessionUser();
		}
		return session;
	}
	
	private SessionUser() {}
	
	public void setSession(String sessionName, Usuario user) {
		this.sessionName = sessionName;
		this.user = user;
	}
	
	public Usuario getUser(String sessionName) {
		if(this.sessionName.equals(sessionName)) {
			return this.user;
		}
		return null;
	}

}
