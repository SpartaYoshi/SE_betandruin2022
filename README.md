# SE_betandruin2022
 Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).

 Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.
 Name of the team: 
 
 
 
 + a brief explanation of what you did for this sprint (including the main problems -technical or not - you faced  and how they were solved)
 
 + any other explanation worth mentioning (for example, any extra work done that you would like to hightlight or a justification of not-implemented work)
 
 
 
 
 
 
 
 
 
 
 
 
 + set a Fee use case:
 To implement this use case I needed to create a new class in the Domain. This class is called Fee and has three different attributes.
 The first one is just an identifier, the second one is the result of a question (for example, the result for who is going to win question, could 
 be 'Real Sociedad'). The last attribute is the actual fee, how big is the benefit you get if your answer is correct.
 Now, every question has a list of Fees, since a question can have more than one answer and more than one fee.
 Speaking about the GUI, I created a new JFrame, which will be only available for the admin user. I had to check that there were events and questions for the selected date, and that a fee was not set.(For that I created an Exception). I had some issues when loading the calendar (since it was empty), and that was because
 the businessLogic was not properly instantiazed, so the GUI couldn't load it properly.