function turned(circle, pos)
    return [circle[i] for (p, i) in sort([(p, i) for (i, p) in enumerate(pos)])]
end

function mix(circle, times)
    n = length(circle)
    pos = [i for i in range(0, n-1)]

    # println(circle)
    for t in range(1, times)
        for i in range(1, n)
            new_pos = mod((circle[i] % (n-1)) + pos[i], n)
            if circle[i] == 0 || new_pos == pos[i]
                # nothing
            elseif new_pos > pos[i]
                if circle[i] > 0
                    mask = (pos .> pos[i]) .&& (pos .<= new_pos)
                    pos = mod.(pos .- mask, n)
                else
                    mask = (pos .< pos[i]) .|| (pos .>= new_pos)
                    pos = mod.(pos .+ mask, n)
                end
            else
                if circle[i] > 0
                    mask = (pos .> pos[i]) .|| (pos .<= new_pos)
                    pos = mod.(pos .- mask, n)
                else
                    mask = (pos .< pos[i]) .&& (pos .>= new_pos)
                    pos = mod.(pos .+ mask, n)
                end
            end
            pos[i] = new_pos
        end
    end
    return turned(circle, pos)
end

function grove_coords(mixed)
    n = length(mixed)
    start = findfirst(mixed .== 0) - 1
    total = 0
    for i in [1000, 2000, 3000]
        p = mod(start + i, n) + 1
        total += mixed[p]
    end
    return total
end

function grove_positioning()

    circle = [parse(Int, line) for line in readlines("20.txt")]

    
    mixed = mix(circle, 1)
    println(grove_coords(mixed))
    mixed = mix(circle .* 811589153, 10)
    println(grove_coords(mixed))
    
end

@time grove_positioning()