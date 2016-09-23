%% Initiate
x1=30:10:60;
x2=30:10:100;
x3=30:10:120;

y1=[19233 158158 1056907 8795945];
y2=[121, 1443 5709 32986 136998 575018 1939484 8065592];
y3=[33, 220 1062 5749 22012 87227 262750 1012472 3062183 8830836];

p1=polyfit(x1, log(y1), 1);
p2=polyfit(x2, log(y2), 1);
p3=polyfit(x3, log(y3), 1);

%% Polynomial
plot(x1, polyval(p1, x1));
hold on
plot(x1, log(y1));

figure
plot(x2, polyval(p2, x2));
hold on
plot(x2, log(y2));

figure
plot(x3, polyval(p3, x3));
hold on
plot(x3, log(y3));

%% Exponential
close all
plot(x1, exp(p1(2)).*exp(p1(1)).^x1);
hold on
plot(x1, y1);
C0 = exp(p1(2))
R0 = exp(p1(1))

figure
plot(x2, exp(p2(2)).*exp(p2(1)).^x2);
hold on
plot(x2, y2);
C1 = exp(p2(2))
R1=exp(p2(1))

figure
plot(x3, exp(p3(2)).*exp(p3(1)).^x3);
hold on
plot(x3, y3);
C2 = exp(p3(2))
R2=exp(p3(1))
