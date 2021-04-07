package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.ArrayList;

class User {
  private String username;
  private String email;
  private String password;
  private String typeOfUser;

  public User(String username, String email, String password){
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
      //hashUserAccount();
    String selectSQL = "INSERT INTO USER (?,?,?,?) ";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.username);
    search.setString(2, this.password);
    search.setString(3, this.email);
    search.setString(4, this.typeOfUser);

    }


  }


  public boolean doesUserNameExist(){
    String selectSQL = "SELECT * FROM USER WHERE USERNAME = ?";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.username);
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

  public boolean IsEmailUsed(){
    String selectSQL = "SELECT * FROM USER WHERE EMAIL = ?";
    PreparedStatement search = Conn.prepareStatement(selectSQL);
    search.setString(1, this.email);
    ResultSet rs = search.executeQuery();
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