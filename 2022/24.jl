function blizzard_basin()

    lines = readlines("24.txt")
    h, w = length(lines), length(lines[1])
    walls = zeros(Bool, (h, w))
    blizzards = Vector{Tuple{Int, Int, Int, Int}}()

    start, goal = (0, 0), (0, 0)

    for (i, line) in enumerate(lines)
        for (j, c) in enumerate(line)
            if c == '#'
                walls[i, j] = 1
            elseif i == 1
                start = (i, j)
            elseif i == h
                goal = (i, j)
            elseif c == '<'
                push!(blizzards, (i, j, 0, -1))
            elseif c == '>'
                push!(blizzards, (i, j, 0, 1))
            elseif c == '^'
                push!(blizzards, (i, j, -1, 0))
            elseif c == 'v'
                push!(blizzards, (i, j, 1, 0))
            end
        end
    end

    q = Set{Tuple{Int, Int}}([start])

    minute = 0
    found_goal = 0
    while found_goal < 3

        stormy = zeros(Bool, (h, w))
        for (i, bliz) in enumerate(blizzards)
            next = ((bliz[1:2] .+ bliz[3:4])..., bliz[3:4]...)
            if next[1] == 1
                next = (h-1, next[2:4]...)
            elseif next[1] == h
                next = (2, next[2:4]...)
            elseif next[2] == 1
                next = (next[1], w-1, next[3:4]...)
            elseif next[2] == w
                next = (next[1], 2, next[3:4]...)
            end
            blizzards[i] = next
            
            stormy[next[1:2]...] = 1
        end

        minute += 1
        
        qq = Set{Tuple{Int, Int}}()

        prev_fg = found_goal

        for pos in q
            for dir in [(0, 0), (-1, 0), (1, 0), (0, -1), (0, 1)]

                next = pos .+ dir
                if next == goal
                    found_goal += 1
                    break
                end
                if any(next .<= 0) || any(next .> (h, w)) || walls[next...] || stormy[next...]
                    continue
                end
                push!(qq, next)

            end
            !(found_goal > prev_fg) || break
        end

        q = qq

        if found_goal > prev_fg

            println(minute)
            start, goal = goal, start
            q = Set{Tuple{Int, Int}}([start])
        end

    end

    println(minute)

end

@time blizzard_basin()