import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
      // add account to DB
    }


  }


  public boolean doesUserNameExist(){
    //for(int i = 0; )
    if(this.username.equals(userNameInDB)){
      return true;
    }else{
      return false;
    }

  }

  public boolean IsEmailUsed(){
    //for(int i = 0; )
    if(this.email.equals(emailInDB)){
      return true;
    }else{
      return false;
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
