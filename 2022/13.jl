function read_num(str, ix)
    if isdigit(str[ix+1])
        return parse(Int64, str[ix:ix+1]), ix+1
    else
        return parse(Int64, str[ix]), ix
    end
end

function make_list(str, ix)
    if isdigit(str[ix+1])
        return str[1:ix-1] * "[" * str[ix:ix+1] * "]" * str[ix+2:length(str)]
    else
        return str[1:ix-1] * "[" * str[ix] * "]" * str[ix+1:length(str)]
    end
end

function check_pair(left, right)
    li = 1
    ri = 1
    while li <= length(left) && ri <= length(right)
        if isdigit(left[li]) && isdigit(right[ri])
            l_val, li = read_num(left, li)
            r_val, ri = read_num(right, ri)
            if l_val < r_val
                return true
            elseif l_val > r_val
                return false
            end
        elseif (left[li] == '[' && right[ri] == '[') ||
               (left[li] == ']' && right[ri] == ']') ||
               (left[li] == ',' && right[ri] == ',')
            # nothing
        elseif left[li] == ']'
            return true
        elseif right[li] == ']'
            return false
        elseif isdigit(left[li]) && right[ri] == '['
            left = make_list(left, li)
        elseif isdigit(right[ri]) && left[li] == '['
            right = make_list(right, ri)
        else
            println("whaat")
        end

        li += 1
        ri += 1
    end

end

function main()
    lines = readlines("13.txt")
    total = 0
    div1_i, div2_i = 0, 0
    for i in 1:3:length(lines)
        left = lines[i]
        right = lines[i+1]

        if check_pair(left, right)
            # println(i ÷ 3 + 1)
            total += i ÷ 3 + 1
        end
        div1_i += check_pair(left, "[[2]]")
        div1_i += check_pair(right, "[[2]]")
    end

    println(total)

    println(div1_i * div2_i)
end

@time main()