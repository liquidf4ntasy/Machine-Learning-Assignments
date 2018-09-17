function [] = logistic_regression(TrainF, degree, TestF)

% Name: Satayajit Deshmukh
% UTA ID: 1001417727
% CSE 6363 Machine Learning - Assigment 5

    traindata = double(load(TrainF));
    classification_target = traindata(1: end, end);
    traindata = traindata(:,1:end-1);
    classification_target(classification_target > 1) = 0;
    Previous_error=0;
    Rows = size(traindata,1);
    columns = size(traindata,2);
    phi = zeros(Rows,1);
    
    for row = 1: Rows
        phi(row, 1) = 1;
        x = 2;
        for col = 1: columns
            for deg = 1:degree
                        phi(row, x) = traindata(row, col)^deg;
                        x = x+1;
            end
        end           
    end
 
    stopping_criterion = true;
    weight = zeros(columns*degree+1,1);    
    phi_trans = transpose(phi);
    m = 1;  
    while stopping_criterion
        weightT = transpose(weight);

        for i = 1:Rows
            output(i,1) = weightT * phi_trans(1:end,i);
            output(i,1) = 1 / (1 + exp(output(i,1)*(-1)));
        end
    
        E = phi_trans * (output - classification_target);
        
        new_error = sum(E,1);
        error_diff = abs(new_error - Previous_error);
        
        R = zeros(Rows,Rows);
        for i = 1:Rows
            R(i,i) = output(i,1) * (1 - output(i,1));
        end

        new_weight = weight - inv(phi_trans * R * phi) * E ;
        
        
        stopping_criterion = abs(sum(new_weight) - sum(weight)) >= 0.001 &  error_diff>= 0.001;
        if stopping_criterion
            weight = new_weight;
            Previous_error = new_error;
        end
        m = m + 1;
    end
    
    test_traindata = double(load(TestF));
    classification_target = test_traindata(1: end, end);
    test_traindata = test_traindata(:,1:end-1);
    Rows = size(test_traindata,1);
    columns = size(test_traindata,2);
    phi = zeros(Rows,1);
    
    for row = 1: Rows
        phi(row, 1) = 1;
        x = 2;
        for col = 1: columns
            for deg = 1:degree
                        phi(row, x) = test_traindata(row, col)^deg;
                        x = x+1;
            end
        end           
    end
    
    phi_trans = transpose(phi);
    for i = 1:Rows
            output(i,1) = transpose(new_weight) * phi_trans(1:end,i);
            output(i,1) = 1 / (1 + exp(output(i,1)*(-1)));            
    end
    
    classification_target(classification_target > 1) = 0;
    predicted = zeros(size(output, 1), 1);
    accuracy = zeros(Rows, 1);    
    for i = 1:size(new_weight, 1)
        fprintf('w%d=%.4f\n', i-1, new_weight(i, 1));
    end
        
    for i = 1:Rows
        First = transpose(new_weight) * transpose(phi(i, 1:end));
        Second = output(i, 1) ;
        if (First > 0) && (Second > 0.5)
            predicted(i, 1) = 1;
            if predicted(i, 1)  == classification_target(i, 1)
                accuracy(i, 1) = 1;
            end
        elseif (First < 0) && (1 - Second > 0.5)
            predicted(i, 1) = 0;
            output(i, 1) = (1 - Second);
            if predicted(i, 1) == classification_target(i, 1)
                accuracy(i, 1) = 1;
            end
        else
            predicted(i, 1) = 1;
            accuracy(i, 1) = 0.5;
        end
		%print required output using below line.
        fprintf('ID=%5d, predicted=%3d, probability = %.4f, true=%3d, accuracy=%4.2f \n', i-1, predicted(i, 1), output(i, 1), classification_target(i, 1), accuracy(i, 1));
    end
        

    tempNum = sum(accuracy);
    denominator = size(accuracy, 1);
    final_accuracy = tempNum/denominator;
    fprintf('classification accuracy=%6.4f\n', final_accuracy) 
end