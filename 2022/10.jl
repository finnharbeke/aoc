lines = readlines("10.txt")
t, X, sig_str = 0, 1, 0

function draw(crt_x, spr_x)
    if crt_x >= spr_x-1 && crt_x <= spr_x+1
        print("#")
    else
        print(".")
    end
    if crt_x == 39
        println()
    end
end

for line in lines
    global t, X, sig_str
    if line == "noop"
        draw(mod(t, 40), X)
        t += 1
        if mod(t - 20, 40) == 0
            sig_str += t * X
            # println(t * X)
        end
    else
        inc = parse(Int64, split(line)[2])
        for i in range(1, 2)
            draw(mod(t, 40), X)
            t += 1
            if mod(t - 20, 40) == 0
                sig_str += t * X
                # println(t * X)
            end
        end
        X += inc
    end
end


println(sig_str)
