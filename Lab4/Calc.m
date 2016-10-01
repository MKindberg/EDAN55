fileID = fopen('tiny.txt');
txt = textscan(fileID, '%f');
M = cell2mat(txt);
n = M(1);

rand = ones(n)/n;

H=zeros(n);
D=zeros(n);
for i=2:2:length(M)
    H(M(i)+1, M(i+1)+1)=H(M(i)+1, M(i+1)+1)+1;
end
for i=1:n
    s = sum(H(i,:));
    for j=1:n
        if s==0
            D(i, j) = 1/n;
            %H(i, j)=1/n;
        else
            H(i, j)=H(i, j)/s;
        end
    end
end

%totProb = rand*0.15+H*0.85;
start = [1 zeros(1,n-1)];
%start*totProb^100
for i=1:100
    starto = start*(0.85*H+0.85*D+0.15*rand);
    if(round(starto, 2)==round(start, 2))
        i-1
        break;
    end
    start=starto;
end
start;
st = [linspace(0, n-1,n);start];
%sortrows(st', 2)
P=(0.85*H+0.85*D+0.15*rand);
fclose(fileID);