lines = readlines("13.txt")

function check_pair(left, right)

    li = 1
    ri = 1
    while li <= length(left) && ri <= length(right)
        if isdigit(left[li]) && isdigit(right[ri])
            if isdigit(left[li+1])
                l_val = parse(Int64, left[li:li+1])
                li += 1
            else
                l_val = parse(Int64, left[li])
            end
            if isdigit(right[ri+1])
                r_val = parse(Int64, right[ri:ri+1])
                ri += 1
            else
                r_val = parse(Int64, right[ri])
            end
            if l_val < r_val
                return true
            elseif l_val > r_val
                return false
            end
        elseif (left[li] == '[' && right[ri] == '[') || (left[li] == ']' && right[ri] == ']') || (left[li] == ',' && right[ri] == ',')
            # nothing
        elseif left[li] == ']'
            return true
        elseif right[li] == ']'
            return false
        elseif isdigit(left[li]) && right[ri] == '['
            if isdigit(left[li+1])
                left = left[1:li-1] * "[" * left[li:li+1] * "]" * left[li+2:length(left)]
            else
                left = left[1:li-1] * "[" * left[li] * "]" * left[li+1:length(left)]
            end
        elseif isdigit(right[ri]) && left[li] == '['
            if isdigit(right[ri+1])
                right = right[1:ri-1] * "[" * right[ri:ri+1] * "]" * right[ri+2:length(right)]
            else
                right = right[1:ri-1] * "[" * right[ri] * "]" * right[ri+1:length(right)]
            end
        else
            println("whaat")
            println(left[li:length(left)])
            println(right[ri:length(right)])
        end

        li += 1
        ri += 1
    end

end

total = 0
for i in 1:3:length(lines)
    left = lines[i]
    right = lines[i+1]

    if check_pair(left, right)
        global total
        # println(i ÷ 3 + 1)
        total += i ÷ 3 + 1
    end
end

println(total)

# part 2
filter!(l -> l != "", lines)
div1 = "[[2]]"
div2 = "[[6]]"
push!(lines, div1)
push!(lines, div2)

sort!(lines, lt=check_pair)

div1_i = findfirst(isequal(div1), lines)
div2_i = findfirst(isequal(div2), lines)

println(div1_i * div2_i)
