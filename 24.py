#for r, num in enumerate(range(99999999999999, 11111111111110, -1)):

num = input()   
ndec = 7
decisions = [f"{bin(n)[2:]:0>{ndec}}" for n in range(2**ndec)]
# from random import shuffle
# shuffle(decisions)
for dec in decisions:
    d = {'w': 0, 'x': 0, 'y': 0, 'z': 0}
    i = 0
    g = d.copy() # general
    eqi = 0
    c = 0
    constraints = []
    for li, line in enumerate(open("24.txt").readlines()):
        line = line[:-1]
        ls = line.split()
        sa, sb = ls[1], ls[2] if len(ls) > 2 else ''
        a = d[sa]
        ga = g[sa]
        if len(ls) > 2:
            b = d[sb] if sb in d.keys() else int(sb)
            gb = g[sb] if sb in g.keys() else int(sb)
        #print(line)
        if line.startswith('inp'):
            g[sa] = f"i{i+1}"
            d[sa] = int(num[i])
            i += 1
        elif line.startswith('add'):
            if ga == 0:
                g[sa] = gb
            if gb != 0:
                if type(ga) is str:
                    g[sa] = f"({ga} + {gb})" if gb != 0 else ga
                elif type(gb) is str:
                    g[sa] = f"({gb} + {ga})" if ga != 0 else gb
                else:
                    g[sa] = ga + gb
            
            d[sa] = a + b
        elif line.startswith('mul'):
            d[sa] = a * b
            if gb == 0:
                g[sa] = 0
            elif gb == 1 or ga == 1:
                pass
            elif b != 0:
                if type(ga) is str:
                    g[sa] = f"({ga} * {gb})"
                elif type(gb) is str:
                    g[sa] = f"({gb} * {ga})" 
                else:
                    g[sa] = ga * gb
        elif line.startswith('div'):
            d[sa] = a // b
            if b != 1:
                if type(ga) is str:
                    #g[sa] = f"({ga} / {gb})"
                    # print(f"{ga} // {gb}, replace by?")
                    if '* 26' in ga:
                        end = len(ga)-ga[::-1].index('62 * ')-5
                        open_ = ga[end:].count("(")
                        close = ga[end:].count(")")
                        g[sa] = ga[close-open_:end]
                    else:
                        g[sa] = 0
                    
                    # print(ga)
                    # print(g[sa])
                    # g[sa] = input()
                else:
                    g[sa] = ga // gb
        elif line.startswith('mod'):
            d[sa] = a % b
            if type(ga) is int:
                g[sa] = ga % gb
            else:
                # print(ga)
                if '* 26' in ga:
                    g[sa] = ga[len(ga) - ga[::-1].index(' + )62 *'):-1 if '* 26' in ga else len(ga)]
                else:
                    g[sa] = ga
                # print(g[sa])
                # print(g[sa])
                #g[sa] = input()
        elif line.startswith('eql'):
            istrs = [f"i{j}" for j in range(14, 0, -1)]
            def mg(gc, dig):
                if type(gc) is str:
                    gmc = gc
                    for istr in istrs:
                        gmc = gmc.replace(istr, dig)
                    return eval(gmc)
                else:
                    return gc
            # print(ga, gb)
            if type(ga) is int and type(gb) is int:
                g[sa] = int(ga == gb)
            elif mg(ga, "9") < mg(gb, "0") or mg(gb, "9") < mg(ga, "0"):
                g[sa] = 0
                #print(ga, gb)
                c += 1
            else:
                g[sa] = int(dec[eqi])
                constraints.append(f"{ga} {'==' if int(dec[eqi]) else '!='} {gb}")
                eqi += 1
                #print(li, sa, sb, ga, gb, '\n', g)
                # if eqi <= 2:
                #     print(sa, g)
                #     if eqi == 2:
                #         break
            
            d[sa] = int(a == b)
        
        # if li == 64:
        #     print(g)
        #     break

        #print(g)
    print(g['z'])
    # print(c, eqi)
    if g['z'] == 0:
        print(dec)
        print('\n'.join(constraints))
        def calc(order):
            res = [-1] * 14
            for i in range(1, 15):
                for c in constraints:
                    left = c.split('=')[0][:-1]
                    if f'i{i}' in left:
                        for v in order:
                            right = eval(left.replace(f'i{i}', str(v)))
                            if right >= 1 and right <= 9:
                                res[i-1] = v
                                i2 = int(c.split()[-1][1:])
                                res[i2-1] = right
                                break
                        break
            return int(''.join(str(n) for n in res))
        
        print(calc(range(9, 0, -1)))
        print(calc(range(1, 10)))
        print(d['z'])
        exit()

    # if decisions.index(dec) == 3:

    #     exit()

