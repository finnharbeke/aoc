a=[int(input()) for _ in range(2000)]
print(sum([n>m for m,n in zip(a[:-1],a[1:])]))