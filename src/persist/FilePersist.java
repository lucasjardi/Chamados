package persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import config.Config;
import model.Usuario;

public class FilePersist {
		
	public boolean saveUser(Usuario user) {
		FileOutputStream f = null;
		ObjectOutputStream o = null;
		
		try {
			f = new FileOutputStream(new File(Config.CREDENTIALS_FILE));
			o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(user);

			return true;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		} finally {
			if(f!= null && o!=null) {
				try {
					f.close();
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
		
	}
	
	public Usuario readUser() {
		Usuario user = null;
		FileInputStream fi = null;
		ObjectInputStream oi = null;
		
		try {
			fi = new FileInputStream(new File(Config.CREDENTIALS_FILE));
			oi = new ObjectInputStream(fi);

			user = (Usuario) oi.readObject();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(fi!=null && oi!=null) {
				try {
					fi.close();
					oi.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return user;
	}
	
	public boolean existsFile() {
		return new File(Config.CREDENTIALS_FILE).exists();
	}
	
}
