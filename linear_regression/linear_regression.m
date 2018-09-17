function [lm_model] = linear_regression(name,deg,lam)
K = dlmread(name);
[r,c]=size(K);
x=K(:,1);
t=K(:,2);

phi = zeros(r,deg+1); 
for i=1:r
    for j=1:deg+1
        phi(i,j)=power(x(i),(j-1));
    end
end
if lam==0
    w=((inv(vpa(transpose(phi)*phi)))*(transpose(phi))*t);
else
    I = eye(deg+1);
    w=((inv((lam*I) + vpa(transpose(phi)*phi)))*(transpose(phi))*t);
end
 
%lm_model= w;

fprintf('w0=%.4f\n', double(w(1,1)));
fprintf('w1=%.4f\n', double(w(2,1)));
if(deg==1)
    fprintf('w2=%.4f\n',0);
else
    fprintf('w2=%.4f\n',double(w(3,1)));
end

end