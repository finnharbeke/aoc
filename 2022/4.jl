lines = readlines("4.txt")

pattern = r"\d+"
reconsider = 0
overlap = 0
for line in lines
    global reconsider, overlap
    # println(line)
    min1, max1, min2, max2 = findall(pattern, line)

    # println(min1, "-", max1, " ", min2, "-", max2)
    min1, max1, min2, max2 = [parse(Int64, s) for s in [getindex(line, ix) for ix in [min1, max1, min2, max2]]]
    # println(min1, "-", max1, " ", min2, "-", max2)
    if min1 <= min2 && max1 >= max2
        reconsider += 1
    elseif min2 <= min1 && max2 >= max1
        reconsider += 1
    elseif min1 <= min2 && min2 <= max1
        overlap += 1
    elseif min2 <= min1 && min1 <= max2
        overlap += 1
    end
end
println(reconsider)
println(reconsider + overlap)