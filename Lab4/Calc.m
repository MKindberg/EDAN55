%%
fileID = fopen('wikipedia.txt');
txt = textscan(fileID, '%f');
M = cell2mat(txt);
n = M(1);

rand = ones(n)/n;

prob = zeros(n);
for i=2:2:length(M)
    prob(M(i)+1, M(i+1)+1)=prob(M(i)+1, M(i+1)+1)+1;
end
for i=1:n
    s = sum(prob(i,:));
    for j=1:n
        if s==0
            prob(i, j)=1/n;
        else
            prob(i, j)=prob(i, j)/s;
        end
    end
end

totProb = rand*0.15+prob*0.85;
start = [1 zeros(1,n-1)];
start*totProb^100
fclose(fileID);