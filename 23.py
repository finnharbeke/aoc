hw = ['E'] * 11
rooms = [['D', 'C'], ['A', 'A'], ['C', 'B'], ['D', 'B']]
#rooms = [['B', 'A'], ['C', 'D'], ['B', 'C'], ['D', 'A']]
#rooms = [['A1', 'A2'], ['B1', 'B2'], ['C1', 'C2'], ['D1', 'D2']]
pos = [2, 4, 6, 8]

P = False
c = 0

n = 2
if True:
    n = 4
    rooms[0].insert(1, 'D')
    rooms[0].insert(2, 'D')
    rooms[1].insert(1, 'C')
    rooms[1].insert(2, 'B')
    rooms[2].insert(1, 'B')
    rooms[2].insert(2, 'A')
    rooms[3].insert(1, 'A')
    rooms[3].insert(2, 'C')

def print_m(hw, rooms):
    for ap in hw:
        print(ap if ap != 'E' else '.', end='')
    print()
    for i in range(n):
        print('  ', end='')
        for r in rooms:
            print(r[i] if r[i] != 'E' else '.', end=' ')
        print()

def foo(hw, rooms: list, path):
    # print(path)
    global c
    c += 1
    if c % 100000 == 0:
        print(c)
    corr = True
    for ix, r in enumerate(rooms):
        l = chr(ord('A')+ix)
        if not all([ap.startswith(l) for ap in r]):
            corr = False

    if corr:
        return [0], [path]

    if P:
        print_m(hw, rooms)

    for i, ap in enumerate(hw):
        if ap == 'E':
            continue
        ix = ord(ap)-ord('A')
        p = pos[ix]
        s = 1 if p >= i else -1
        if hw[i+s:p+s:s] == ['E'] * abs(i-p):
            for home in range(n)[::-1]:
                if all(rooms[ix][h].startswith(ap) for h in range(home+1, n)[::-1]) and rooms[ix][:home+1] == ['E'] * (home+1):
                    nr = [room.copy() for room in rooms]
                    nr[ix][home] = ap
                    nhw = hw.copy()
                    nhw[i] = 'E'
                    d = abs(i-(p+s)) + home
                    chscores, paths = foo(nhw, nr, path + [f"{ap} hw{i}->r{ix}{home} d{d} c{d*(10**ix)}"])
                    scores = [sc + d * (10 ** ix) for sc in chscores]
                    return scores, paths

    scores = []
    paths = []
    for ix, r in enumerate(rooms):
        l = chr(ord('A')+ix)
        for home, ap in enumerate(r):
            if ap == 'E':
                continue
            if (not ap.startswith(l) and r[:home] == ['E'] * home) or (
                ap.startswith(l) and r[:home] == ['E'] * home and any(not r[h].startswith(l) for h in range(home+1, n))):
                p = pos[ix]
                if not ap.startswith(l):
                    ix2 = ord(ap)-ord('A')
                    i = pos[ix2]
                    s = 1 if p >= i else -1
                    for home2 in range(n)[::-1]:
                        if all(rooms[ix][h].startswith(ap) for h in range(home+1, n)[::-1]) and rooms[ix][:home+1] == ['E'] * (home+1):
                            nr = [room.copy() for room in rooms]
                            nr[ix][home] = 'E'
                            nr[ix2][home2] = ap
                            d = abs(i-(p+s)) + home + 1 + home2
                            nhw = hw.copy()
                            chscores, paths = foo(nhw, nr, path + [f"{ap} r{ix}{home}->r{ix2}{home2} d{d} c{d*(10**ix2)}"])
                            scores = [sc + d * (10 ** ix2) for sc in chscores]
                            return scores, paths
                for i, ap2 in enumerate(hw):
                    if i in pos:
                        continue
                    s = 1 if p >= i else -1
                    if hw[i:p+s:s] == ['E'] * abs(i-(p+s)) and r[:home] == ['E']*home:
                        nr = [room.copy() for room in rooms]
                        nr[ix][home] = 'E'
                        nhw = hw.copy()
                        nhw[i] = ap
                        d = abs(i-(p+s)) + home 
                        ix2 = ord(ap)-ord('A')
                        chscores, npaths = foo(nhw, nr, path + [f"{ap} r{ix}{home}->hw{i} d{d} c{d*(10**ix2)}"])
                        paths += npaths
                        scores += [sc + d * (10 ** ix2) for sc in chscores]
    return scores, paths

scores, paths = foo(hw, rooms, [])
print(len(scores), len(paths), min(scores))
path = paths[scores.index(min(scores))]
for s in path:
    ap = s[:2]
    src, dst = s.split(' ')[1].split('->')
    if src.startswith('hw'):
        hw[int(src[2:])] = 'E'
    else:
        rooms[int(src[1])][int(src[2])] = 'E'
    if dst.startswith('hw'):
        hw[int(dst[2:])] = ap
    else:
        rooms[int(dst[1])][int(dst[2])] = ap
    print_m(hw, rooms)
    print(s.split()[-1])