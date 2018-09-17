Instructions to Run/Test the Code:

1. Copy the two *.m files to a folder.
2. Open the neural_network.m file in matlab. Change the folder in matlab to the directory where the neural_network.m file is located.
3. Make sure the txt files with training and testing data are also located in the same directory.
4. Run the function in Matlab with this format/command
--> neural_network(training file, testing file, layers, units, rounds)
   example: "neural_network('pendigits_training.txt', 'pendigits_test.txt', 2, 20, 50)"
   another example: neural_network('pendigits_training.txt', 'pendigits_test.txt', 3, 20, 20)
   
   
** WARNING **
Code execution requires time! (2-3 minutes or more depending on the system and the inputs given)