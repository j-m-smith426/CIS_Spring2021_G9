package main;

import java.sql.SQLException;

public class SupportTicket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			User a = new User("TestUser", "testemail@email.com", "Password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
