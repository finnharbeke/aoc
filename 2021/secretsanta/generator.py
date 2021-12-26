symbols = [chr(i) for i in range(ord('a'), ord('z')+1)] + ['.', ',', '?', '!', "'"]

info = False

import re
prime = lambda n: re.match(r'^1?$|^(11+?)\1+$', '1' * n) == None
primes = list(filter(prime, range(300)))[:len(symbols)]
pretty = lambda someform: '\n'.join([' '.join(str(x) for x in l) for l in someform])

if __name__ == "__main__":
    original = ""
    last = "\t"
    while last != "q":
        if len(original):
            original += "\n"
        if last != "\t":
            original += last
        last = input()

    #print(primes)

    strings = [l.split(' ') for l in original.split('\n')]
    #print(strings.__repr__())

    m = 0
    m_w = ""
    def word_num(w: str):
        global m, m_w
        arr = []
        n = 1
        #ps = []
        for i in range(len(w)):
            n *= (primes[i]) ** (symbols.index(w[i]) + 1)
            arr.append((primes[i], symbols.index(w[i]) + 1))
            #ps.append((primes[symbols.index(w[i])], i + 1))
        m = max(m, n)
        if m == n:
            m_w = w
        #print(arr, end=' ')
        return n#, ps

    nums = [[word_num(w) for w in l] for l in strings]
    #print()
    #print(pretty(nums))

    bins = [[bin(n)[2:] if n > 1 else '' for n in l] for l in nums]
    #print(pretty(bins))

    char = lambda n: str(n) if n <= 9 else chr(ord('a') - 10 + n)

    codes = []
    lgst = 0
    for li, l in enumerate(bins):
        codes.append([])
        for b in l:
            code = ""
            i = 0
            while i < len(b):
                c = b[i]
                start = i
                while i < len(b) and b[i] == c:
                    i += 1
                code += char(i - start)
                lgst = max(lgst, i-start)
            codes[li].append(code)

    print(pretty(codes)) if not info else print(m_w, m, len(str(m)), lgst, char(lgst))