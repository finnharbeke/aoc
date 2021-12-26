edges = dict()
for line in iter(input, ""):
    u, v = line.split("-")
    edges[u] = edges.get(u, []) + [v]
    edges[v] = edges.get(v, []) + [u]

used = dict()
def dfs(node, used: list, twice):
    if node == 'end':
        return 1
    t = 0
    used.append(node)
    for neigh in edges.get(node, []):
        if neigh == 'start' or twice and neigh in used and neigh.upper() != neigh:
            continue
        else:
            if neigh in used and neigh.upper() != neigh:
                t += dfs(neigh, used.copy(), True)
            else:
                t += dfs(neigh, used.copy(), twice)
    return t

print(dfs("start", [], False))