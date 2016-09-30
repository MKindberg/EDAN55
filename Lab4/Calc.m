%% three
n=4;
rand = ones(n)/n;
prob = [0 2/3 1/3 0;0 0 1 0;1/3 1/3 0 1/3;1/4 1/4 1/4 1/4];
totProb = rand*0.15+prob*0.85;
[1 0 0 0]*totProb^100

%% three
n=5;
rand = ones(n)/n;
prob=[0.0 1.0 0.0 0.0 0.0 ;
0.0 0.0 0.4 0.4 0.2 ;
0.0 0.0 0.0 1.0 0.0 ;
1.0 0.0 0.0 0.0 0.0 ;
0.5 0.0 0.5 0.0 0.0 ;];
totProb = rand*0.15+prob*0.85;
[1 0 0 0 0]*totProb^100