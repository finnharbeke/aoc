lines = readlines("6.txt")
# lines = readlines("6t.txt")

parents = Dict{String, String}()
neighbors = Dict{String, Vector{String}}()

for line in lines
    par, chi = split(line, ')')
    parents[chi] = par
    if par in keys(neighbors)
        push!(neighbors[par], chi)
    else
        neighbors[par] = [chi]
    end
    if chi in keys(neighbors)
        push!(neighbors[chi], par)
    else
        neighbors[chi] = [par]
    end
    # break
end

n_orbits = 0
for (k, v) in parents
    global n_orbits
    par = v
    while par != "COM"
        n_orbits += 1
        par = parents[par]
    end
    n_orbits += 1
end

# println(parents)
println(n_orbits)

# println(neighbors)

q = [("YOU", 0)]
visited = Set{String}()
santa_d = -1
while length(q) > 0
    global santa_d
    planet, d = popfirst!(q)
    push!(visited, planet)
    for neigh in neighbors[planet]
        if neigh == "SAN"
            santa_d = d-1
            break
        elseif !(neigh in visited)
            push!(q, (neigh, d+1))
        end
    end
    if santa_d >= 0
        break
    end
end
println(santa_d)