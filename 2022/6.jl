line = readline("6.txt")

for i in 1:1:length(line) - 3
    s = getindex(line, i:1:i+3)
    if length(Set(s)) == 4
        println(i+3)
        break
    end
end

for i in 1:1:length(line) - 13
    s = getindex(line, i:1:i+13)
    if length(Set(s)) == 14
        println(i+13)
        break
    end
end