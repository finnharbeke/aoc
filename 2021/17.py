import re
minx,maxx,miny,maxy = [int(x) for x in re.findall(r"-?\d+", input())]
tot = 0
def calc(x,y,vx,vy,p=False):
    global tot
    my = y
    s = 1

    og_vx, og_vy = vx, vy
    while not (x > maxx or y < miny):

        tot += 1
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
    
mmy = 0
c = 0
valid = set()
for i in range(400):
    for j in range(-300, 300):
        my, x, y, s = calc(0,0,i,j)
        if my is not None:
            #print(i, j, my)
            mmy = max(my, mmy)
            c += 1
            #valid.add((x, y, s, i, j))
        
print(mmy, c)
print(tot)
#print(valid)