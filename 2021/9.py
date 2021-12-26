m = [[int(x) for x in input()] for i in range(100)]
t = 0
v = []
def size(i, j):
    if (i,j) in v:
        return 0
    v.append((i,j))
    s = 1
    if i > 0 and m[i][j] < m[i-1][j] and m[i-1][j] < 9:
        s += size(i-1, j)
    if i < len(m)-1 and m[i][j] < m[i+1][j] and m[i+1][j] < 9:
        s += size(i+1, j)
    if j > 0 and m[i][j] < m[i][j-1] and m[i][j-1] < 9:
        s += size(i, j-1)
    if j < len(m[i])-1 and m[i][j] < m[i][j+1] and m[i][j+1] < 9:
        s += size(i, j+1)
    return s

sizes = []

for i in range(100):
    for j in range(len(m[i])):
        if (j == 0 or m[i][j] < m[i][j-1]) and (j == len(m[i])-1 or m[i][j] < m[i][j+1]) and (i == 0 or m[i][j] < m[i-1][j]) and (i == len(m)-1 or m[i][j] < m[i+1][j]):
            t += 1 + m[i][j]

            v = []
            sizes.append(size(i, j))
            #print(sizes[-1], i, j)
r = 1
for i in range(3):
    r *= max(sizes)
    sizes.remove(max(sizes))

print(t)
print(r)