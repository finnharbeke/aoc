pts = 0
scores = []
op, cl, p = ['(', '[', '{', '<'], [')', ']', '}', '>'], [3, 57, 1197, 25137]
for i in range(90):
    line = input()
    last = []
    for j, c in enumerate(line):
        if c in op:
            last.append(c)
        else:
            if last[-1] == op[cl.index(c)]:
                last = last[:-1]
            else:
                pts += p[cl.index(c)]
                break
        if j == len(line)-1:
            toopen, s = [], 0
            while len(line) > 0:
                if line[-1] in op and len(toopen) > 0 and cl[op.index(line[-1])] == toopen[-1]:
                    toopen = toopen[:-1]
                elif line[-1] in op:
                    s *= 5
                    s += list(range(1, 5))[op.index(line[-1])]
                else:
                    toopen.append(line[-1])
                line = line[:-1]
            scores.append(s)

print("1:", pts)
print("2:", sorted(scores)[int(len(scores)/2)])
