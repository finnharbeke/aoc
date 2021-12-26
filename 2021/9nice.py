from math import prod
n = 100
m = [[9]*(n+2)]+[[9]+[int(x) for x in input()]+[9] for i in range(n)]+[[9]*(n+2)]
t = 0
v = []
def size(i, j):
    if (i,j) in v or m[i][j] == 9:
        return 0
    v.append((i,j))
    return 1 + size(i-1, j) + size(i+1, j) + size(i, j-1) + size(i, j+1)

sizes = []
for i in range(1, n+1):
    for j in range(1, len(m[i])-1):
        if m[i][j] < m[i][j-1] and m[i][j] < m[i][j+1] and m[i][j] < m[i-1][j] and m[i][j] < m[i+1][j]:
            t += 1 + m[i][j]
            v = []
            sizes.append(size(i, j))

print(t)
print(prod(sorted(sizes)[-3:]))