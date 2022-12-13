lines = readlines("12.txt")

w = length(lines[1])
h = length(lines)

S = (0, 0)
starts = []
for i in range(1, h)
    for j in range(1, w)
        if lines[i][j] == 'S'
            global S
            S = (i, j)
            push!(starts, (i, j))
        elseif lines[i][j] == 'a'
            push!(starts, (i, j))
        end
    end
end

function bfs(start)
    i, j = start
    q = [(i, j, 0)]
    visited = zeros(Bool, (h, w))
    while length(q) > 0
        i, j, n = popfirst!(q)
        if visited[i, j]
            continue
        end
        #println(length(q), ", @", i, ", " ,j, ": ", lines[i][j])
        visited[i, j] = true
        height = lines[i][j]
        if height == 'E'
            return n
            break
        elseif height == 'S'
            height = 'a'
        end
        for move in [(-1, 0), (1, 0), (0, -1), (0, 1)]
            i2, j2 = i + move[1], j + move[2]
            if i2 == 0 || i2 > h || j2 == 0 || j2 > w
                continue
            end
            if lines[i2][j2] == 'E' && height in ['y', 'z']
                pushfirst!(q, (i2, j2, n+1))
            elseif lines[i2][j2] != 'E' && !visited[i2, j2] && (lines[i2][j2] - height <= 1)
                push!(q, (i2, j2, n+1))
            end
        end
    end
    return -1
end

println(bfs(S))
shortest = h * w
while length(starts) > 0
    global shortest
    start = popfirst!(starts)
    n = bfs(start)
    if n == -1
        continue
    end
    shortest = min(n, shortest)
end

println(shortest)
