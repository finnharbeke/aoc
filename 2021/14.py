to = dict()
count = dict()
for i, line in enumerate(open("14.txt").readlines()):
    if i == 0:
        start = line[:-1]
        front = start[0]
        end = start[-1]
        for j in range(1, len(start)): # NNCB -> {'NN':1, 'NC':1, 'CB':1}
            count[start[j-1:j+1]] = count.get(start[j-1:j+1], 0) + 1
        count[start[j-1:j+1]] = count.get(start[j-1:j+1], 0) + 1
    elif i > 1:
        to[line[:2]] = line[-2] # AB -> C
def result(d: dict):
    mi, ma = -1, -1
    for i in range(ord("A"), ord("Z")+1):
        l = chr(i)
        c = sum([d[key] * key.count(l) for key in d] + [int(l == front), int(l == end)]) // 2
        if c == 0:
            continue
        ma = max(ma, c)
        mi = min(mi, c) if mi >= 0 else c
    return ma-mi
for i in range(40):
    new = dict()
    for key in count:
        new[key[0] + to[key]] = new.get(key[0] + to[key], 0) + count[key]
        new[to[key] + key[1]] = new.get(to[key] + key[1], 0) + count[key]
    count = new
    if i in [9, 39]:
        print(result(count))