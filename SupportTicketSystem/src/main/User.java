package main;

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
  private String key = "Bar12345Bar12345";

  public User(String username, String email, String password, String typeOfUser) throws SQLException{
    this.username = username;
    this.email = email;
    this.password = password;
    this.typeOfUser = typeOfUser;
  }
  public void addUserToDB() throws SQLException {
    boolean gate1 = false;
    boolean gate2 = false;
    
    if(doesUserNameExist()){
      System.out.println("Username is Taken");
    }else{
      gate1 = true;
    }
    if(IsEmailUsed()){
      System.out.println("Email is already Taken");
    }else{
      gate2 = true;
    }

    if((gate1) && (gate2)){
    this.username = hashUserAccount(this.username);
    this.password = hashUserAccount(this.password);
    //this.typeOfUser = hashUserAccount(this.typeOfUser);
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
  
  public String getUserName(){
    return username;
  }
  
  public String getEmail(){
    return email;
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
}