function lessthan(cube::Tuple{Int, Int, Int}, other::Tuple{Int, Int, Int})
    for i in range(1, length(cube))
        if cube[i] == other[i]
            continue
        else
            return cube[i] < other[i]
        end
    end
    return false
end

function binary_search(cubes::Vector{Tuple{Int, Int, Int}}, query::Tuple{Int, Int, Int}, a::Int, b::Int)
    if b - a <= 1
        return cubes[a] == query || cubes[b] == query
    end
    mid = (a + b) ÷ 2
    if lessthan(query, cubes[mid])
        return binary_search(cubes, query, a, mid-1)
    else
        return binary_search(cubes, query, mid, b)
    end
end

function enclosed(cubes::Vector{Tuple{Int, Int, Int}}, start::Tuple{Int, Int, Int}, out::Set{Tuple{Int, Int, Int}})

    visited = Set{Tuple{Int, Int, Int}}()
    q = Vector{Tuple{Int, Int, Int}}()

    push!(q, start)

    while length(q) > 0
        x, y, z = popfirst!(q)
        if (x, y, z) in out || x < 0 || x >= 20 || y < 0 || y >= 20 || z < 0 || z >= 20
            return false, visited
        end
        push!(visited, (x, y, z))
        for i in range(-1, 1)
            for j in range(-1, 1)
                for k in range(-1, 1)
                    if sum(abs(i) + abs(j) + abs(k)) != 1
                        continue
                    end
                    next = (x + i, y + j, z + k)
                    if !(next in visited) && !binary_search(cubes, next, 1, length(cubes))
                        # air
                        push!(q, next)
                    end
                end
            end
        end
    end
    return true, visited

end

function boiling_boulders()
    lines = readlines("18.txt")
    cubes = Vector{Tuple{Int, Int, Int}}()
    for line in lines
        x, y, z = [parse(Int, x) for x in split(line, ",")]
        push!(cubes, (x, y, z))
    end

    sort!(cubes, lt=lessthan)

    surface = 0
    outer_surface = 0

    all_enc = Set{Tuple{Int, Int, Int}}()
    all_out = Set{Tuple{Int, Int, Int}}()

    for cube in cubes
        x, y, z = cube

        for i in range(-1, 1)
            for j in range(-1, 1)
                for k in range(-1, 1)
                    if sum(abs(i) + abs(j) + abs(k)) != 1
                        continue
                    end
                    neighbor = (x + i, y + j, z + k)
                    if !binary_search(cubes, neighbor, 1, length(cubes))
                        surface += 1
                        if neighbor in all_enc
                            continue
                        end
                        enc, vis = enclosed(cubes, neighbor, all_out)
                        if !enc
                            outer_surface += 1
                            all_out = union(all_out, vis)
                        else
                            all_enc = union(all_enc, vis)
                        end
                    end
                end
            end
        end
    end

    println(surface)
    println(outer_surface)

end

@time boiling_boulders()