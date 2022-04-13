# SE_betandruin2022
 Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).(2nd Iteration)

 Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.
 
 Name of the team: Ainhoa Corporation Ltd.

+ Migration to JavaFx:
At the beginning of the project we needed to import the previous use cases and GUIs into javaFX. Since it is the first time we are using intelliJ, we have encountered with several errors in the project (especially when pulling/pushing and recognizing the project as a maven one)
Once we solved the problems these are the main changes that we have implemented from the previous iteration:
 - We have implemented multiple language compatibility, so now all labels and texts are in Spanish Basque and English
 - Portals are now divided into anonymous user,registered user and administrator
 - Implemented session mode detection for window backtracking
 - Revamped prototypes: Login, Register and Portal
 - Fee class now is named as Result

+ Deposit money use case:
This is the first use case that we implemented. We just had to create a GUI for it and  attribute in User class.
This attribute (float) stores the actual money available the user has. We have implemented that, just after creating the User object, (s)he will start with 10€, to bet into anything it wants.
We have had some issues when updating the value of the user, since the current User and the user in the database are not exactly the identical object. However, we managed to update both.
Apart from that, the businessLogic now has a currentUser attribute, which stores the current user object. This is to know who is logged in, and who is placing bets.



+ Place a Bet Use Case:
To implement this use case, it has been mandatory to create a new Domain Class named Bet. This class has some attributes, which are: a numBet as an ID, the amount to bet (amount) and the result it belongs to (result). On the other side, we have also added a vector of Bets in both User Domain class and Result Domain Class. This implementation is prepared to throw some exceptions:  MinimumBetException, for whenever that minimum is not respected; EventFinished, if the selected date is previous to the current one; NotEnoughMoneyException, for when the current user has not enough available money deposited. Moreover, when a bet is placed, that specific amount of money must be substract from the available money of the user, therefore, we have implemented a method that substracts it.



+ Remove a Bet Use Case:
  To implement this use case, as mentioned in the one above, we needed to have a list of bets in each User and Result, which will represent
  the bets the current user has made. So taking into account which bet we want to remove and all the bets the user has, we fill the table. Now the system has to be able to remove the bet from the users list, the Result list and delete that Bet from the database. Also, the system must refound the concrete amount of money placed in that bet into the current user. If no bet was selected, the system will show an error message.


+ General issues: Apart from that, and as mentioned above, we think we have spend a lot of hours into this project especially because conflicts appeared when pulling / pushing or merging. In one occasion some changes were lost, so we had to implement them again.
  Finally, another issue that we have spend a lot of time in was the remove Bet. Here we were trying to remove the Bet before removing it from the Result list, so the Bet was never removed and all its attributes were null



+ **Final Note**:
To login as an administrator, we have created a new User.[*username*: **"juanan"**, *password*: **"hello"**]
    Because of that, the databaseMode at the beginning is as *initialize*. Once that user has been created, the mode can be changed to *open* again.
