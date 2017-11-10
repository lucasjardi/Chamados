package persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Usuario;

public class FilePersist {
		
	public boolean saveUser(Usuario user) {
		try {
			FileOutputStream f = new FileOutputStream(new File("credentials.file"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(user);

			o.close();
			f.close();
			
			return true;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
		
		return false;
		
	}
	
	public Usuario readUser() {
		Usuario user = null;
		
		try {
			FileInputStream fi = new FileInputStream(new File("credentials.file"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			user = (Usuario) oi.readObject();
			
			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean existsFile() {
		return new File("credentials.file").exists();
	}
	
}
