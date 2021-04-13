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
  private Connection Conn = new ConnectToDB().getConnect();

  public User(String username, String email, String password) throws SQLException{
    this.username = username;
    this.email = email;
    this.password = password;
    typeOfUser = "User";
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
    this.username = hashUserAccounts(this.username);
    this.password = hashUserAccounts(this.password);
    this.email = hashUserAccounts(this.email);
    this.typeOfUser = hashUserAccounts(this.typeOfUser);
    String selectSQL = "INSERT INTO Users Values(?,?,?,?); ";
    PreparedStatement insert;
	try {
	insert = Conn.prepareStatement(selectSQL);
	insert.setString(1, this.username);
	insert.setString(2, this.password);
	insert.setString(3, this.email);
	insert.setString(4, this.typeOfUser);
	insert.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    }


  }

 public String hashUserAccount(String input){
   try{
    String text = input;
    String key = "Bar12345Bar12345";
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(text.getBytes());
    String encryptedString = (new String(encrypted));
    return encryptedString;
    }catch(Exception e){
	System.out.println("Hashing Error");
	}  

}

 public String decryptUserAccount(String input){
   try{
    String text = input;
    String key = "Bar12345Bar12345";
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    String decrypted = new String(cipher.doFinal(encrypted));
    retrun decrypted;
  }catch(Exception e){

    System.out.println("Decryption Error");
   }   

 }

  public boolean doesUserNameExist() throws SQLException{
    String selectSQL = "SELECT * FROM Users WHERE USERNAME = ?;";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.username);
    search.executeQuery();
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
    String selectSQL = "SELECT * FROM Users WHERE EMAIL = ?;";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.email);
    search.executeQuery();
    ArrayList<String> list = new ArrayList<String>();
    while(rs.next()){
      String name = rs.getString("email");
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