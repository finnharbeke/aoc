using LinearAlgebra
function task()
    lines = readlines("9.txt")
    head, tail = [0, 0], [0, 0]
    snake = [[0, 0] for i in range(1, 10)]
    visited = Set([Pair(tail...)])
    visited2 = Set([Pair(tail...)])
    move = Dict("U" => [0, 1], "D" => [0, -1], "R" => [1, 0], "L" => [-1, 0])
    for line in lines
        dir, n = split(line)
        n = parse(Int64, n)
        for i in range(1, n)
            head += move[dir]
            snake[1] += move[dir]
            diff = head - tail # part1
            tail += norm(diff) < 1.42 ? [0, 0] : clamp.(diff, -1, 1)
            for s in range(2, 10) # part 2
                diff = snake[s-1] - snake[s]
                snake[s] += norm(diff) < 1.42 ? [0, 0] : clamp.(diff, -1, 1)
            end
            push!(visited, Pair(tail...))
            push!(visited2, Pair(snake[10][1], snake[10][2]))
        end
    end
    println(length(visited), " ", length(visited2))
end
@time task()