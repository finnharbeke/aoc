function main()
    lines = readlines("14.txt")

    solid = Set{Pair{Int64, Int64}}()
    bottom = 0
    for line in lines
        corners = split(line, " -> ")
        corners = [split(c, ",") for c in corners]
        corners = [(parse(Int64, c[1]), parse(Int64, c[2])) for c in corners]
        for i in range(1, length(corners)-1)
            x1, y1 = corners[i]
            x2, y2 = corners[i+1]
            bottom = max(y1, y2, bottom)
            if y1 == y2
                for x in range(min(x1, x2), max(x1, x2))
                    push!(solid, Pair(x, y1))
                end
            elseif x1 == x2
                for y in range(min(y1, y2), max(y1, y2))
                    push!(solid, Pair(x1, y))
                end
            else
                println("no way")
                println(line)
            end
        end
    end
    wall = length(solid)
    println(wall)

    floor = bottom + 2
    reached_bottom = false
    is_top = false
    while !is_top
        sx, sy = 500, 0
        psx, psy = 500, -1
        while sy < floor-1 && (psx != sx || psy != sy)
            psx, psy = sx, sy
            if !(Pair(sx, sy+1) in solid)
                sy += 1
            elseif !(Pair(sx-1, sy+1) in solid)
                sx -= 1
                sy += 1
            elseif !(Pair(sx+1, sy+1) in solid)
                sx += 1
                sy += 1
            end
            if sy == bottom && !reached_bottom
                reached_bottom = true
                println(length(solid) - wall)
            end
        end
        push!(solid, Pair(sx, sy))
        is_top = sx == 500 && sy == 0
    end
    println(length(solid) - wall)
end

@time main()