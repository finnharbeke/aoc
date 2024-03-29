m1 = [[int(x) for x in input()] for i in range(10)]
flashed = set()
f = 0

def flash(i, j):
    global f, flashed
    if (i, j) in flashed:
        return
    flashed.add((i, j))
    f += 1
    for ni, nj in [(-1,-1), (-1, 1), (1, -1), (1, 1), (-1, 0), (1, 0), (0, -1), (0, 1)]:
        if i + ni >= 0 and i+ni < len(m1) and j + nj >= 0 and j+nj < len(m1[i]):
            m1[i+ni][j+nj] += 1
            if m1[i+ni][j+nj] > 9:
                flash(i+ni, j+nj)

for s in range(500):
    flashed = set()
    for i in range(len(m1)):
        for j in range(len(m1[i])):
            m1[i][j] += 1
            if m1[i][j] > 9:
                flash(i, j)
    for (i, j) in flashed:
        m1[i][j] = 0
    if s == 99:
        print(f)
    if len(flashed) == len(m1) * len(m1[0]):
        print(s+1)
        break