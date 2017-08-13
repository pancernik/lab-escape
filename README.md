## Example input

	OOOOOOOOOO
	O    O   O
	O OO O O O
	O  O O O O
	O OO   O  
	O OOOOOOOO
	O        O
	OOOOOOOOOO
	
Starting point: (x=3, y=1)
 	
 	OOOOOOOOOO
	O    O   O
	O OO O O O
	O• O O O O
	O OO   O  
	O OOOOOOOO
	O        O
	OOOOOOOOOO
	
Desired output:
	
	OOOOOOOOOO
	O••••O•••O
	O•OO•O•O•O
	O• O•O•O•O
	O OO•••O••
	O OOOOOOOO
	O        O
	OOOOOOOOOO
	
## Notes

As the problem has some open questions, I assumed the solution should accept multiple
types of mazes (including ones with cycles). Therefore, I've chosen to implement the solution 
using BSF with backtracking.

I also focused on the correctness of the algorithm first, and decided to test multiple error cases 
(e.g. invalid starting position, invalid maze, invalid size) later. Should I have more time, 
I'd test those cases.

Additionally, if I knew the nature of clients of this application (API), I could consider different
encoding of the HTTP messages. Most likely, I would make the API RESTfull and JSON-speaking as
that's what the clients would probably prefer.

I would also avoid using an exception in the case of no escape path. Such a maze should be considered 
correct (e.g. Minotaur's Labirynth), therefore the application should not use exceptions 
to handle this case.
