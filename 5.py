lines = []
in_ =  ""

# hello meal
minx = 1000000
miny = 1000000
maxx = -1
maxy = -1

d = []
for i in range(500):
    in_ = input()
    x1 = int(in_.split(" -> ")[0].split(",")[0])
    y1 = int(in_.split(" -> ")[0].split(",")[1])
    x2 = int(in_.split(" -> ")[1].split(",")[0])
    y2 = int(in_.split(" -> ")[1].split(",")[1])
    if x1 > x2:
        t = x1
        x1 = x2
        x2 = t
        t = y1
        y1 = y2
        y2 = t
    lines.append([x1, y1, x2, y2])
    #print(in_ , lines[-1])
    maxx = max(maxx, x1, x2)
    maxy = max(maxy, y1, y2)
    minx = min(miny, x1, x2)
    miny = min(miny, y1, y2)

#print(miny, maxy, minx, maxx, (maxy-miny) * (maxx-minx))

c = []
i = 0
count = 0
for i in range(miny, maxy+1):
    c.append([0] * (maxx-minx+1))

for line in lines:
    x1, y1, x2, y2 = line
    #print(x1, y1, x2, y2)
    if x1 == x2 or y1 == y2:
        for x in range(x1, x2+1):
            step = 1 if y2 >= y1 else -1
            for y in range(y1, y2+step, step):
                c[y-miny][x-minx] += 1
    else:
        for i in range(x2-x1 + 1):
            x = x1 + i
            y = y1 + i if y1 <= y2 else y1 - i
            c[y-miny][x-minx] += 1

for y in range(miny, maxy+1):
    for x in range(minx, maxx+1):
        if c[y-miny][x-maxx] > 1:
            count += 1

#print("\n".join(["".join([str(x) if x > 0 else "." for x in row]) for row in c]))

print(count)