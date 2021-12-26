import re
import numpy as np
nums = r"-?\d+"
n = -1
beacons = []
for line in open("19.txt").readlines():
    line = line[:-1]
    if line.startswith('--'):
        n += 1
        beacons.append(np.empty((0, 3), dtype=int))
    elif len(line):
        x, y, z = re.findall(nums, line)
        beacons[n] = np.concatenate([beacons[n], np.array([[x, y, z]], dtype=int)], axis=0)
n += 1
dims = np.array([
    np.array([1, 1, 1]),
    np.array([1, 1, -1]),
    np.array([1, -1, 1]),
    np.array([1, -1, -1]),
    np.array([-1, 1, 1]),
    np.array([-1, 1, -1]),
    np.array([-1, -1, 1]),
    np.array([-1, -1, -1])
])
rotations = [
    np.diag([1, 1, 1]), # x, y, z
    np.array([[1, 0, 0], # x, z, y
              [0, 0, 1],
              [0, 1, 0]]),
    np.array([[0, 1, 0], # y, x, z
              [1, 0, 0],
              [0, 0, 1]]),
    np.array([[0, 1, 0], # z, x, y
              [0, 0, 1],
              [1, 0, 0]]),
    np.array([[0, 0, 1], # z, y, x
              [0, 1, 0],
              [1, 0, 0]]),
    np.array([[0, 0, 1], # y, z, x
              [1, 0, 0],
              [0, 1, 0]])
]

ops = [r * d for r in rotations for d in dims]
pos = {0: np.array([0, 0, 0])}
dont = set()
while len(pos) < n:
    for t in range(n):
        for b in pos:
            if t in pos or (b, t) in dont:
                continue
            for oi, op in enumerate(ops):
                test = beacons[t].dot(op)
                uniq, counts = np.unique(
                    np.repeat(beacons[b], len(test), axis=0) - np.tile(test, (len(beacons[b]), 1)),
                    axis=0, return_counts=True
                )
                if (counts >= 12).any():
                    diff = uniq[np.where(counts >= 12)[0][0]]
                    pos[t] = diff
                    beacons[t] = test + diff
                    #print(f"scanner {t}, base {b}: op {oi}, pos = {diff}")
                    break
            if t in pos:
                break
            else:
                dont.add((b, t))

beacs = set([tuple(beac) for beac in np.concatenate(beacons)])
np.concatenate(beacons)
print(len(beacs))

m = 0
for b1 in pos.values():
    for b2 in pos.values():
        manh = np.absolute(b1 - b2).sum()
        m = max(m, manh)
print(m)
