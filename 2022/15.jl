function merge_intervals(inters)
    back_ix = length(inters)
    while back_ix > 1
        left, right = popat!(inters, back_ix)
        merged = false
        for i in range(1, length(inters))
            fleft, fright = inters[i]
            if left - fright > 1 || fleft - right > 1
                # not touching
                continue
            else
                inters[i] = (min(fleft, left), max(fright, right))
                merged = true
                break
            end
        end
        if !merged
            push!(inters, (left, right))
        end
        back_ix -= 1
    end
    return inters
end

function main()
    lines = readlines("15.txt")

    y = 2e6
    # y = 10
    bound = 4000000
    # bound = 20

    not_poss = Set{Int64}()
    beacons = Set{Int64}()
    sensors = Set{Int64}()

    all_inters = [[] for i in range(0, bound)]

    for line in lines
        sx, sy, bx, by = findall(r"-?\d+", line)
        sx, sy, bx, by = [parse(Int64, line[coord]) for coord in [sx, sy, bx, by]]
        if sy == y
            push!(sensors, sx)
        end
        if by == y
            push!(beacons, bx)
        end
        man_dist = abs(sx - bx) + abs(sy - by)
        vert_dist = abs(sy - y)

        # println(man_dist, " ", vert_dist)

        for i in range(0, man_dist - vert_dist)
            push!(not_poss, sx + i)
            push!(not_poss, sx - i)
        end

        for i in range(0, man_dist)
            if sy + i >= 0 && sy + i <= bound
                push!(all_inters[sy+i+1], (max(0, sx - (man_dist - i)), min(bound, sx + (man_dist - i))))
                all_inters[sy+i+1] = merge_intervals(all_inters[sy+i+1])
            end
            if sy - i >= 0 && sy - i <= bound
                push!(all_inters[sy-i+1], (max(0, sx - (man_dist - i)), min(bound, sx + (man_dist - i))))
                all_inters[sy-i+1] = merge_intervals(all_inters[sy-i+1])
            end
        end
    end
    println(length(setdiff(not_poss, union(beacons, sensors))))
    
    for (i, inters) in enumerate(all_inters)
        if length(inters) > 1
            println(i - 1, " ", inters)
            if inters[1][1] == 0
                x = inters[1][2] + 1
            else
                x = inters[2][2] + 1
            end
            y = i - 1
            println(x * 4000000 + y)
        end
    end
end

@time main()