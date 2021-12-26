import itertools

with open("input19.txt", "r") as f:
    n = 140
    rules = [''] * n
    ruling = True
    while ruling:
        line = str(f.readline()).replace(":", "").strip()
        print(line)
        if line == "":
            ruling = True
            break
        if line.count("|") == 0 and line.count('"') == 0:
            rule = line.split()[0]
            ris = line.split()[1:]
            rule, ris = int(rule), [int(x) for x in ris]
            rules[rule] = (False, ris)
        elif (line.count('"')):
            rule, char = line.replace('"', "").split()
            rule = int(rule)
            rules[rule] = char
        else:
            rule = line.replace("|", "").split()[0]
            ris = line.replace("|", "").split()[1:]
            rule, ris = int(rule), [int(x) for x in ris]
            rules[rule] = (True, ris)
    
    #print(rules)
    def poss(i):
        print(i)
        
        if possibilities[i] != [-1]:
            # if i==42:
            #     print(possibilities[i])
            return possibilities[i]

        possibilities[i] = []
        if i == 8:
            # for i2 in range(1, 23):
            #     for comb in itertools.combinations_with_replacement(poss(42), i2):
            #         for c2 in itertools.permutations(comb):
            #             possibilities[i].append(i2 * ''.join(c2))
                #possibilities[i] += [i * s for s in poss(42)]
            #print("8: ", possibilities[i])
            return possibilities[i]

        if i == 11:
            print("haaaloo")
            # for i2 in range(1, 12):
            #     for comb in itertools.combinations_with_replacement(poss(42), i2):
            #         for c2 in itertools.permutations(comb):
            #             for comb31 in itertools.combinations_with_replacement(poss(42), i2):
            #                 for c231 in itertools.permutations(comb31):
            #                     possibilities[i].append(i2 * ''.join(c2) + i2 * ''.join(c231))
                        
            #print(possibilities[i])
            return possibilities[i]

        rule = rules[i]
        if isinstance(rule, str):
            possibilities[i].append(rule)
            return possibilities[i]
        elif not rule[0]:
            if len(rule[1]) == 1:
                possibilities[i] = poss(rule[1][0])
                return possibilities[i]
            else:
                poss1 = poss(rule[1][0])
                poss2 = poss(rule[1][1])
                #print(rule[1])
                for a in poss1:
                    for b in poss2:
                        #print(a, b)
                        possibilities[i].append(a+b)
                
                return possibilities[i]
                        
        else:
            if len(rule[1]) == 2:
                possibilities[i] = poss(rule[1][0]) + poss(rule[1][1])
                return possibilities[i]

            else:
                poss1 = poss(rule[1][0])
                poss2 = poss(rule[1][1])
                for a in poss1:
                    for b in poss2:
                        possibilities[i].append(a+b)
                
                poss3 = poss(rule[1][2])
                poss4 = poss(rule[1][3])
                for a in poss3:
                    for b in poss4:
                        possibilities[i].append(a+b)

                return possibilities[i]

    possibilities = [[-1]] * n
    for i in range(n):
        if possibilities[i] == [-1]:
            poss(i)

    #print(rules)
    print([len(x) for x in possibilities])
    print(possibilities[42])
    print(possibilities[31])

    def check8(s):
        if s == "":
            return True
        for a in possibilities[42]:
            if s.startswith(a) and (len(a) == s or check8(s[len(a):])):
                return True
        return False
    
    def check11(s):
        for a in possibilities[42]:
            for b in possibilities[31]:
                if s.startswith(a) and s.endswith(b) and (len(a) + len(b) == len(s) or check11(s[len(a):len(s)-len(b)])):
                    return True
            return False

    def check811(s, n42, n31):
        if s == "":
            return n31 < n42 and n31 > 0
        if n31 == 0:
            for a in possibilities[42]:
                if s.startswith(a) and check811(s[len(a):], n42+1, n31):
                    print(f"{s}: True, n42: {n42}, n31: {n31}")
                    return True
        if n31 < n42 - 1:
            for b in possibilities[31]:
                if s.startswith(b) and check811(s[len(b):], n42, n31+1):
                    print(f"{s}: True, n42: {n42}, n31: {n31}")
                    return True
        print(f"{s}: False, n42: {n42}, n31: {n31}")
        return False

    total = 0
    while True:
        line = f.readline().strip()
        print(line)
        if line == "":
            break
        # if line in list(possibilities[0]):
        #     total += 1
        # for i in range(len(line)+1):
        #     c8 = check8(line[:i])
        #     c11 = check11(line[i:])
        if check811(line, 0, 0):
            total += 1
            print("true")
            # break
    
    print(total)