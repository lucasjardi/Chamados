package facade;

import java.util.List;

import controller.CallController;
import controller.LocalController;
import controller.PermissionsController;
import controller.TwitterController;
import controller.UserController;
import helper.Config;
import model.Calls;
import model.Locals;
import model.Permissions;
import model.SessionUser;
import model.Users;
import persist.CallPersist;
import persist.FilePersist;
import persist.LocalPersist;
import persist.PermissionsPersist;
import persist.UserPersist;

public class Facade {
    
    private static Facade facade = null;
    
    private final UserController usercontrol;
    private final TwitterController twittercontrol;
    private final CallController chamadoControl;
    private final LocalController localControl;
    private final PermissionsController permissionsControl;
    private final SessionUser currentSession;
    
    public static boolean hibernateStarted = false;
    
    private Facade(){
        this.usercontrol = new UserController(new UserPersist(), new FilePersist());
        this.twittercontrol = new TwitterController();
        this.chamadoControl = new CallController(new CallPersist());
        this.localControl = new LocalController(new LocalPersist());
        this.permissionsControl = new PermissionsController(new PermissionsPersist());
        this.currentSession = SessionUser.getInstancia();
        hibernateStarted = true; // variavel auxiliar
    }
    
    public static Facade getInstancia(){
        if(facade == null){
            facade = new Facade();
        }
        return facade;
    }
    
    // metodos de users
    
    public boolean login(String user, String pass,boolean remember, boolean isEncrypt) {
    	return this.usercontrol.login(user, pass, remember,isEncrypt);
    }
    
    public Users getUser(String session){
    	return this.currentSession.getUser(session);
    }
    
    public boolean saveUser(Users u) {
        return this.usercontrol.save(u);
    }
    
    public boolean loginWithTwitter() {
    	return this.twittercontrol.loginTwitter();
    }
    
    // fim de metodos de users
    
    
    // metodos de Chamados
    
    public void saveChamado(Calls c){
        this.chamadoControl.save(c);
    }

    public List<Calls> listChamadosWaiting(){
    	return this.chamadoControl.waitList();
    }
    
    public List<Calls> listAllChamados(){
    	return this.chamadoControl.listAll();
    }
    
    public void deleteCall(Calls c) {
    	this.chamadoControl.deleteCall(c);
    }
    
    public void changeStatus(Calls chamado) {
    	this.chamadoControl.changeStatus(chamado);
    }
    
    // fim de metodos de Chamados
    
    
    // metodos de Locais
    
    public void saveLocal(Locals l){
        this.localControl.save(l);
    }
    
    public List<Locals> listAllLocals(){
    	return this.localControl.listAllLocals();
    }
    
    // fim de metodos de Locais
    
    
    
    // metodos de Permissoes
    
    public Permissions getPermissionById(Integer id) {
		return this.permissionsControl.getById(id);
    }
    
    public void savePermission(Permissions p){
        this.permissionsControl.save(p);
    }
    // fim de metodos de Permissoes
    
    
    
    // metodos de arquivo de credencial
    
    public boolean existsCredentials() {
    	return this.usercontrol.existsCredentials();
    }
    
    public Users getCredentials() {
    	return this.usercontrol.getCredentials();
    }
    
    public boolean deleteCredentials() {
    	return this.usercontrol.deleteCredentials();
    }
    
    // fim de metodos de arquivo de credencial
    
    
    
    
    //metodo que retorna caminho da View baseado na permissao do Usuario
    
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
