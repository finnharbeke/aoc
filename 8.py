digits = {
    0: [1, 1, 1, 0, 1, 1, 1],
    1: [0, 0, 1, 0, 0, 1, 0],
    2: [1, 0, 1, 1, 1, 0, 1],
    3: [1, 0, 1, 1, 0, 1, 1],
    4: [0, 1, 1, 1, 0, 1, 0],
    5: [1, 1, 0, 1, 0, 1, 1],
    6: [1, 1, 0, 1, 1, 1, 1],
    7: [1, 0, 1, 0, 0, 1, 0],
    8: [1, 1, 1, 1, 1, 1, 1],
    9: [1, 1, 1, 1, 0, 1, 1],
}
counts = dict()
for i in range(2, 8):
    counts[i] = []
    for k in digits:
        if sum(digits[k]) == i:
            counts[i].append(k)
letters = list("abcdefg")
c = 0
total = 0
for i in range(200):
    left, right = [x.split() for x in input().split("|")]
    p = [letters.copy() for i in range(7)]
    for x in right:
        if len(x) in [2, 4, 3, 7]:
            c += 1
    for x in left:
        if len(x) in [2, 4, 3, 7]:
            n = counts[len(x)][0]
            for l in letters:
                if l not in x:
                    for i in range(7):
                        if digits[n][i] == 1 and l in p[i]:
                            p[i].remove(l)
                        if len(p[i]) == 1:
                            for j in range(7):
                                if i != j:
                                    p[j].remove(p[i][0])
    r = 0
    while sum(len(p[i]) for i in range(7)) > 7:
        # number possibilities
        for x in left:
            ns = counts[len(x)].copy()
            for n in ns:
                for i in range(7):
                    if digits[n][i] == 1 and all([pl not in x for pl in p[i]]) and n in ns:
                        ns.remove(n)
            #print(ns)
            # remove letters from unused positions
            used = [False for i in range(7)]
            for n in ns:
                for i in range(7):
                    if digits[n][i] == 1:
                        used[i] = True
            #print(used)
            for i in range(7):
                if not used[i]:
                    for l in x:
                        if l in p[i]:
                            p[i].remove(l)
            on = [False for i in range(7)]
            for i, pl in enumerate(p):
                if all(pll in x for pll in pl):
                    on[i] = True
                for n in ns:
                    for j in range(7):
                        if digits[n][j] == 0 and on[j]:
                            ns.remove(n)
                if len(ns) == 1:
                    for xl in x:
                        for j in range(7):
                            if digits[ns[0]][j] == 0 and xl in p[j]:
                                p[j].remove(xl)
            #print(on)
        # remove if used up
        for length in range(1, 8):
            for i, pl in enumerate(p):
                if len(pl) == length:
                    if p.count(pl) == length:
                        for l in pl:
                            for j in range(7):
                                if p[j] != pl:
                                    for pll in pl:
                                        if pll in p[j]:
                                            p[j].remove(pll)
        #print(p)
        if r == 10:
            break
        r+=1

    res = ""
    for x in right:
        on = [0 for i in range(7)]
        for l in x:
            for i, pl in enumerate(p):
                if pl == [l]:
                    on[i] = 1
                    break
        for n in range(10):
            if digits[n] == on:
                res += str(n)
                break
    res = int(res)
    #print(res)
    total += res
#print(c)
print(total)