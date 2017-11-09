package util;

import fachada.Fachada;

import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import model.Usuario;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterUtil {
    // funcao de login no twitter. Retorna true se logou e false se deu ruim
    public boolean loginTwitter(){
        Fachada facade = Fachada.getInstancia();

        Twitter twitter = new TwitterFactory().getInstance();
        // nessa linha abaixo vcs trocam pra key de vcs
        twitter.setOAuthConsumer("mYwpIZtLSixRpCh0RwK7xCUEG", "n7mNPviSW2V9m6ywtx9D2UHE9TopaSI6jqNX5kRw28yotcX083");

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
//        facade.util.openURL(authURL);

        // na linha abaixo eu chamo uma funcao que mostra um dialog pro usuario inserir o PIN
//        String pin = dialogInputPin();
        try{
        	 accessToken = twitter.getOAuthAccessToken(requestToken, "");
//            if(pin.length() > 0){
//                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//                // essa linha abaixo seleciona o usuario. A partir deste objeto vcs podem pegar o usuario e o avatar por exemplo.
//                User twitterUser = twitter.showUser(twitter.getId());
//                ForumUser u = new ForumUser(twitterUser.getName(), twitterUser.getOriginalProfileImageURL());
//                facade.saveUser(u);
//                return true;
//            }
        } catch (TwitterException te) {
            if(401 == te.getStatusCode()){
                // PIN nao estava correto
            } else {
                te.printStackTrace();
            }
        }
        return false;
    }
    
    private void inputPrompt() {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("PIN input");
    	dialog.setHeaderText("Put your pin bellow here: ");
    	dialog.setContentText("PIN:");

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    System.out.println("Your name: " + result.get());
    	}
    }
}
