lines = readlines("1.txt")

maxsum = 0
sum = 0
println(getindex(lines, [1, 2, 3, 4, 5, 6]))
sums = Int64[]
for line in lines
    global sum, maxsum
    if line == ""
        maxsum = max(maxsum, sum)
        push!(sums, sum)
        sum = 0
    else
        sum += parse(Int64, line)
    end
end
println(maxsum)
sort!(sums)
n = length(sums)
println(sums[n], " ", sums[n-1], " ", sums[n-2], ", ", sums[n] + sums[n-1] + sums[n-2])