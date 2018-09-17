function [] = neural_network(training_file, test_file, layers, units_per_layer, rounds)
    object = calc_nn(training_file, test_file, layers, units_per_layer, rounds);
    object = object.initialise(object);
    for k = 1:object.rounds
        object = object.feed_forward(object, k-1);
    end
    object = object.testing(object);
end