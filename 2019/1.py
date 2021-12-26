print(sum([int(int(x[:-1])/3)-2 for x in open("1.txt").readlines()]))
print(sum([[f:=lambda x: int(x/3)-2+f(int(x/3)-2) if x > 5 else 0,f(int(x[:-1]))][-1] for x in open("1.txt").readlines()]))