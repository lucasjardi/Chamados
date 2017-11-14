package controller;


import java.io.IOException;
import java.util.Optional;

import config.Config;
import exceptions.UserAlreadyExists;
import fachada.Fachada;
import javafx.scene.control.TextInputDialog;
import model.SessionUser;
import model.Usuario;
import persist.UsuarioPersist;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterController {
	
	private final UsuarioPersist usuarioPersist;
	
	public TwitterController(UsuarioPersist up) {
		 this.usuarioPersist = up;
	}
	

    public boolean loginTwitter(){
    	
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.API_KEY,Config.API_SECRET);

        RequestToken requestToken = null;
        try {
            requestToken = twitter.getOAuthRequestToken();
        } catch (TwitterException ex) {
            ex.printStackTrace();
            // erro ao estabelecer conexao com twitter
        }
        AccessToken accessToken = null;

        // pega URL de autorizacao do twitter conforme o que a API alimenta
        String authURL = requestToken.getAuthorizationURL();
        // na linha abaixo eu chamo uma funcao que acessa o browser
        try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(authURL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String pin = inputPrompt();
        try{
            if(!pin.equals("")){
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                
                if(accessToken!= null) {
                	User twitterUser = twitter.showUser(twitter.getId());
                    Usuario newUser = new Usuario();
                    
                    newUser.setNick(twitterUser.getScreenName());
                    newUser.setNome(twitterUser.getName());
                    newUser.setPermissao(Fachada.getInstancia().getPermissionById(2));
                    
                    this.usuarioPersist.save(newUser);
                    
                    SessionUser session = SessionUser.getInstancia();
                    session.setSession("login", newUser);
                    
                    return true;  	
                }               
                
            }
        } catch (TwitterException te) {
            if(401 == te.getStatusCode()){
                // PIN nao estava correto
            } else {
                te.printStackTrace();
            }
        }
        return false;
    }
    
    private String inputPrompt() {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("PIN input");
    	dialog.setHeaderText("Put your pin bellow here: ");
    	dialog.setContentText("PIN:");

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    return result.get();
    	}
    	
    	return "";
    }
    
//    public void save(Usuario u) {
//    	
//		if(!this.existsLogin(u.getNick())){
//            this.usuarioPersist.save(u);
//        }else{
//            try {
//				throw new UserAlreadyExists("Usuario ja existente.");
//			} catch (UserAlreadyExists e) {
//				e.printStackTrace();
//			}
//        }
//
//    }
//    
//    public boolean existsLogin(String nick){
//        return this.usuarioPersist.existsLogin(nick);
//    }
}
