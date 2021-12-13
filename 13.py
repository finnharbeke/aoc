dots = set()
for line in iter(input, ""):
    x, y = [int(_) for _ in line.split(",")]
    dots.add((x, y))

first = True
for line in iter(input, ""):
    instr = line.split()[-1]
    dim, where = instr.split("=")
    where = int(where)

    new = set()
    remove = set()
    c = 0
    for x, y in dots:
        if dim == "x" and x > where:
            remove.add((x, y))
            new.add((where - (x-where), y))
        elif dim == "y" and y > where:
            remove.add((x, y))
            new.add((x, where - (y-where)))
            c += 1
    for d in new:
        dots.add(d)
    for d in remove:
        dots.remove(d)


    if first:
        print(len(dots))
        first = False

for y in range(min(map(lambda xy: xy[1], dots)), max(map(lambda xy: xy[1], dots))+1):
    for x in range(min(map(lambda xy: xy[0], dots)), max(map(lambda xy: xy[0], dots))+1):
        if (x,y) in dots:
            print("#", end='')
        else:
            print(' ', end='')
    print()
