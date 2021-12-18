import re, math
minx,maxx,miny,maxy = [int(x) for x in re.findall(r"-?\d+", input())]

get_vx = lambda x,s: [res:=x/s + .5 *(s-1), int(res) if int(res)==res else 0][1]
get_vy1 = lambda y,s: [res:=-y/s + .5 *(s-1) - 1, int(res) if int(res)==res and res > 0 and s > 2*res else 0][1]
get_vy2 = lambda y,s: [res:=y/(s+.5) - .5 *(s*s-1)/(s+.5), int(res) if int(res)==res and res < 0 else 0][1]

valid = set()

def calc(x,y,vx,vy,p=False):
    my = y
    s = 1

    og_vx, og_vy = vx, vy
    while not (x > maxx or y < miny):

        x+= vx
        y+= vy
        if vx > 0:
            vx -= 1
        elif vx < 0:
            vx += 1
        vy -= 1
        if p:
            print(x,y)
        my = max(y,my)
        if minx <= x and x <= maxx and miny <= y and y <= maxy:
            #print(f"{x=}, {y=}, {s=}, {og_vx=}, {og_vy=}, {my=}")
            return my, x, y, s
        s+= 1
        #print(vx,y,vy)
    #print("=>", x, y)
    return None, None, None, None

def qvy(y, s):
    b = -4*s -3
    a = 3
    c = s * (s+1) / 2 - y
    d = b * b - 4 * a * c
    if d < 0:
        return 0, 0
    else:
        q1, q2 = (b + math.sqrt(b*b - 4*a*c)) / (2*a), (b - math.sqrt(b*b - 4*a*c)) / (2*a)
        if abs(q1) >= s or int(q1) != q1 or q1 > 0:
            q1 = 0
        if abs(q2) >= s or int(q2) != q2 or q2 > 0:
            q2 = 0
        return q1, q2
        

c = 0
my = 0
count = {}
for x in range(minx, maxx+1):
    for y in range(miny, maxy+1):
        for s in range(1,max(abs(x), abs(3*y))):
            vx = get_vx(x,s)
            vy1 = get_vy1(y,s)
            vy2 = get_vy2(y,s)
            hy1 = int((vy1*vy1+vy1)/2) if vy1 > 0 else 0
            hy2 = int((vy2*vy2+vy2)/2) if vy2 > 0 else 0
            my = max(my, hy1, hy2)
            c += int(vx > 0 and vy1 != 0) + int(vx > 0 and vy2 != 0)
            if vx > 0 and vy1 != 0:
                valid.add((x, y, s, vx, vy1))
            if vx > 0 and vy2 != 0:
                valid.add((x, y, s, vx, vy2))
            count[x] = count.get(x, 0) + int(vx > 0 and vy1 != 0) + int(vx > 0 and vy2 != 0)
            if int(vx > 0 and vy1 != 0) + int(vx > 0 and vy2 != 0) > 0:
                chy1, cx1, cy1, s1 = calc(0, 0, vx, vy1)
                chy2, cx2, cy2, s2 = calc(0, 0, vx, vy2)
                if (cx1 != x or cy1 != y or s1 != s) and (cx2 != x or cy2 != y or s2 != s):
                    print(f"{x=}, {y=}, {s=}, {vx=}, {vy1=}, {hy1=}, {vy2=}, {hy2=}")
                    print(f"{cx1=}, {cy1=}, {s1=}")
                    print(f"{cx2=}, {cy2=}, {s2=}")
                # if cx2 != x or cy2 != y or s2 != s:
                #     print(f"{x=}, {y=}, {s=}, {vx=}, {vy1=}, {hy1=}, {vy2=}, {hy2=}")
                #     print(f"{cx2=}, {cy2=}, {s2=}")
                pass

            # vy2, hy = get_vyp(y,s)
            
            # q1, q2 = qvy(y, s)
            # if vx == 0 or (int(q1) == q1 or int(q2) == q2) and q1 + q2 != 0:
            #     print(f"{x=}, {y=}, {s=}, {vx=}, {q1=}, {q2=}")

print(c, my)
# print(count)
# print(valid)
