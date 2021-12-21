p1 = 10
p2 = 9
p1s = 0
p2s = 0

c = 0
while (p1s < 1000 and p2s < 1000):
    if c % 2 == 0:
        p1 = (p1 + 9*c + 6) % 10
        p1 = 10 if p1 == 0 else p1
        p1s += p1
    else:
        p2 = (p2 + 9*c + 6) % 10
        p2 = 10 if p2 == 0 else p2
        p2s += p2
    c += 1
print(min(p1s, p2s) * c * 3)


poss = {3: 1, 4: 3, 5: 6, 6: 1 + 6, 7: 6, 8: 3, 9: 1}
print(sum(poss.values()))
scores = {(0, 0, 10, 9): 1}
p1w = 0
p2w = 0

c = 0
while sum(scores.values()):
    news = {}
    if c % 2 == 0:
        for throw in poss:
            for s, os, pos, opos in scores:
                n = scores[(s, os, pos, opos)]
                np = (pos + throw) % 10
                np = 10 if np == 0 else np
                news[(s+np, os, np, opos)] = news.get((s+np, os, np, opos), 0) + poss[throw] * n
        scores = news

    else:
        for throw in poss:
            for os, s, opos, pos in scores:
                n = scores[(os, s, opos, pos)]
                np = (pos + throw) % 10
                np = 10 if np == 0 else np
                news[(os, s+np, opos, np)] = news.get((os, s+np, opos, np), 0) + poss[throw] * n
        scores = news
    rem = []
    for s1, s2, p1, p2 in scores:
        if s1 >= 21:
            p1w += scores[(s1, s2, p1, p2)]
            rem.append((s1, s2, p1, p2))
        elif s2 >= 21:
            p2w += scores[(s1, s2, p1, p2)]
            rem.append((s1, s2, p1, p2))
    for tup in rem:
        scores.pop(tup)

    c += 1

print(p1w, p2w, max(p1w, p2w))

    # print(p1, p2,  p1s, p2s, c)
    