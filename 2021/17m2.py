from math import sqrt
def sx(y, s):
    return (y + .5*s*s - .5*s)/s
def sy(sx):
    return .5*sx*(sx-1)
def f(s, sx, sy):
    return -.5 * s * s - (sx + .5) * s - .5 * (sx*sx - sx) + sy

def vx(x, s):
    res = []
    till0 = (2*x+s*s-s)/2
    if s >= till0:
        res.append(till0)
    a = (s*s+s)/2
    r2 = .5 * (-1 + sqrt(8*a+8*x+1))
    

def vy(y, s):
    return -(y - (s-1)*s/2)/2
    


import re
minx,maxx,miny,maxy = [int(x) for x in re.findall(r"-?\d+", input())]

print(vx(28, 7), vy(-6, 7))

c = 0
hy = 0
for x in range(minx, maxx+1):
    for y in range(miny, maxy+1):
        for s in range(1,max(x,abs(3*y))):
            # vx = x/s + .5 *(s-1)
            # my_sx = sx(y, s)
            # my_sy = sy(my_sx)
            # vy = f(1, my_sx, my_sy)
            #assert f(0, my_sx, my_sy) == 0
            mvx = vx(x, s)
            mvy = vy(y, s)
            if int(mvy) != mvy or int(mvx) != mvx or mvx <= 0:
                continue
            hy = max(hy, (mvy*(mvy+1))/2 if mvy > 0 else 0)
            c += 1
print(c, hy)
