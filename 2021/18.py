def int_char(n: int):
    return str(n) if n <= 9 else chr(ord('a') + (n - 10))
def char_int(c: str):
    return int(c) if ord(c) <= ord('9') else ord(c) - ord('a') + 10

def explode(s: str, i: int, last_n: int):
    l = s[i]
    r = s[i+2]
    s = s[:i-1] + '0' + s[i+4:]
    if last_n is not None:
        s = s[:last_n] + (int_char(char_int(s[last_n]) + char_int(l))) + s[last_n+1:]
    while i < len(s):
        if s[i] not in ['[', ']', ',']:
            s = s[:i] + (int_char(char_int(r) + char_int(s[i]))) + s[i+1:]
            break
        i += 1
    return s, i

def split(s: str, i: int):
    n = char_int(s[i])
    assert n >= 10
    l, r = int_char(int(n / 2)), int_char(int(n / 2) if n / 2 == int(n / 2) else int(n / 2) + 1)
    pair = f"[{l},{r}]"
    s = s[:i] + pair + s[i+1:]
    return s

def reduce(s: str, split_ok=False):
    last_n = None
    i = 0
    lvl = 0
    correct = True
    while i < len(s):
        if s[i] == '[':
            lvl += 1
        elif s[i] not in ['[', ']', ',']:
            if lvl >= 5:
                s, i = explode(s, i, last_n)
                return False, s, False
            elif s[i] not in [chr(ord('0') + i) for i in range(10)]:
                if not split_ok:
                    correct = False
                    last_n = i
                else:
                    s = split(s, i)
                    return False, s, False
            else:
                last_n = i
        elif s[i] == ']':
            lvl -= 1
        i += 1

    return correct, s, True

def reduced(snail: str):
    correct, split_ok = False, False
    while not correct:
        correct, snail, split_ok = reduce(snail, split_ok=split_ok)
    return snail

def snail_sum(s: str, i: int):
    assert s[i] == '['
    i += 1
    if s[i] == '[':
        l, i = snail_sum(s, i)
    else:
        l = char_int(s[i])
    i += 1
    assert s[i] == ','
    i += 1
    if s[i] == '[':
        r, i = snail_sum(s, i)
    else:
        r = char_int(s[i])
    i += 1
    assert s[i] == ']'
    return 3*l + 2*r, i

long_snail = None
snails = []
for snail in open("18.txt").readlines():
    snail = snail[:-1]
    snails.append(snail)
    long_snail = snail if long_snail is None else reduced(f"[{long_snail},{snail}]")
    
print(f"magnitude of long snail: {snail_sum(long_snail, 0)[0]}")
m = 0
for i in range(len(snails)):
    for j in range(len(snails)):
        if i != j:
            snail = reduced(f"[{snails[i]},{snails[j]}]")
            m = max(m, snail_sum(snail, 0)[0])
print(f"max magnitude of pair:{m}")