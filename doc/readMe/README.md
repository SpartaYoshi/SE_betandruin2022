# SE_betandruin2022
Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).(3rd Iteration)

Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.

Name of the team: Ainhoa Corporation Ltd.

+**RemoveEvent()** use case: To implement this use case, we had to take into account that all references to the Event were declared as PERSIST.TYPE=ALL, that means that it will have a cascade effect both on removing and persisting events.
With this, we ensure that when an Event is removed, all its questions are also removed, and all the results of the question, and all the bets of the result...
Finally, the user's list of bets needed to be updated, so we had to do that "manually", checking which user had a bet related to the deleted event. If that's the case, the bet will have all it's attributes marked as null, so we just have to delete it.



+ **Final Note**:
  To login as an administrator:[*username*: **"juanan"**, *password*: **"hello"**]
    + To login as a regular user: [*username*: **"ainhoa"**, *password*: **"123"**]
      Because of that, the remote database is as *initialize*. Once that user has been created, the mode can be changed to *open* again.
