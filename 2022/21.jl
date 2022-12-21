function eval_if_poss(monkeys, monkey, part1=false)
    if monkey == "humn" && !part1
        return nothing
    end
    if typeof(monkeys[monkey]) == Int
        return monkeys[monkey]
    end
    m1, op, m2 = split(monkeys[monkey], " ")
    r1, r2 = eval_if_poss(monkeys, m1, part1), eval_if_poss(monkeys, m2, part1)
    if r1 === nothing || r2 === nothing
        return nothing
    elseif op == "+"
        return r1 + r2
    elseif op == "-"
        return r1 - r2
    elseif op == "*"
        return r1 * r2
    elseif op == "/"
        return r1 ÷ r2
    end
end

function set_equal(monkeys, monkey, x)
    if monkey == "humn"
        return x
    elseif typeof(monkeys[monkey]) == Int
        return nothing # shouldn't be happening
    end
    m1, op, m2 = split(monkeys[monkey], " ")
    a = eval_if_poss(monkeys, m1)
    b = eval_if_poss(monkeys, m2)
    if a === nothing && b === nothing
        return nothing
    elseif a === nothing
        if op == "+"
            return set_equal(monkeys, m1, x - b)
        elseif op == "-"
            return set_equal(monkeys, m1, x + b)
        elseif op == "*"
            return set_equal(monkeys, m1, x ÷ b)
        elseif op == "/"
            return set_equal(monkeys, m1, x * b)
        end
    else
        if op == "+"
            return set_equal(monkeys, m2, x - a)
        elseif op == "-"
            return set_equal(monkeys, m2, - x + a)
        elseif op == "*"
            return set_equal(monkeys, m2, x ÷ a)
        elseif op == "/"
            return set_equal(monkeys, m2, a ÷ x)
        end
    end

end

function monkey_math()
    monkeys = Dict{String, Union{AbstractString, Int}}()

    for line in readlines("21.txt")
        monkey, expr = split(line, ": ")
        if findfirst(r"\d+", expr) !== nothing
            expr = parse(Int, expr)
        end
        monkeys[monkey] = expr
    end

    println(eval_if_poss(monkeys, "root", true))

    # part 2

    eq1, _, eq2 = split(monkeys["root"], " ")

    r1 = eval_if_poss(monkeys, eq1)
    r2 = eval_if_poss(monkeys, eq2)
    if r1 === nothing
        println(set_equal(monkeys, eq1, r2))
    else
        println(set_equal(monkeys, eq2, r1))
    end

end

@time monkey_math()