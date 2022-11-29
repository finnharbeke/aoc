input = readline("5.txt")
# input = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"
program = [parse(Int32, s) for s in split(input, ",")]

i = 1
while i <= length(program)
    global i
    instr = string(program[i])
    while length(instr) < 5
        instr = "0" * instr
    end
    
    op = parse(Int, getindex(instr, [4, 5]))
    println(op)
    params = Int64[]
    local stop
    if op <= 2
        stop = 3
    elseif op <= 4
        stop = 1
    elseif op <= 6
        stop = 2
    elseif op <= 8
        stop = 3
    else
        stop = 0
    end
    for p in 1:stop
        if instr[4-p] == '0'
            append!(params, program[program[i+p]+1])
        else
            append!(params, program[i+p])
        end
    end
    if op == 1
        program[program[i+3]+1] = params[1] + params[2]
        i += 4
    elseif op == 2
        program[program[i+3]+1] = params[1] * params[2]
        i += 4
    elseif op == 3
        program[program[i+1]+1] = 5 # 1 for part 1
        i += 2
    elseif op == 4
        println("out: ", params[1])
        i += 2
    elseif op == 5
        if params[1] != 0
            i = params[2] + 1
        else
            i += 3
        end
    elseif op == 6
        if params[1] == 0
            i = params[2] + 1
        else
            i += 3
        end
    elseif op == 7
        if params[1] < params[2]
            program[program[i+3]+1] = 1
        else
            program[program[i+3]+1] = 0
        end
        i += 4
    elseif op == 8
        if params[1] == params[2]
            program[program[i+3]+1] = 1
        else
            program[program[i+3]+1] = 0
        end
        i += 4
    elseif op == 99
        i += 1
        break
    end
end

println("done")