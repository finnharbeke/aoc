function merge_intervals!(inters::Vector{Pair{Int64, Int64}})
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
                inters[i] = Pair(min(fleft, left), max(fright, right))
                merged = true
                break
            end
        end
        if !merged
            push!(inters, Pair(left, right))
        end
        back_ix -= 1
    end
    sort!(inters)
end

function main()
    lines = readlines("15.txt")

    y = 2e6
    y = 10
    bound = 4000000
    bound = 20
    n_beacons_on_y = 0
    n = length(lines)
    y_inters = Vector{Pair{Int64, Int64}}()
    top_left_ys  = Array{Union{Nothing, Int64}, 1}(nothing, n)
    top_right_ys = Array{Union{Nothing, Int64}, 1}(nothing, n)
    bot_left_ys  = Array{Union{Nothing, Int64}, 1}(nothing, n)
    bot_right_ys = Array{Union{Nothing, Int64}, 1}(nothing, n)

    up_pairs = Vector{Int64}()
    down_pairs = Vector{Int64}()

    for (i, line) in enumerate(lines)
        sx, sy, bx, by = [parse(Int64, line[coord]) for coord in findall(r"-?\d+", line)]
        if by == y
            n_beacons_on_y += 1
        end
        # part 1
        man_dist = abs(sx - bx) + abs(sy - by)
        range = max(0, man_dist - abs(sy - y))
        # println(man_dist, " ", vert_dist)
        push!(y_inters, Pair(sx - range, sx + range))
        merge_intervals!(y_inters)

        # part 2
        # y intercept of boundaries that are outside of man_dist
        top_left_ys[i]  = sy + (man_dist + 1) - sx
        top_right_ys[i] = sy + (man_dist + 1) + sx
        bot_left_ys[i]  = sy - (man_dist + 1) + sx
        bot_right_ys[i] = sy - (man_dist + 1) - sx

        if top_left_ys[i] in bot_right_ys[i]
            push!(up_pairs, top_left_ys[i])
        end
        if top_right_ys[i] in bot_left_ys[i]
            push!(down_pairs, top_right_ys[i])
        end
        if bot_left_ys[i] in top_right_ys[i]
            push!(down_pairs, bot_left_ys[i])
        end
        if bot_right_ys[i] in top_left_ys[i]
            push!(up_pairs, bot_right_ys[i])
        end
    end
    println(y_inters)
    left, right = y_inters[1]
    println(right - left + 1 - n_beacons_on_y)

    println(up_pairs)
    println(down_pairs)
    println(top_left_ys)
    println(top_right_ys)
    println(bot_left_ys)
    println(bot_right_ys)
    println(intersect(top_left_ys, top_right_ys, bot_left_ys, bot_right_ys))
end

@time main()