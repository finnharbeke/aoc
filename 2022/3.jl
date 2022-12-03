lines = readlines("3.txt")

sum = 0

for line in lines
    global sum
    mid = length(line) ÷ 2
    front = line[1:mid]
    back = line[mid+1:length(line)]
    # println(front, " ", back)
    inter = intersect(Set(front), Set(back))
    for char in inter
        # println(char)
        if islowercase(char)
            sum += char - 'a' + 1
        else
            sum += char - 'A' + 27
        end
    end

    # println(sum)
end

println(sum)
sum = 0

for i in 1:3:length(lines)
    global sum
    inter = intersect(Set(lines[i]), Set(lines[i+1]), Set(lines[i+2]))
    for char in inter
        # println(char)
        if islowercase(char)
            sum += char - 'a' + 1
        else
            sum += char - 'A' + 27
        end
    end
end

println(sum)