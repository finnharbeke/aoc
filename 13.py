dots = set()
[dots.add(tuple([int(_) for _ in l.split(",")])) for l in iter(input, "")]

first = True
for i, line in enumerate(iter(input, "")):
    dim, f = line.split()[-1].split("=")
    f = int(f)
    r = set()
    n = set()
    for x, y in dots:
        if dim == "x" and x > f:
            r.add((x, y))
            n.add((f - (x-f), y))
        elif dim == "y" and y > f:
            r.add((x, y))
            n.add((x, f - (y-f)))
    dots = dots.difference(r).union(n)
    print(len(dots)) if i == 0 else None

for y in range(0, max(map(lambda xy: xy[1], dots))+1):
    for x in range(0, max(map(lambda xy: xy[0], dots))+1):
        print("#", end='') if (x,y) in dots else print(' ', end='')
    print()
