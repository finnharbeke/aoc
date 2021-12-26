w1 = input().split(',')
w2 = input().split(',')
def segments(w, i=0, x=0, y=0, t=0):
    if i >= len(w):
        return []
    n = int(w[i][1:])
    x2, y2, = x, y
    if w[i].startswith('U'):
        y2 += n
    elif w[i].startswith('D'):
        y2 -= n
    elif w[i].startswith('R'):
        x2 += n
    elif w[i].startswith('L'):
        x2 -= n
    return [(x, y, x2, y2, w[i], t)] + segments(w, i+1, x2, y2, t+n)

def cross(s1, s2):
    x, y = s1[:2]
    n = int(s1[4][1:])
    crosses = []
    for i in range(n+1):
        if (min(s2[0], s2[2]) <= x <= max(s2[0], s2[2]) and 
            min(s2[1], s2[3]) <= y <= max(s2[1], s2[3])):
            
            crosses.append((x, y, s1[5] + i + s2[5] + (max(abs(s2[0]-x), abs(s2[1]-y)))))

        if s1[4].startswith('U'):
            y += 1
        elif s1[4].startswith('D'):
            y -= 1
        elif s1[4].startswith('R'):
            x += 1
        elif s1[4].startswith('L'):
            x -= 1
    return crosses

s1s = segments(w1)
s2s = segments(w2)
m1 = None
where1 = (0, 0)      
m2 = None
where2 = (0, 0)      

for s1 in s1s:
    for s2 in s2s:
        for c in cross(s1, s2):
            if c[:2] != (0, 0):
                d = sum(abs(x) for x in c[:2])
                if m1 is None or d < m1:
                    m1, where1 = d, c
                if m2 is None or c[2] < m2:
                    m2, where2 = c[2], c

print(f"part 1: {m1=} {where1=}")
print(f"part 2: {m2=} {where2=}")
