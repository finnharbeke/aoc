xs = [int(_) for _ in input().split(",")]
print(sum(xs)/len(xs))
def gauss(n):
    return int(n * (n+1) / 2)
print(gauss(11))
m = -1
for i in range(max(xs)):
    d = sum([gauss(abs(x - i)) for x in xs])
    if m < 0 or d < m:
        m = d
    #print(d)
print(m)