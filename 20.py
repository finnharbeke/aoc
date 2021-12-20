algo = None
img = []
default = '.'
def enhance(img):
    global default
    new = []
    for i in range(len(img)):
        new.append("")
        for j in range(len(img[i])):
            b = ""
            for ni, nj in [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1, 1)]:
                if i+ni >= 0 and i+ni < len(img) and j+nj >= 0 and j+nj < len(img[i]):
                    b += "1" if img[i+ni][j+nj] == '#' else "0"
                else:
                    b += "1" if default == '#' else "0"
            ix = int(b, 2)
            new[i] += algo[ix]
    default = algo[0] if default == '.' else algo[-1]
    return new



for line in open("20.txt").readlines():
    line = line[:-1]
    if algo is None:
        algo = line
    elif len(line):
        if not len(img):
            img = ["."*(len(line)+150)]*75
        img.append("." * 75 + line + "." * 75)

img += ["."*(len(line)+150)]*75


for i in range(50):
    #print('\n'.join(img))
    img = enhance(img)
    #print('\n'.join(img).count("#"))


print('\n'.join(img))