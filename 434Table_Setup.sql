use SupportTicketDB

Create Table Users(
	Username varchar(20) Not Null,
	Password varchar(20) Not Null,
	Email varchar(50) Not Null,
	AccountType varchar(16) Not Null,
	Primary Key(Email)
);

use SupportTicketDB

Create Table Ticket(
	TicketID int Not Null,
	Title varchar(20) Not Null,
	requesterID varchar(20) Not Null,
	Catagory varchar(20) Not Null,
	Description varchar(180),
	Priority varchar(20),
	DueDate Date,
	primary Key (TicketID),
);

Create Table History(
	DateSubmitted Date Not Null,
	TicketID varchar(20) Not Null,
	AgentName varchar(20) Not Null,
	Description varchar(180) Not Null,

);


