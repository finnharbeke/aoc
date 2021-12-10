pts = 0
scores = []
for i in range(90):
    line = input()
    last = []
    for j, c in enumerate(line):
        if c in ['(', '[', '{', '<']:
            last.append(c)
        elif c == ')':
            if last[-1] == '(':
                last = last[:-1]
            else:
                # illegal )
                pts += 3
                break
        elif c == ']':
            if last[-1] == '[':
                last = last[:-1]
            else:
                # illegal ]
                pts += 57
                break
        elif c == '}':
            if last[-1] == '{':
                last = last[:-1]
            else:
                # illegal }
                pts += 1197
                break
        elif c == '>':
            if last[-1] == '<':
                last = last[:-1]
            else:
                # illegal >
                pts += 25137
                break
        if j == len(line)-1:
            #incomplete
            s = 0
            t = ""
            toopen = []
            while len(line) > 0:
                if line[-1] in ['(', '[', '{', '<'] and len(toopen) > 0 and [')', ']', '}', '>'][['(', '[', '{', '<'].index(line[-1])] == toopen[-1]:
                    toopen = toopen[:-1]
                elif line[-1] == '(':
                    s *= 5
                    s += 1
                    t += ')'
                elif line[-1] == '[':
                    s *= 5
                    s += 2
                    t += ']'
                elif line[-1] == '{':
                    s *= 5
                    s += 3
                    t += '}'
                elif line[-1] == '<':
                    s *= 5
                    s += 4
                    t += '>'
                else:
                    toopen.append(line[-1])
                line = line[:-1]
            scores.append(s)
            print(t, s)

print(pts)
print(sorted(scores)[int(len(scores)/2)])
