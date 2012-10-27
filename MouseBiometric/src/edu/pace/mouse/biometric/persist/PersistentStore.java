package edu.pace.mouse.biometric.persist;

import java.sql.Connection;
import java.sql.DriverManager;

public class PersistentStore {

	public static DataStore getDataStore(){
		return new H2DataStore();
	}
	
	static Connection getConnection(){
		try {
			Class.forName("org.h2.Driver");
			return DriverManager.getConnection("jdbc:h2:data/data", "sa", ""); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
