lines = readlines("8.txt")

w = length(lines[1])
h = length(lines)

trees = zeros(Int64, (w, h))

for (i, line) in enumerate(lines)
    for j in range(1, w)
        trees[i, j] = parse(Int64, line[j])
    end
end

visible = zeros(Bool, (w, h))

for i in range(1, h)
    for j in range(1, w)
        if i == 1 || i == h || j == 1 || j == w
            visible[i, j] = true
        elseif trees[i,j] > maximum(trees[1:i-1, j])
            visible[i, j] = true
        elseif trees[i,j] > maximum(trees[i+1:h, j])
            visible[i, j] = true
        elseif trees[i,j] > maximum(trees[i, 1:j-1])
            visible[i, j] = true
        elseif trees[i,j] > maximum(trees[i, j+1:w])
            visible[i, j] = true
        end
    end
end

for i in range(1, h)
    println(join([(visible[i, j] ? "#" : " ") for j in range(1, w)], ""))
end
println(sum(visible))

best = 0
for i in range(1, h)
    for j in range(1, w)
        global best
        up = [(i2 == 1 || trees[i2, j] >= trees[i, j]) for i2 in i-1:-1:1]
        down = [(i2 == h || trees[i2, j] >= trees[i, j]) for i2 in i+1:h]
        left = [(j2 == 1 || trees[i, j2] >= trees[i, j]) for j2 in j-1:-1:1]
        right = [(j2 == w || trees[i, j2] >= trees[i, j]) for j2 in j+1:w]
        
        score = prod([isnothing(x) ? 0 : x for x in [indexin(true, dir)[1] for dir in [up, down, right, left]]])
        best = max(score, best)
    end
end
println(best)
