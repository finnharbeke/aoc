function diffuse(elves::Set{Tuple{Int, Int}}, n::Int)
    dirs = ['n', 's', 'w', 'e']

    for _ in range(1, n)

        proposed = Dict{Tuple{Int, Int}, Tuple{Int, Int}}()
    
        for elve in elves

            i, j = elve

            spacious = true
            for ri in range(-1, 1)
                for rj in range(-1, 1)
                    if ri == 0 && rj == 0
                        continue
                    end
                    spacious = spacious && !((i+ri, j+rj) in elves)
                end
            end
            if spacious
                proposed[elve] = (i, j)
                continue
            end

            next = (i, j)
            for dir in dirs
                free = true
                if dir == 'n'
                    base = (i-1, j)
                elseif dir == 's'
                    base = (i+1, j)
                elseif dir == 'w'
                    base = (i, j-1)
                elseif dir == 'e'
                    base = (i, j+1)
                end
                for r in range(-1, 1)

                    if dir == 'n' || dir == 's'
                        adj = base .+ (0, r)
                    else
                        adj = base .+ (r, 0)
                    end

                    free = free && !(adj in elves)
                end
                if free
                    next = base
                    break
                end
            end

            proposed[elve] = next
        end

        next_elves = Set{Tuple{Int, Int}}()
        all_proposed = values(proposed)
        for (elve, next) in proposed
            if count(isequal(next), all_proposed) > 1
                push!(next_elves, elve)
            else
                push!(next_elves, next)
            end
        end
        elves = next_elves
        dirs = append!(dirs[2:4], dirs[1:1])
    end

    return elves
end

function until_stop(elves)

    dirs = ['n', 's', 'w', 'e']

    for round_number in range(1, 100000000)

        proposed = Dict{Tuple{Int, Int}, Tuple{Int, Int}}()
    
        for elve in elves

            i, j = elve

            spacious = true
            for ri in range(-1, 1)
                for rj in range(-1, 1)
                    if ri == 0 && rj == 0
                        continue
                    end
                    spacious = spacious && !((i+ri, j+rj) in elves)
                end
            end
            if spacious
                proposed[elve] = (i, j)
                continue
            end

            next = (i, j)
            for dir in dirs
                free = true
                if dir == 'n'
                    base = (i-1, j)
                elseif dir == 's'
                    base = (i+1, j)
                elseif dir == 'w'
                    base = (i, j-1)
                elseif dir == 'e'
                    base = (i, j+1)
                end
                for r in range(-1, 1)

                    if dir == 'n' || dir == 's'
                        adj = base .+ (0, r)
                    else
                        adj = base .+ (r, 0)
                    end

                    free = free && !(adj in elves)
                end
                if free
                    next = base
                    break
                end
            end

            proposed[elve] = next
        end

        next_elves = Set{Tuple{Int, Int}}()
        all_proposed = values(proposed)
        moved_at_all = false
        for (elve, next) in proposed
            if count(isequal(next), all_proposed) > 1
                push!(next_elves, elve)
            else
                push!(next_elves, next)
                if next != elve
                    moved_at_all = true
                end
            end
        end
        elves = next_elves
        dirs = append!(dirs[2:4], dirs[1:1])
        if !moved_at_all
            return round_number
        end
    end
    return -1
end

function unstable_diffusion()

    elves = Set{Tuple{Int, Int}}()

    for (i, line) in enumerate(readlines("23.txt"))
        for (j, char) in enumerate(line)
            if char == '#'
                push!(elves, (i, j))
            end
        end
    end

    diffused_elves = diffuse(elves, 10)

    xs = [e[2] for e in diffused_elves]
    minx, maxx = minimum(xs), maximum(xs)
    ys = [e[1] for e in diffused_elves]
    miny, maxy = minimum(ys), maximum(ys)

    free_space = 0
    for i in range(miny, maxy)
        for j in range(minx, maxx)
            # print(!((i, j) in diffused_elves) ? '.' : '#')
            free_space += !((i, j) in diffused_elves)
        end
        # println()
    end

    println(free_space)
    println(until_stop(elves))
end

@time unstable_diffusion()