from generator import primes, symbols, pretty

codes = []
for line in open(input()).readlines():
    codes.append(line.split())

num = lambda c: int(c) if ord(c) <= ord('9') else ord(c) - ord('a') + 10

bins = []
for li, line in enumerate(codes):
    bins.append([])
    for code in line:
        b = ""
        for i in range(len(code)):
            b += ('1' if i % 2 == 0 else '0') * num(code[i])
        bins[li].append(b)

nums = [[int(b, 2) for b in l] for l in bins]

def factorize(n: int):
    fs = []
    for p in primes:
        c = 0
        while n % p == 0:
            n //= p
            c += 1
        if c > 0:
            fs.append((p, c))
    return fs

def word(n: int):
    factors = factorize(n)
    factors.sort(key=lambda t: t[0])
    #print(factors)
    w = ""
    for i, f in factors:
        w += symbols[f-1]
    return w

#print(primes)
words = [[word(n) for n in l] for l in nums]
print(pretty(words))