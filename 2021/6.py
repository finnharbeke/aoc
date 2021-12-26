fish = [int(x) for x in input().split(",")]
nWithTime = [0] * 9
for x in fish:
    nWithTime[x] += 1
for t in range(256):
    nWithTime = nWithTime[1:] + [nWithTime[0]]
    nWithTime[6] += nWithTime[8]
    #print(nWithTime)
print(sum(nWithTime))


#print(fish)
#print(len(fish))