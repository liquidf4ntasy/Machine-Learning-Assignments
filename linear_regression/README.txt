Instructions to Run/Test the Code:
1. Copy linear_regression.m file and whatever input text file to a folder.
2. Open the *.m file in matlab. Change the folder in matlab to the directory where the *.m file is located.
3. Make sure the txt file with training inputs and outputs is also located in the same directory.
4. Run the function in Matlab with this format/command
--> linear_regression ('<training_file>',<degree>,<?>)

[ Refer Matlab Screenshot.jpg included in the directory ]

example: 
>> linear_regression ('sample_data1.txt',1,0)
for degree=1, lambda = 0

>> linear_regression ('sample_data1.txt',2,0)
for degree=2, lambda = 0