# if not r % 100000:
# #     print(r)

# # if d['z'] == 0:
# #     print(f"{num=}: z == 0 is {d['z'] == 0}, d: {d}")

# #s = "z"
# d = {}
# d['z'] = 0
# i = 14
# lines = [l[:-1] for l in open("24.txt").readlines()]
# v = 'wxyz'

# M = 1000000

# def rec(d: dict, i: int, li: int):
#     if li < 0:
#         return [d]
#     line = lines[li]
#     ls = line.split()
#     a = ls[1]
#     b = ls[2] if len(ls) > 2 else '0'
#     if line.startswith('inp'):
#         i -= 1
#         #s = s.replace(ls[1], f"inp{i}")
#         d[f"i{i}"] = d[a]
#         return rec(d, i, li-1)
#     elif line.startswith('add'):
#         #s = s.replace(ls[1], f"({a} + {b})")
#         if b not in v:
#             d[a] = d[a] - int(b)
#             return rec(d, i, li-1)
#         else:
#             if b in d:
#                 d[a] = d[a] - d[b]
#                 return rec(d, i, li-1)
#             ret = []
#             if d[a] == 0:
#                 d[b] = 0
#                 return rec(d, i, li-1)
#             for p in range(-M, M+1):
#                 d1 = d.copy()
#                 d1[a] = p
#                 d1[b] = d[a] - p
#                 ret += rec(d1, i, li-1)
#             return ret
#             print(d, 'add', a, b)
#             exit()
#             #for p in range(d[a] + 1):

#     elif line.startswith('mul'):
#         #s = s.replace(ls[1], f"({a} * {b})")
#         aos, bos = [], []
#         if b not in v:
#             if d[a] % int(b) == 0:
#                 d[a] = d[a] // int(b)
#                 return rec(d, i, li-1)
#             else:
#                 return []
#         if b in d:
#             d[a] = d[a] // d[b]
#             return rec(d, i, li-1)
#         else:
#             for 
#         for p in range((max(opts[a])+1) // 2):
#             if ao % p == 0 and (b not in opts or ao // p in opts[b]):
#                 aos.append(p)
#                 bos.append(ao // p)


#     elif line.startswith('div'):
#         #s = s.replace(ls[1], f"({a} // {b})")
#         print(d, 'div', a, b)
#         exit()
#         aos, bos = [], []
#         if b not in d.keys():
#             for res in range(d[a] * int(b), d[a] * (int(b)+1)):
#                 aos.append(res)
#                 continue
#         m = 10000000 if b not in opts[b] else max(opts[b])

#         for p in range((max(opts[a])+1) // 2):
#             if ao % p == 0 and (b not in opts or ao - p in opts[b]):
#                 aos.append(p)
#                 bos.append(ao // p)

#     elif line.startswith('mod'):
#         # s = s.replace(ls[1], f"({a} % {b})")
#         print(d, 'mod', a, b)
#         exit()
#     elif line.startswith('eql'):
#         if d[a] not in [0, 1]:
#             return []
#         if d[a] == 0:
#             d.pop(a)
#             return rec(d, i, li-1)
#         else:
#             d[a] = d[b]
#             return rec(d, i, li-1)
#         # s = s.replace(ls[1], f"({a} == {b})")

# # print(s)

# print(rec(d, 14, len(lines)-1))