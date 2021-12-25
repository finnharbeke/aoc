import re
s = set()
i = 0

class Cube:
    @staticmethod
    def disjoint(c: tuple, o: tuple):
        c[0] > o[1] or c[1] < o[0] or c[2] > o[3] or c[3] < o[2] or c[4] > o[5] or c[5] < o[4]

    @staticmethod
    def within(c: tuple, o: tuple):
        for i in range(6):
            if i % 2 == 0 and c[i] <= o[i]:
                return False
            elif i % 2 == 1 and c[i] >= o[i]:
                return False
        return True

    @staticmethod
    def exists(c: tuple):
        return c[0] <= c[1] and c[2] <= c[3] and c[4] <= c[5]

    @staticmethod
    def subtract(c: tuple, o):
        opts = [
            (c[0], min(c[1], o[0]-1), c[2], c[3], c[4], c[5]),
            (max(o[1]+1, c[0]), c[1], c[2], c[3], c[4], c[5]),
            (max(o[0], c[0]), min(o[1], c[1]), c[2], min(c[3], o[2]-1), c[4], c[5]),
            (max(o[0], c[0]), min(o[1], c[1]), max(o[3]+1, c[2]), c[3], c[4], c[5]),
            (max(o[0], c[0]), min(o[1], c[1]), max(o[2], c[2]), min(o[3], c[3]), c[4], min(o[4]-1, c[5])),
            (max(o[0], c[0]), min(o[1], c[1]), max(o[2], c[2]), min(o[3], c[3]), max(o[5]+1, c[4]), c[5]),
        ]
        #print(opts)
        return list(filter(lambda c: Cube.exists(c), opts))

    def volume(c):
        if not Cube.exists(c):
            return 0
        return (c[1] - c[0] + 1) * (c[3] - c[2] + 1) * (c[5] - c[4] + 1)

cubes = []
for line in open("22.txt").readlines():
    #print(i)
    i += 1
    line = line[:-1]
    x1, x2, y1, y2, z1, z2 = [int(x) for x in re.findall(r"-?\d+", line)]
    on = line.startswith("on")
    c = (min(x1, x2), max(x1, x2), min(y1, y2), max(y1, y2), min(z1, z2), max(z1, z2))
    for x in range(max(c[0], -50), min(51, c[1]+1)):
        for y in range(max(c[2], -50), min(51, c[3]+1)):
            for z in range(max(c[4], -50), min(51, c[5]+1)):
                if on:
                    s.add((x, y, z))
                elif (x, y, z) in s:
                    s.remove((x, y, z))

    j = 0
    while j < len(cubes):
        old = cubes[j]
        if Cube.disjoint(old, c):
            j += 1
            continue
        subs = Cube.subtract(old, c)
        if len(subs):
            cubes[j] = subs[0]
            j += 1
        else:
            # assert Cube.within(old, c)
            cubes.pop(j)
        #assert len(subs) > 0
        for sub in subs[1:]:
            cubes.insert(j, sub)
            j += 1
        #cubes = cubes[:j] + subs + cubes[j+1:]
        #j += len(subs)

    if on:
        cubes.append(c)
    print(f"lines done: {i}, {len(cubes)=}")

print(len(s))

c = sum(Cube.volume(c) for c in cubes)
e = 2758514936282235
print(f"{e=}\n{c=}")



