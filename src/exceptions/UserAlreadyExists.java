package exceptions;

import helper.Helpers;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(String message) {
        super(message);
        Helpers.throwExceptionDialog(this,message);
    }
    
    
}
