function bin_search(arr::Vector{NTuple{8, Int}}, query::NTuple{8, Int}, a::Integer, b::Integer)
    if b - a <= 1
        if query < arr[a] return a
        elseif query < arr[b] return b
        else return b + 1
        end
    end
    mid = (a + b) ÷ 2
    if query < arr[mid]
        return bin_search(arr, query, a, mid - 1)
    else
        return bin_search(arr, query, mid, b)
    end
end

function new_state!(states::Vector{NTuple{8, Int}}, state::NTuple{8, Int})
    for i in range(length(states), 1, step=-1)
        if all(states[i] .<= state)
            popat!(states, i)
        end
    end
    ix = length(states) == 0 ? 1 : bin_search(states, state, 1, length(states))
    insert!(states, ix, state)
end

function most_geodes(n_ingr::Vector{Vector{Int}}, total_time::Integer)
    maxes = [maximum([rec[1] for rec in n_ingr]), n_ingr[3][2], n_ingr[4][2], 1000]
    states = [(1, 0, 0, 0, 0, 0, 0, 0)]
    recipes = [
        (n_ingr[1][1], 0, 0, 0),
        (n_ingr[2][1], 0, 0, 0),
        (n_ingr[3][1], n_ingr[3][2], 0, 0),
        (n_ingr[4][1], 0, n_ingr[4][2], 0),
    ]


    for t in range(1, total_time)
        nexts = NTuple{8, Int}[]
        for state in states
            for (i, recipe) in enumerate(recipes)
                affordable = all(recipe .<= state[5:8])
                if affordable && state[i] < maxes[i]
                    mins = state[1:4] .- recipe
                    change = (Int(i == 1), Int(i == 2), Int(i == 3), Int(i == 4), mins[1], mins[2], mins[3], mins[4])
                    new_state!(nexts, state .+ change)
                end
            end
            new_state!(nexts, state .+ (0, 0, 0, 0, state[1], state[2], state[3], state[4]))
        end
        states = nexts
        # println(states)
    end
    return maximum([state[8] for state in states])
end

function not_enough_minerals()

    lines = readlines("19.txt")

    total = 0
    total2 = 1

    for (id, line) in enumerate(lines)
        line = split(line, ":")[2]
        recipes = split(line, ". ")

        n_ingr = [[parse(Int, rec[ixs]) for ixs in findall(r"\d+", rec)] for rec in recipes]
        # println(n_ingr)

        geodes = most_geodes(n_ingr, 24)
        total += id * geodes
        println("$id: $geodes")
        if id <= 3
            geodes = most_geodes(n_ingr, 32)
            total2 *= geodes
        end

    end

    println(total)
    println(total2)
end

@time not_enough_minerals()