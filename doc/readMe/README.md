# SE_betandruin2022
Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).(3rd Iteration)

Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.

Name of the team: Ainhoa Corporation Ltd.

                                        MANDATORY USE CASES


-**RemoveEvent** use case: To implement this use case, we had to take into account that all references to the Event were declared as PERSIST.TYPE=ALL, that means that it will have a cascade effect both on removing and persisting events.
With this, we ensure that when an Event is removed, all its questions are also removed, and all the results of the question, and all the bets of the result...
Finally, the user's list of bets needed to be updated, so we had to do that "manually", checking which user had a bet related to the deleted event. If that's the case, the bet will have all it's attributes marked as null, so we just have to delete it.

-**PublishResults** use case: In this case, we had to implement a GUI for the admin to select a question and a result.
Once they are correctly selected, (s)he will just click on 'Mark as final result'. Automatically, the question will be marked as processed, and the selected result will be marked as correct.
The rest of the results, will not be treated, so their finalResult value will be false. Apart from this, it will check all the users that have bet on that result, so they canwin the money they deserve. (amount*fee)
Losers are not take into account, since they were charged when they bet.

-**ShowMovements** use case: To implement this use case, we had to take into account that a new class is needed, in this case, we created a new class called Movement, in which we will have the date of the money movement, the description (if the money is deposited, won or the user has placed a bet or cancelled one of them), and also the amount of money the user gained or lost. As we take the dates when the movement is done (current or instant date and hour), all the movements will be represented in a table ordered by the date, and, obviously, the current amount of money will be printed above, so as the user
is able to see how much money does he/she has. At that moment and all the movements done.


                                OPTIONAL USE CASES AND GENERAL IMPROVEMENTS:

-**MyProfile** use case: This use case has been created in order to display all the information of the current user. It allows also to navigate to other use cases, which are Show Movements, Edit Password, or Check My Results. 

-**EditPassword** use case: This application also gives the option to the user to modify his/her password. So this use case allows the user to do it. Only the password, since the application takes the name, surname, birthdate and username as permanent. The system shows an error message in two cases. First one, when the user writes the same password as he/she already had and, the second one, when the field is not filled. Once the password has been changed, we persist the user, so that the change is permanent.

-**CheckMyResults** use case: To implement this use case it has been necessary to create new attributes and methods, that check if its respective question has been processed or not and also to check which is the final answer, as indicated in publish result, in order to fill the tables. The first table is the one that indicates the event, questionId and selected result, and the second table indicates if it is the correct answer (true) or not (false). If no results of the user match the ones respective to the processed questions, a message will appear with an advertisement.

-**Fetch API:**

-**Questions translated:**


-**Remote Database and businessLogic:**



**`Final Note`**:
    + To login as an administrator:[*username*: **"juanan"**, *password*: **"hello"**]
    + To login as a regular user: [*username*: **"ainhoa"**, *password*: **"123"**]
      Because of that, the remote database is as *initialize*. Once that user has been created, the mode can be changed to *open* again.
