Instructions to Run/Test the Code:
1. Copy logistic_regression.m file and whatever train/test text file to a folder.
2. Open the *.m file in matlab. Change the folder in matlab to the directory where the *.m file is located.
3. Make sure the txt file with training inputs and outputs is also located in the same directory.
4. Run the function in Matlab with this format/command
--> logistic_regression ('<training_file>',<degree>,<test_file>)


example: 
>> logistic_regression ('pendigits_training.txt',1,'pendigits_test.txt')
for degree=1

>> logistic_regression ('pendigits_training.txt',2,'pendigits_test.txt')
for degree=2
