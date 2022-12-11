lines = readlines("11.txt")
items = []
increase = []
dividors = []
iftrues = []
iffalses = []
for i in 1:7:length(lines)
    ix = parse(Int64, lines[i][findfirst(r"\d+", lines[i])])
    push!(items, [parse(Int64, lines[i+1][x]) for x in findall(r"\d+", lines[i+1])])
    words = split(lines[i+2])
    push!(increase, words[length(words)-2:length(words)])
    push!(dividors, parse(Int64, lines[i+3][findfirst(r"\d+", lines[i+3])]))
    push!(iftrues, parse(Int64, lines[i+4][findfirst(r"\d+", lines[i+4])]) + 1)
    push!(iffalses, parse(Int64, lines[i+5][findfirst(r"\d+", lines[i+5])]) + 1)
end

n = length(items)

function getVal(old, s)
    if s == "old"
        return old
    else
        return parse(Int64, s)
    end
end

monkey_inspection_counts = [0 for m in range(1, n)]

modder = prod(dividors)

for r in range(1, 10000)
    for m in range(1, n)

        monkey_inspection_counts[m] += length(items[m])
        while length(items[m]) > 0
            x = items[m][1]
            if increase[m][2] == "+"
                new = getVal(x, increase[m][1]) + getVal(x, increase[m][3])
            elseif increase[m][2] == "*"
                new = getVal(x, increase[m][1]) * getVal(x, increase[m][3])
            end
            # x = new ÷ 3
            x = mod(new, modder)
            if mod(x, dividors[m]) == 0
                push!(items[iftrues[m]], x)
            else
                push!(items[iffalses[m]], x)
            end
            popfirst!(items[m])
        end
    end
    if (r == 100 || r == 500 || r == 2000)
        println(items)
    end
end

println(monkey_inspection_counts)
sort!(monkey_inspection_counts)
println(monkey_inspection_counts[n], " * ", monkey_inspection_counts[n-1], " = ", monkey_inspection_counts[n]*monkey_inspection_counts[n-1])