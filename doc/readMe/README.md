# SE_betandruin2022
***Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).***
(3rd Iteration)

**Editors:** Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.

**Name of the team:** Ainhoa Corporation Ltd.『アインフノア』



                                        MANDATORY USE CASES


- **RemoveEvent**

We have taken into account that all references to the Event were declared as PERSIST.TYPE=ALL, 
which implies that it will have a cascade effect on both removing and persisting events.

With this, we ensure that when an Event is removed, its questions, possible results, bets set... are also removed.

Finally, the user's list of bets needed to be updated, so we have to do that "manually", by 
checking which user had a bet related to the deleted event.
If that's the case, the bet will have all its attributes marked as null, while we delete it.



- **PublishResults**

We have implemented an interface for the admin to select a question and a result.
Once they are correctly selected, they will just click on 'Mark as final result'. 
Automatically, the question will be marked as processed, and the selected result will be marked as correct.
The rest of the results, will not be treated (final results have a boolean attribute to determine their state). 

Apart from this, it will check all the users that have bet on that result, so they rightfully receive their profits (amount * fee).
Bet losers are not taken into account, since they were charged when they bet.


- **ShowMovements**

We have had to take into account that a new class is needed in order to show balance movement history.
In this case, we have created a new class called Movement, in which we will have the date, 
type (if the money is deposited, won or the user has placed a bet or cancelled one of them), 
and amount of money gained or lost by the user. 

As we take the dates when a movement is done (current or instant date and hour), 
all the movements will be represented in a table ordered by the date, and, furthermore, 
the current amount of money will be printed above, so as the user
is able to see how much money they have.


                                OPTIONAL USE CASES AND GENERAL IMPROVEMENTS:

- **MyProfile**

The application counts with a menu that displays the user's personal information.
It also counts with navigation to more personal features, which are Show Movements, Edit Password, and Check My Results. 

- **EditPassword** 

This application also gives the option to the user to modify their password.
The user can only change passwords, as other personal info is maintained as permanent.

The system shows an error message in two cases. 
First one, when the user writes the same password as they already had and, the second one, when the field is not filled. 

Once the password has been changed, we persist the user, so that the change is permanent.

- **CheckMyResults** 

To implement this use case it has been necessary to create new attributes and methods, that check if its respective question has been processed or not and also to check which is the final answer, as indicated in publish result, in order to fill the tables. The first table is the one that indicates the event, questionId and selected result, and the second table indicates if it is the correct answer (true) or not (false). If no results of the user match the ones respective to the processed questions, a message will appear with an advertisement.

- **Data fetching from API:**

The application makes use of an API to fetch online data about football matches, and parses them in such way that they are
automatically managed by our business logic so that our events are detected and processed accordingly, and users who have won
their bets receive profits.

- **Questions translated:**

The application counts with a language changing option, with three languages: English, Spanish and Basque. The application
requires a fresh restart so the user can enjoy the application on their preferred language.

- **Remote Database and business logic:**

The application also counts with a remote database server to which the user gets connected to use our features. It also makes
it possible for the user to log-in automatically after restarting the application, as the server keeps track of it.

**`Final Note`**:

    + To login as an administrator:[*username*: **"juanan"**, *password*: **"hello"**]

    + To login as a regular user: [*username*: **"ainhoa"**, *password*: **"123"**]

      Because of that, the remote database is as *initialize*.
      Once that user has been created, the mode can be changed to *open* again.
