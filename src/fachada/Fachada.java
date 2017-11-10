package fachada;

import java.util.List;

import config.Config;
import controller.ChamadoController;
import controller.LocalController;
import controller.PermissionsController;
import controller.TwitterController;
import controller.UserController;
import model.Chamados;
import model.Local;
import model.Permissoes;
import model.SessionUser;
import model.Usuario;
import persist.ChamadoPersist;
import persist.FilePersist;
import persist.LocalPersist;
import persist.PermissoesPersist;
import persist.UsuarioPersist;

public class Fachada {
    
    private static Fachada fachada = null;
    
    private final UserController usercontrol;
    private final TwitterController twittercontrol;
    private final ChamadoController chamadoControl;
    private final LocalController localControl;
    private final PermissionsController permissionsControl;
    private final SessionUser currentSession;
    
    public static boolean hibernateStarted = false;
    
    private Fachada(){
        this.usercontrol = new UserController(new UsuarioPersist(), new FilePersist());
        this.twittercontrol = new TwitterController(new UsuarioPersist());
        this.chamadoControl = new ChamadoController(new ChamadoPersist());
        this.localControl = new LocalController(new LocalPersist());
        this.permissionsControl = new PermissionsController(new PermissoesPersist());
        this.currentSession = SessionUser.getInstancia();
        hibernateStarted = true;
    }
    
    public static Fachada getInstancia(){
        if(fachada == null){
            fachada = new Fachada();
        }
        return fachada;
    }
    
    public Usuario getUser(String session){
    	return this.currentSession.getUser(session);
    }
    
    public void saveUser(Usuario u) {
        this.usercontrol.save(u);
    }
    
    public void saveChamado(Chamados c){
        this.chamadoControl.save(c);
    }
    
    public void saveLocal(Local l){
        this.localControl.save(l);
    }
    
    public void savePermission(Permissoes p){
        this.permissionsControl.save(p);
    }
    
    public boolean login(String user, String pass) {
    	return this.usercontrol.login(user, pass);
    }
    
    public boolean loginWithTwitter() {
    	return this.twittercontrol.loginTwitter();
    }
    
    public List<Local> listAllLocals(){
    	return this.localControl.listAllLocals();
    }
    
    public List<Chamados> listChamadosWaiting(){
    	return this.chamadoControl.waitList();
    }
    
    public void changeStatus(Chamados chamado) {
    	this.chamadoControl.changeStatus(chamado);
    }
    
    public Permissoes getPermissionById(Integer id) {
		return this.permissionsControl.getById(id);
    }
    
    
    private boolean saveCredentials(Usuario user) {
    	return this.usercontrol.saveCredentials(user);
    }
    
    public boolean existsCredentials() {
    	return this.usercontrol.existsCredentials();
    }
    
    public Usuario getCredentials() {
    	return this.usercontrol.getCredentials();
    }
    
    public String getAuthPath() {
    	String PATH = Config.PATH_LOGIN;
    	
    	if(this.getUser("login") != null) {
        	
        	if(this.getUser("login").getPermissao().getId() == 1){
        		PATH = Config.PATH_ADMIN;
        	}
        	else if(this.getUser("login").getPermissao().getId() == 2) {
        		PATH = Config.PATH_CREATE_CHAMADO;
        	}
        	
    	} 
    	
    	return PATH;
    	
    }
}
