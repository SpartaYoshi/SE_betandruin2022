# SE_betandruin2022
 Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).(1st Iteration)

 Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.
 
 Name of the team: Ainhoa Corporation Ltd.
 
 
 + Account login use case:
 The login screen's objective is to prevent users to enter accounts where they do not have access to. For that, a Failed Login system check has been implemented to
 ensure that the credentials given are the correct ones registered in the database (username and password). So, when they log-in they can use the service Bet&Ruin.
 
 
 
 + Account registration use case:
 This section corresponds to the creation of identifiable user accounts that are required to use the service Bet&Ruin provides. For the correct implementation of registration
 guidelines and requirements, a few new Exceptions have been made in order to check that the data given by the new user follows the criteria to be elligible to use the platform,
 such as the minimum legal age for betting and choosing available usernames to avoid accidental duplication.
 
 
 + Set a Fee Use Case:
 To implement this use case we needed to create a new class in the Domain. This class is called Fee and has three different attributes.
 The first one is just an identifier, the second one is the result of a question (for example, the result for who is going to win question, could 
 be 'Real Sociedad'). The last attribute is the actual result, how big is the benefit you get if your answer is correct.
 Now, every question has a list of Fees, since a question can have more than one answer and more than one result.
 Speaking about the GUI, I created a new JFrame, which will be only available for the admin user. I had to check that there were events and questions for the selected date, 
 and that a result was not set.(For that I created an Exception). I had some issues when loading the calendar (since it was empty), and that was because
 the businessLogic was not properly instantiazed, so the GUI couldn't load it properly.
 
 
 
 + Create Event Use Case: 
Our task in this section is to add a new event, so that we can create a question or set a result on that event later. It has been necessary to create a new GUI called CreateEventGUI, as well as several new methods in some of the classes: firstly, in the Business Logic, a “createEvent” method, and secondly, in the database, the general "createEvent" method. To ensure its correct operation, it has been essential to implement a method called "isAnyTeamPlaying", which checks if one of the teams is assigned to another event on the same date (they must be written correctly, that is, with the first letter capitalized, since they belong to proper names). Having selected a date, (not before the current one), when clicking on the button to create an Event, it is requested to write the name of each team. As mentioned above, should any of those teams not have another event on that date, the event will be successfully created and added to our database and thus the event combo box. Finally, the calendar will indicate that there is an event on that specific date, painting it.



+ Remove a Bet Use Case:
  To implement this use case we needed to have a list of bets in each user, which will represent
  the bets they have made. So taking into account which bet we want to remove and all the bets the user has in that list, the system has to be able to remove the bet from the users list, and also, the system must refound the concrete amount of many placed in that bet in the user bank account. However, if the list does not contain that bet, the system will show an error message.


+ **Final Note**:
To login as an administrator, we have created a new User.[*username*: **"juanan"**, *password*: **"hello"**]
	Because of that, the databaseMode at the beginning is as *initialize*. Once that user has been created, the mode can be changed to *open* again.
 