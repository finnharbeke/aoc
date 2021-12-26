om = [[int(x)  for x in line[:-1]] for line in open("15.txt").readlines()]
m = []
for li in range(5):
    for lj in range(5):
        o = li + lj
        f = lambda x: x%9 if x%9 != 0 else 9
        for i in range(len(om)):
            if lj == 0:
                m.append([])
            for j in range(len(om[i])):
                m[li * len(om) + i].append(f(om[i][j] + o))

d = [[-1 for j in range(len(m[i]))] for i in range(len(m))]
p = [[(i, j) for j in range(len(m[i]))] for i in range(len(m))]
h = len(m)*len(m[0])*9
for i in range(len(m)):
    for j in range(len(m[i])):
        opts = [
            d[i-1][j] + m[i][j] if i > 0 else h,
            d[i][j-1] + m[i][j] if j > 0 else h,
            d[i-1][j+1] + m[i][j+1] + m[i][j] if i > 0 and j<len(m[i])-1 else h,
            d[i-1][j+1] + m[i-1][j] + m[i][j] if i > 0 and j<len(m[i])-1 else h
        ]
        d[i][j] = min(opts)
        
        if i+j==0:
            d[i][j] = 0
        else:
            p[i][j] = [(i-1, j), (i, j-1), (i, j+1), (i-1, j+1)][opts.index(d[i][j])]
for i in range(len(m)):
    for j in range(len(m[i])):
        opts = [
            d[i][j],
            d[i+1][j-1] + m[i+1][j] + m[i][j] if i < len(m)-1 and j>0 else h,
            d[i+1][j-1] + m[i][j-1] + m[i][j] if i < len(m)-1 and j>0 else h
        ]
        d[i][j] = min(opts)
        p[i][j] = [p[i][j], (i+1, j), (i, j-1)][opts.index(d[i][j])]
print(d[-1][-1])
i, j = len(m)-1, len(m[i])-1
path = []
while (i, j) != (0, 0):
    path.append((i, j))
    i, j = p[i][j]

    # print(i, j)
path.append((0, 0))
open("15path2.txt", "w").write("\n".join(["".join('#' if (x, y) in path else ' ' for x in range(500)) for y in range(500)]))
open("15d2.txt", "w").write("\n".join(["|".join(f'{x:>2}' for x in r) for r in d]))