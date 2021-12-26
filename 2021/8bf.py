import itertools
d = ["1110111", "0010010", "1011101", "1011011", "0111010", "1101011", "1101111", "1010010", "1111111", "1111011"]
t = 0
for line in open("8.txt").readlines():
    code, r = line.split("|")
    for cables in itertools.permutations("abcdefg"):
        n = lambda x: "".join("1" if l in x else "0" for l in cables)
        if all([n(x) in d for x in code.split()]):
            t += int("".join([str(d.index(n(x))) for x in r.split()]))
            break
    break
print(t)