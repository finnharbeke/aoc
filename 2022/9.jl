lines = readlines("9.txt")

head = [0, 0]
tail = [0, 0]

visited = Set([Pair(tail[1], tail[2])])


using LinearAlgebra

for line in lines
    dir, n = split(line)
    n = parse(Int64, n)

    for i in range(1, n)
        if dir == "U"
            head[2] += 1
        elseif dir == "D"
            head[2] -= 1
        elseif dir == "R"
            head[1] += 1
        elseif dir == "L"
            head[1] -= 1
        end

        diff = head - tail
        if norm(diff) < 1.42
            continue
        elseif diff[2] == 0
            tail[1] += diff[1] ÷ abs(diff[1])
        elseif diff[1] == 0
            tail[2] += diff[2] ÷ abs(diff[2])
        else
            tail[1] += diff[1] ÷ abs(diff[1])
            tail[2] += diff[2] ÷ abs(diff[2])
        end
        # println(tail)
        push!(visited, Pair(tail[1], tail[2]))

    end
end

println(length(visited))

snake = [[0, 0] for i in range(1, 10)]

visited = Set([Pair(snake[10][1], snake[10][2])])

for line in lines
    dir, n = split(line)
    n = parse(Int64, n)

    for i in range(1, n)
        if dir == "U"
            snake[1][2] += 1
        elseif dir == "D"
            snake[1][2] -= 1
        elseif dir == "R"
            snake[1][1] += 1
        elseif dir == "L"
            snake[1][1] -= 1
        end

        for s in range(2, 10)
            diff = snake[s-1] - snake[s]
            if norm(diff) < 1.42
                continue
            elseif diff[2] == 0
                snake[s][1] += diff[1] ÷ abs(diff[1])
            elseif diff[1] == 0
                snake[s][2] += diff[2] ÷ abs(diff[2])
            else
                snake[s][1] += diff[1] ÷ abs(diff[1])
                snake[s][2] += diff[2] ÷ abs(diff[2])
            end
        end
        # println(tail)
        push!(visited, Pair(snake[10][1], snake[10][2]))

    end
end

println(length(visited))