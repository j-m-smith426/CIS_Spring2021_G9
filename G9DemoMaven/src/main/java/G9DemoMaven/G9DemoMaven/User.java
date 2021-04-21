package G9DemoMaven.G9DemoMaven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class User {
  private String username;
  private String email;
  private String password;
  private String typeOfUser;
  ConnectToDB connection = new ConnectToDB();
  Connection Conn = connection.getConnect();
  //String key is the key that is used to encrypt and decrypt everything in the project
  private String key = "Bar12345Bar12345";
  private static  User currentUser = null; 

  public User(String username, String email, String password, String typeOfUser) throws SQLException{
    this.username = username;
    this.email = email;
    this.password = password;
    this.typeOfUser = typeOfUser;
  }
//Function to call to add the User to the SQL Database
  public void addUserToDB() throws SQLException {
    boolean gate1 = false;
    boolean gate2 = false;
// Calls doesUserNameExist to see if it can unlock the first gate, if username is taken in the sql it will not add the user
    if(doesUserNameExist()){
      System.out.println("Username is Taken");
    }else{
      gate1 = true;
    }
// Calls IsEmailUsed to see if it can unlock the second gate, if Email is taken in the sql it will not add the user
    if(IsEmailUsed()){
      System.out.println("Email is already Taken");
    }else{
      gate2 = true;
    }
// If both Username and Email is not in the Database of Users then it calls the function hashUSerAccount to encrypt the Username and Password then Inserts it into the SQL
    if((gate1) && (gate2)){
    this.username = hashUserAccount(this.username);
    this.password = hashUserAccount(this.password);
    String selectSQL = "INSERT INTO Users Values(?,?,?,?); ";
    PreparedStatement insert;
	try {
	insert = Conn.prepareStatement(selectSQL);
	insert.setString(1, this.username);
	insert.setString(2, this.password);
	insert.setString(3, this.email);
	insert.setString(4, this.typeOfUser);
	insert.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    }


  }
// hashUserAccount encrypts whatever String you input into the function with AES and the Key.
 public String hashUserAccount(String input){
	 String encryptedString = input;
	 try{
    String text = input;
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(text.getBytes());
    encryptedString = (new String(encrypted));
    
    }catch(Exception e){
	System.out.println("Hashing Error");
	}  
	 return encryptedString;
}
// this decrypts Strings if you need to do so
 public String decryptUserAccount(String input){
	 String decrypted = input;
	 try{
    String text = input;
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(input.getBytes());
     decrypted = new String(cipher.doFinal(encrypted));
    
  }catch(Exception e){

    System.out.println("Decryption Error");
   }   
	 return decrypted;
 }
// searches the SQL User Table for any accounts with the Username that is inputed, if its there then it returns true, if not false
  public boolean doesUserNameExist() throws SQLException{
    String selectSQL = "SELECT * FROM Users WHERE USERNAME = ?;";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, hashUserAccount(this.username));
    ResultSet rs = search.executeQuery();
    ArrayList<String> list = new ArrayList<String>();
    while(rs.next()){
      String name = rs.getString("username");
      list.add(name);
    }
    if(list.isEmpty()){
      return false;
    }else{
      return true;
    }

  }
// searches the SQL User Table for any accounts with the Email that is inputed, if its there then it returns true, if not false
  public boolean IsEmailUsed() throws SQLException{
    String selectSQL = "SELECT * FROM Users WHERE Email = ?;";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.email);
    ResultSet rs = search.executeQuery();
    ArrayList<String> list = new ArrayList<String>();
    while(rs.next()){
      String name = rs.getString("Email");
      list.add(name);
    }
    if(list.isEmpty()){
      return false;
    }else{
      return true;
    }
  }

//List of Get and Set functions that lets you have set or get the private variables for the object
  public String getEmail(){
	return this.email;
  }
  
  public String getUserName(){
    return username;
  }
  
  public String getPassword(){
    return password;
  }
  
  public String getTypeOfUser(){
    return typeOfUser;
  }

  public void setUserName(String input){
    this.username = input;
  }
  
  public void setEmail(String input){
    this.email = input;
  }
  public void setPassword(String input){
    this.password = input;
  }
  
  public void setTypeOfUser(String input){
    this.typeOfUser = input;
  }
public static User getCurrentUser() {
	return currentUser;
}
public static void setCurrentUser(User currentUser) {
	User.currentUser = currentUser;
}
}