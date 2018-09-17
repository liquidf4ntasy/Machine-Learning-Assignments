classdef calc_nn < handle
properties
train_file;   test_file;    rounds;   trainData;
testData;   target;  test_target;   first_wghts;   op_wghts;    op_acivation;
phi;    test_phi;    attr_no;   no_of_samples;    no_of_units;    unique_class;
op_units;    NOL;    act;    net;   hidden_weights;    net_1;    act_1;
op_act;    delta;    step;    del;    delta_op;
total_accuracy;   act_res;    acc_res;  pred_res;
end

methods (Static)
%Static methods do not require an object of the class.
function [par] = calc_nn(training_file, test_file, layers, units_per_layer, rounds)
par.train_file = training_file;
par.test_file = test_file;
par.rounds = rounds;
par.trainData = load(par.train_file);
par.testData = load(par.test_file);
par.attr_no = size(par.trainData, 2);
par.no_of_samples = size(par.trainData, 1);
par.no_of_units = units_per_layer;
par.target = par.trainData(1: end, end);
par.test_target = par.testData(1: end, end);
par.unique_class = unique(par.target);
par.unique_class = sort(par.unique_class);
par.op_units = size(par.unique_class, 1);
par.NOL = layers;
if par.NOL > 3
par.NOL = 3;
end
par.step = 0.98;
par.total_accuracy = 0;
par.del = zeros(par.no_of_units, par.NOL-2);
end

%begin initialise phase.
function [par] = initialise(par)
ones = zeros(par.no_of_samples, 1);
ones(ones == 0) = 1;
par.phi = par.trainData(1: end, 1: end-1);
maximum = max(par.phi(:));
par.phi = (par.phi)/(maximum);
par.phi = [par.phi ones];
ones = zeros(size(par.testData, 1), 1);
ones(ones == 0) = 1;
par.test_phi = par.testData(1: end, 1: end-1);
maximum = max(par.test_phi(:));
par.test_phi = (par.test_phi)/(maximum);
par.test_phi = [par.test_phi ones];
par.act_res = zeros(size(par.testData, 1), 1);
par.acc_res = zeros(size(par.testData, 1), 1);
par.pred_res = zeros(size(par.testData, 1), 1);
par.first_wghts = -0.05 + (0.05- (-0.05)).*rand(size(par.phi, 2), par.no_of_units);
par.act = zeros(par.no_of_units+1, par.NOL-2);
par.net = zeros(par.no_of_units, par.NOL-2);

if par.NOL > 2
par.op_wghts = -0.05 + (0.05- (-0.05)).*rand(par.no_of_units+1, par.op_units);
else
par.op_wghts = -0.05 + (0.05- (-0.05)).*rand(size(par.phi, 2), par.op_units);
end

par.hidden_weights = -0.05 + (0.05- (-0.05)).*rand(par.no_of_units+1, par.no_of_units, par.NOL-3);
par.delta_op = zeros(par.op_units, 1);
end


function [par] = feed_forward(par, round)
for row = 1:size(par.phi, 1)
for layer = 1:par.NOL-1

if layer == par.NOL-1 && par.NOL > 2
par.net_1 =  transpose(par.op_wghts(1:end, 1:end)) * (par.act(1:end, layer-1));
par.op_act = logsig(par.net_1);

elseif layer == par.NOL-1 && par.NOL == 2
par.net_1  = transpose(par.op_wghts(1:end, 1:end)) * transpose(par.phi(row, 1:end));
par.op_act = logsig(par.net_1);

elseif layer == 1
par.net(1:end, layer) = transpose(par.first_wghts) * transpose(par.phi(row, 1:end));
par.act(1:end-1, layer) = logsig(par.net(1:end, layer));
par.act(end, layer) = 1;

elseif layer > 1 && layer < par.NOL-1
par.net(1:end, layer) = transpose(par.hidden_weights(1:end, 1:end, layer-1)) * (par.act(1:end, layer-1));
par.act(1:end-1, layer) = logsig(par.net(1:end, layer));
par.act(end, layer) = 1;
end
end
par = par.back_prop(par, round, row);
end
end


function [par] = back_prop(par, round, row)
tg = par.target(row, 1);
pos = find(par.unique_class == tg);
rate = power(par.step,round);

for layer = par.NOL-1: -1: 1
if layer == par.NOL-1 && par.NOL > 2
for unit = 1:par.op_units
if unit == pos
t = 1;
else
t = 0;
end
o = par.op_act(unit, 1);
delta = (o-t)*o*(1-o);
par.delta_op(unit, 1) = delta;
for wght = 1:par.no_of_units+1
par.op_wghts(wght, unit) = par.op_wghts(wght, unit) - (rate*delta*par.act(wght, end));
end
end

