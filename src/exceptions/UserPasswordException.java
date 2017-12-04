package exceptions;

import helper.Helpers;

public class UserPasswordException extends Exception{
	
	public UserPasswordException(String msg) {
		super(msg);
		Helpers.throwExceptionDialog(this,msg);
	}
}
