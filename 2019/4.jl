start = 246515
stop = 739105

function valid_password(s)
    if length(s) != 6
        return false
    end
    second_last_char = 'a'
    last_char = 'a'
    last_i = 0
    has_double_d = false
    for (ix, char) in enumerate(s)
        # print(ix, s[ix], "\n")
        if char == last_char && second_last_char != last_char && (ix == length(s) || s[ix+1] != char)
            has_double_d = true
        end
        i = parse(Int8, char)
        if i < last_i
            return false
        end
        second_last_char = last_char
        last_char = char
        last_i = i
    end
    return has_double_d
end

sum = 0
for i in start:stop
    global sum
    s = string(i)
    sum += valid_password(s)
end
print(sum, "\n")