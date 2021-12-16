from math import prod
out = False
v_sum = 0
def parse(bs, p):
    global v_sum
    v = int(bs[:3], 2)
    v_sum += v
    id_ = int(bs[3:6], 2)
    print(f"{' '*(len(p))}v{v},id{id_}") if out else None
    if id_ == 4:
        s = ""
        i = 6
        while i < len(bs):
            s += bs[i+1:i+5]
            i += 5
            if bs[i-5] == "0":
                break
        n = int(s, 2)
        print(f"{' '*(len(p))}> {n}") if out else None
        return n, i
    else:
        c_ns = []
        lti = bs[6]
        length, number = 0, 0
        if lti == "0":
            length = int(bs[7:22], 2)
            i = 22
            _ = 1
            while i < length + 22:
                n, j = parse(bs[i:], p + "c" + str(_))
                _ += 1
                c_ns.append(n)
                i += j
        elif lti == "1":
            number = int(bs[7:18], 2)
            i = 18
            for _ in range(number):
                n, j = parse(bs[i:], p+"c"+str(_+1))
                c_ns.append(n)
                i += j
        ops = [
            sum, prod, min, max, 0,
            lambda l: int(l[0]>l[1]),
            lambda l: int(l[0]<l[1]),
            lambda l: int(l[0]==l[1])
        ]
        print(f"{' '*(len(p))}> {ops[id_](c_ns)}") if out else None
        return ops[id_](c_ns), i

for l in open("16.txt").readlines():
    l = l[:-1]
    bi = f"{bin(int(l, 16))[2:]:0>{4*len(l)}}"
    print(parse(bi, "r")[0])

print(v_sum)