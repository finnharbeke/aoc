function pyroclastic_flow()

    jets = readline("17.txt")

    rocks = [
        ["####"],
        [" # ", "###", " # "],
        ["  #", "  #", "###"],
        ["#", "#", "#", "#"],
        ["##", "##"]
    ]

    n = 0
    r = 1
    j = 1

    tower = ["wwwwwwwww"]
    empty = "w       w"

    cut_off = 0

    println(length(jets), ", ", length(rocks))

    hist = Dict{Tuple{Int, Int}, Vector{Int}}()
    ns = Dict{Tuple{Int, Int}, Vector{Int}}()

    top = 1000000000000
    did_jump = false
    while (n < top)

        if !did_jump
            if (r, j) in keys(hist)
                push!(hist[(r, j)], cut_off + length(tower) - 1 - sum(hist[(r, j)]))
                push!(ns[(r, j)], n - sum(ns[(r, j)]))
                if hist[(r, j)][length(hist[(r, j)])] == hist[(r, j)][length(hist[(r, j)]) - 1] && (
                    ns[(r, j)][length(ns[(r, j)])] == ns[(r, j)][length(ns[(r, j)]) - 1]
                )
                    inc = hist[(r, j)][length(hist[(r, j)])]
                    jump = ns[(r, j)][length(ns[(r, j)])]
                    n_jumps = ((top - n) ÷ jump)
                    cut_off += n_jumps * inc
                    n += n_jumps * jump
                    did_jump = true
                end
            else
                hist[(r, j)] = [cut_off + length(tower) - 1]
                ns[(r, j)] = [n]
            end
        end

        push!(tower, empty)
        push!(tower, empty)
        push!(tower, empty)

        coords = Pair{Int, Int}[]
        for part in reverse(rocks[r])
            ats = "w  " * part * repeat(" ", 5 - length(part)) * "w"
            push!(tower, empty)
            for i in range(1, 9)
                if ats[i] == '#'
                    push!(coords, Pair(i, length(tower)))
                end
            end
        end
        # println(coords)

        while true
            move = jets[j] == '<' ? -1 : 1
            can_move = true
            for (x, y) in coords
                if tower[y][x+move] != ' '
                    can_move = false
                    break
                end
            end
            if (can_move)
                # can be pushed
                for i in range(1, length(coords))
                    x, y = coords[i]
                    coords[i] = Pair(x + move, y)
                end
            end
            j = j % length(jets) + 1

            can_fall = true

            for (x, y) in coords
                if tower[y-1][x] != ' '
                    can_fall = false
                    break
                end
            end

            can_fall || break

            for i in range(1, length(coords))
                x, y = coords[i]
                coords[i] = Pair(x, y - 1)
            end
        end

        for (x, y) in coords
            tower[y] = tower[y][1:x-1] * "#" * tower[y][x+1:length(tower[y])]
        end

        while tower[length(tower)] == "w       w"
            pop!(tower)
        end


        # println("$r, $j, $n")
        # println()
        # println(join(reverse(tower), "\n"))
        r = r % length(rocks) + 1
        n += 1

        max_len = 500
        if (length(tower) > max_len)
            cut_off += length(tower) - max_len
            tower = tower[length(tower)-(max_len-1):length(tower)]
        end

        if (n == 2022)
            println(cut_off + length(tower) - 1)
        end
    end

    println(cut_off + length(tower) - 1)


    display(hist)
    display(ns)
end

@time pyroclastic_flow()