elseif layer == par.NOL-1 && par.NOL == 2
for unit = 1:par.op_units
if unit == pos
t = 1;
else
t = 0;
end
o = par.op_act(unit, 1);
delta = (o-t)*o*(1-o);
par.delta_op(unit, 1) = delta;
for wght = 1:size(par.phi, 2)
par.op_wghts(wght, unit) = par.op_wghts(wght, unit) - (rate*delta*par.phi(row, wght));
end
end

elseif layer == par.NOL-2 && layer > 1 && par.NOL >3
for unit = 1:par.no_of_units
par.del(unit, layer) = transpose(par.delta_op)*transpose(par.op_wghts(unit, 1:end));
o = par.act(unit, end);
par.del(unit, layer) = par.del(unit, layer)*o*(1-o);
par.delta = par.del(unit, layer);

for wght = 1:par.no_of_units+1
par.hidden_weights(wght, unit, end) = par.hidden_weights(wght, unit, end)-rate*par.delta*par.act(wght, end-1);
end
end

elseif layer < par.NOL-2 && layer > 1 && par.NOL >3

for unit = 1:par.no_of_units
par.del(unit, layer) = transpose(par.del(1:end, layer+1))*transpose(par.hidden_weights(unit, 1:end, layer));
o = par.act(unit, layer);
par.del(unit, layer) = par.del(unit, layer)*o*(1-o);
par.delta = par.del(unit, layer);
for wght = 1:par.no_of_units+1
par.hidden_weights(wght, unit, layer-1) = par.hidden_weights(wght, unit, layer-1)-rate*par.delta*par.act(wght, layer-1);
end
end


elseif layer == 1 && par.NOL == 3

for unit = 1:par.no_of_units
par.del(unit, layer) = transpose(par.delta_op)*transpose(par.op_wghts(unit, 1:end));
o = par.act(unit, 1);
par.del(unit, layer) = par.del(unit, layer)*o*(1-o);
par.delta = par.del(unit, layer);
for wght = 1:size(par.phi, 2)
par.first_wghts(wght, unit) = par.first_wghts(wght, unit)-(rate*par.delta*par.phi(row, wght));
end
end


elseif layer == 1 && par.NOL > 3
for unit = 1:par.no_of_units

par.del(unit, layer) = transpose(par.del(1:end, layer+1))*transpose(par.hidden_weights(unit, 1:end, 1));
o = par.act(unit, 1);
par.del(unit, layer) = par.del(unit, layer)*o*(1-o);
par.delta = par.del(unit, layer);
for wght = 1:size(par.phi, 2)
par.first_wghts(wght, unit) = par.first_wghts(wght, unit)-rate*par.delta*par.phi(row, wght);
end
end
end
end
end

%begin testing phase.
function [par] = testing(par)
for row = 1:size(par.test_phi, 1)
for layer = 1:par.NOL-1

if layer == par.NOL-1 && par.NOL > 2
par.net_1 =  transpose(par.op_wghts(1:end, 1:end)) * (par.act(1:end, layer-1));
par.op_act = logsig(par.net_1);

elseif layer == par.NOL-1 && par.NOL == 2
par.net_1  = transpose(par.op_wghts(1:end, 1:end)) * transpose(par.test_phi(row, 1:end));
par.op_act = logsig(par.net_1);

elseif layer == 1
par.net(1:end, layer) = transpose(par.first_wghts) * transpose(par.test_phi(row, 1:end));
par.act(1:end-1, layer) = logsig(par.net(1:end, layer));
par.act(end, layer) = 1;
elseif layer > 1 && layer < par.NOL-1
par.net(1:end, layer) = transpose(par.hidden_weights(1:end, 1:end, layer-1)) * (par.act(1:end, layer-1));
par.act(1:end-1, layer) = logsig(par.net(1:end, layer));
par.act(end, layer) = 1;

end
end
max_act = max(par.op_act(:));
pos = find(par.op_act ==  max_act);
predicted = par.unique_class(pos);
test_t = par.test_target(row, 1);

%check accuracy for predictions
acc= 0;
if predicted == test_t
acc = 1;
end

par.act_res(row, 1) = max_act;
par.acc_res(row, 1) = acc;
par.pred_res(row, 1) = predicted;
par.total_accuracy = par.total_accuracy+acc;
% print results of classification
fprintf('ID=%5d, predicted=%3d, true=%3d, accuracy=%4.2f \n', row-1, predicted, test_t, acc);
end

fprintf('classification accuracy=%6.4f  ', (sum(par.acc_res)/size(par.test_phi, 1)));
end

end
end