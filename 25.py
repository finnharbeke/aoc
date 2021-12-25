m = [line[:-1] for line in open("25.txt").readlines()]
moving = True
r = 0
while moving:
    #print(f"round {r}")
    #print('\n'.join(m))
    r += 1
    n = ['.' * len(m[i]) for i in range(len(m))]
    moving = False
    for i in range(len(m)):
        for j in range(len(m[i])):
            if m[i][j] == '>' and j < len(m[i])-1 and m[i][j+1] == '.':
                n[i] = n[i][:j] + '.>' + n[i][j+2:]
                moving = True
            elif m[i][j] == '>' and j == len(m[i])-1 and m[i][0] == '.':
                n[i] = '>' + n[i][1:j] + '.'
                moving = True
            
            elif m[i][j] == '>':
                n[i] = n[i][:j] + '>' + n[i][j+1:]
            elif m[i][j] == 'v':
                n[i] = n[i][:j] + 'v' + n[i][j+1:]

    m = n.copy()
    for i in range(len(m)):
        for j in range(len(m[i])):
            if m[i][j] == 'v' and i < len(m)-1 and m[i+1][j] == '.':
                n[i] = n[i][:j] + '.' + n[i][j+1:]
                n[i+1] = n[i+1][:j] + 'v' + n[i+1][j+1:]
                moving = True
            elif m[i][j] == 'v' and i == len(m)-1 and m[0][j] == '.':
                n[i] = n[i][:j] + '.' + n[i][j+1:]
                n[0] = n[0][:j] + 'v' + n[0][j+1:]
                moving = True
            elif m[i][j] == 'v':
                n[i] = n[i][:j] + 'v' + n[i][j+1:]
    
    m = n

print(r)