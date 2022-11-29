input = readline("2.txt")
program = [parse(Int32, s) for s in split(input, ",")]
program[2] = 12
program[3] = 2
for i in 1:4:length(program)
    op = program[i]
    if op == 1
        program[program[i+3]+1] = program[program[i+1]+1] + program[program[i+2]+1]
    elseif op == 2
        program[program[i+3]+1] = program[program[i+1]+1] * program[program[i+2]+1]
    elseif op == 99
        break
    end
end
println(program[1])
input = readline("2.txt")
for noun in 0:99
    for verb in 0:99
        local program
        program = [parse(Int32, s) for s in split(input, ",")]
        program[2] = noun
        program[3] = verb
        for i in 1:4:length(program)
            op = program[i]
            if op == 1
                program[program[i+3]+1] = program[program[i+1]+1] + program[program[i+2]+1]
            elseif op == 2
                program[program[i+3]+1] = program[program[i+1]+1] * program[program[i+2]+1]
            elseif op == 99
                break
            end
        end
        if program[1] == 19690720
            println("noun=", noun, "verb=", verb, " ", noun*100+verb)
        end
    end
end