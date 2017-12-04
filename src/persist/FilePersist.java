package persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import helper.Config;
import helper.Helpers;
import model.Users;

public class FilePersist {
		
	public boolean saveUser(Users user) {
		FileOutputStream f = null;
		ObjectOutputStream o = null;
		
		try {
			f = new FileOutputStream(new File(Config.CREDENTIALS_FILE));
			o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(user);

			return true;

		} catch (FileNotFoundException e) {
			Helpers.throwExceptionDialog(e, "File not found exception.");
//			System.out.println("File not found");
		} catch (IOException e) {
//			System.out.println("Error initializing stream");
			Helpers.throwExceptionDialog(e, "Error initializing stream.");
		} finally {
			if(f!= null && o!=null) {
				try {
					f.close();
					o.close();
				} catch (IOException e) {
					Helpers.throwExceptionDialog(e, "IO Exception");
//					e.printStackTrace();
				}
			}
		}
		
		return false;
		
	}
	
	public Users readUser() {
		Users user = null;
		FileInputStream fi = null;
		ObjectInputStream oi = null;
		
		try {
			fi = new FileInputStream(new File(Config.CREDENTIALS_FILE));
			oi = new ObjectInputStream(fi);

			user = (Users) oi.readObject();
			
		} catch (FileNotFoundException e) {
//			Helpers.throwExceptionDialog(e, "File not found Exception.");
		} catch (IOException e) {
//			Helpers.throwExceptionDialog(e, "Error Initializing Stream.");
		} catch (ClassNotFoundException e) {
//			Helpers.throwExceptionDialog(e, "Class not found exception.");
		} finally {
			if(fi!=null && oi!=null) {
				try {
					fi.close();
					oi.close();
				} catch (IOException e) {
//					Helpers.throwExceptionDialog(e, "IO Exception.");
				}
			}
		}
		
		return user;
	}
	
	public boolean existsFile() {
		return new File(Config.CREDENTIALS_FILE).exists();
	}
	
	public boolean deleteFile() {
		if (this.existsFile()) {
			return new File(Config.CREDENTIALS_FILE).delete();
		}
		return false;
	}
}
