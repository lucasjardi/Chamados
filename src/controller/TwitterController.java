package controller;


import java.io.IOException;

import persist.FilePersist;
import facade.Facade;
import helper.Config;
import helper.Helpers;
import model.SessionUser;
import model.Users;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterController {
	
    public boolean loginTwitter(){
    	
    	boolean successLogin = false;
    	
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Config.API_KEY,Config.API_SECRET);

        RequestToken requestToken = null;
        try {
            requestToken = twitter.getOAuthRequestToken();
        } catch (TwitterException ex) {
        	Helpers.throwExceptionDialog(ex, "Twitter Exception.");
        }
        AccessToken accessToken = null;
        
        String authURL = requestToken.getAuthorizationURL();
       
        try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(authURL));
		} catch (IOException e) {
			Helpers.throwExceptionDialog(e, "Browser start error!");
		}
        
        //recebe codigo pin do usuario
        String pin = Helpers.inputDialog("PIN Input", "Put your pin bellow here: ","PIN:");
        
        //bloco de codigo que faz a verificaçao do pin e se tudo certo ele salva
        try{
            if(! pin.isEmpty() ){
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                
                if(accessToken!= null) {
                	
                	String password = "";
                	String passwordConfirmation = "";
                	do{
                		password = Helpers.inputDialog("Register Password", "For next time, you just need to inform your twitter nick and this password: ","Password:");
                    	passwordConfirmation = Helpers.inputDialog("Confirm Password", "Please insert one more time: ","Password Again:");
                    	
                    	// aviso de q as senhas tem q coincidir
                    	if ( !password.equals(passwordConfirmation) ) {
							Helpers.simpleDialog("Password invalid", "Passwords don't match. Try again!", "");
						}
                    	
                	}while(!password.equals(passwordConfirmation));
                	
                	
                	// salvando novo usuario e setando sessao
                	if (! password.isEmpty() && ! passwordConfirmation.isEmpty() ) {
                		
                		// resgatando usuario do twitter. User é do twitter. Users é minha model de usuario
                		User twitterUser = twitter.showUser(twitter.getId());
                		
                        Users newUser = new Users();
                        
                        newUser.setNick(twitterUser.getScreenName());
                        newUser.setNome(twitterUser.getName());
                        newUser.setPermissao(Facade.getInstancia().getPermissionById(2));
                        newUser.setSenha(password);
                        
                        if ( Facade.getInstancia().saveUser(newUser) ) {
                        	
                        	// Para usuarios que logam com twitter já salvo no arquivo pra nao precisar logar na proxima vez
                        	FilePersist fp = new FilePersist();
                            if(! fp.existsFile() ){
                            	fp.saveUser(newUser);
                            }
                            fp = null;
                            
                            
                            
                            // seta session                        
                            SessionUser session = SessionUser.getInstancia();
                            session.setSession("login", newUser);
                            
                            // sucesso
                            successLogin = true;
                            
                        }
                      
                	}
                }               
                
            }
        } catch (TwitterException te) {
            if(401 == te.getStatusCode()){
            } else {
            	Helpers.throwExceptionDialog(te, "Twitter Exception");
            }
        }
        
        //retorna true se tudo ok
        return successLogin;
    }
    

}
