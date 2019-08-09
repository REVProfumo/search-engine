This is a search engine which is executed through script ruleEngine.sh,
which installs the engine and runs it.
The script needs an argument, the path to the folder where to scan for files
and perform the searches.

The files content is represented through a FileObject.
The build of the FileObject instances is done asynchronously at startup of the application.

The engine perform searches on files through a pool of asynchronous threads,
waiting for threads to terminate before displaying results.

The match criterion is:
1) 100% is all words match
2) else, it is number of words that match divided by the total number of words given as input.

In order to exit the program, one needs to run ":quit" command at the
"|search>" prompt.

I include JUnit dependency in order to define and run unit tests.
There are two of them, one testing the FileObject constructor,
second one testing the match of the words (and so the asynchronous flow as well).