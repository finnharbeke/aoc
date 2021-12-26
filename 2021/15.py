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

q = [(0, 0, 0, 0, 0)]
v = [[False for j in range(len(m[i]))] for i in range(len(m))]
d = [[-1 for j in range(len(m[i]))] for i in range(len(m))]
p = [[(i, j) for j in range(len(m[i]))] for i in range(len(m))]
while len(q):
    q.sort(key=lambda x: x[0])
    dst, i, j, pi, pj = q.pop(0)
    if d[i][j] >= 0:
        continue
    d[i][j] = dst
    p[i][j] = (pi, pj)
    v[i][j] = True
    if (i == len(m)-1 and j == len(m[i])-1):
        print(dst)
        path = [(i, j)]
        while not(pi == p[pi][pj][0] and pj == p[pi][pj][1]):
            path.append((pi, pj))
            pi, pj = p[pi][pj]
        path.append((0, 0))
        open("15path.txt", "w").write("\n".join(["".join('#' if (x, y) in path else ' ' for x in range(j+1)) for y in range(i+1)]))
        # break
    for ni, nj in [(-1, 0), (0, -1), (0, 1), (1, 0)]:
        if (i + ni) >= 0 and i + ni < len(m) and (j + nj) >= 0 and j + nj < len(m[i + ni]) and not v[i+ni][j+nj]:
            q.append((dst+m[i+ni][j+nj], i + ni, j+nj, i, j))

open("15d.txt", "w").write("\n".join(["|".join(f'{x:>2}' for x in r) for r in d]))