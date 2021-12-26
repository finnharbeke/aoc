base = [int(x) for x in input().split(',')]
for s1 in range(99):
    for s2 in range(99):
        ns = base.copy()
        i = 0   
        ns[1], ns[2] = s1, s2
        while i < len(ns):
            if ns[i] == 1:
                ns[ns[i+3]] = ns[ns[i+1]] + ns[ns[i+2]]
                i += 4
            elif ns[i] == 2:
                ns[ns[i+3]] = ns[ns[i+1]] * ns[ns[i+2]]
                i += 4
            elif ns[i] == 99:
                if s1 == 12 and s2 == 2:
                    print(f"part 1: {ns[0]}")
                if ns[0] == 19690720:
                    print(f"part 2: {s1=}, {s2=} -> {100 * s1 + s2}")
                #print(f"{s1=}, {s2=} -> {ns[0]=}")
                break