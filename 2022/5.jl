lines = readlines("5.txt")

stacks = Any[]
for i in range(1, 9)
    push!(stacks, Char[])
end

for i in range(1, 8)
    global stacks
    line = lines[i]
    ixs = findall(r"[A-Z]+", line)
    for ix in ixs
        ix = ix[1]
        i = (ix - 2) ÷ 4 + 1
        push!(stacks[i], line[ix])
    end
end

for i in range(1, 9)
    reverse!(stacks[i])
end
println(stacks)

# for line in lines[11:length(lines)]
#     local n
#     n, from, to = findall(r"\d+", line)
#     n, from, to = [parse(Int64, s) for s in [line[n], line[from], line[to]]]

#     for _ in range(1, n)
#         x = pop!(stacks[from])
#         # println(x)
#         push!(stacks[to], x)
#     end
# end
for line in lines[11:length(lines)]
    local n
    n, from, to = findall(r"\d+", line)
    n, from, to = [parse(Int64, s) for s in [line[n], line[from], line[to]]]

    for i in range(1, n)
        x = popat!(stacks[from], length(stacks[from]) - (n-i))
        # println(x)
        push!(stacks[to], x)
    end
end


# println([string(stack[length(stack)]) for stack in stacks])
println(join([string(stack[length(stack)]) for stack in stacks], ""))