function decode(snafu)

    total = 0
    mult = 1    
    for i in range(length(snafu), 1, step=-1)
        dig = undef
        if isdigit(snafu[i])
            dig = parse(Int, snafu[i])
        elseif snafu[i] == '-'
            dig = -1
        else
            dig = -2
        end
        total += mult * dig
        mult *= 5
    end
    return total
end

function encode(decimal)

    snafu = ""
    mult = 1
    while decimal > 0
        rest = decimal % (mult * 5)
        dig = rest ÷ mult
        if dig < 3
            snafu = string(dig) * snafu
            decimal -= dig * mult
        elseif dig == 3
            snafu = "=" * snafu
            decimal += 2 * mult
        elseif dig == 4
            snafu = "-" * snafu
            decimal += 1 * mult
        end
        mult *= 5
    end
    return snafu
end

function fully_of_hot_air()

    total = 0
    for line in readlines("25.txt")
        decimal = decode(line)
        total += decimal
    end
    println(total)
    println(encode(total))
end

@time fully_of_hot_air()