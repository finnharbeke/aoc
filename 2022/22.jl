function monkey_map()

    open = Dict{Tuple{Int, Int}, Bool}()
    left = Dict{Int, Int}()
    right = Dict{Int, Int}()
    top = Dict{Int, Int}()
    bottom = Dict{Int, Int}()
    done_with_map = false
    instr = ""
    start = (0, 0)
    cubeface = 0
    faces = Dict{Tuple{Int, Int}, Int}()
    face_origins = Dict{Int, Tuple{Int, Int}}()
    face_relations = Dict{Int, Dict{Int, Int}}()
    for (i, line) in enumerate(readlines("22.txt"))
        if line == ""
            done_with_map = true
            continue
        end
        if done_with_map
            instr = line
            continue
        end
        right[i] = length(line)
        found_left = false
        for (j, char) in enumerate(line)
            if char == ' '
                continue
            end
            if !(j in keys(top))
                top[j] = i
            end
            if i > get(bottom, j, 0)
                bottom[j] = i
            end
            if !found_left
                left[i] = j
                found_left = true
                if i == 1
                    start = (i, j)
                    cubeface = 1
                    face_origins[cubeface] = start
                    face_relations[cubeface] = Dict{Int, Int}()
                end
            end
            if char == '.'
                open[(i, j)] = true
            elseif char == '#'
                open[(i, j)] = false
            end

            found_face = false
            for (cf, orig) in face_origins

                if abs(i - orig[1]) < 50 && abs(j - orig[2]) < 50
                    found_face = true
                    faces[(i, j)] = cf
                    break
                end
            end

            if found_face
                continue
            end

            cubeface += 1
            face_origins[cubeface] = (i, j)
            faces[(i, j)] = cubeface
            face_relations[cubeface] = Dict{Int, Int}()

            if i > 1 && (i-1, j) in keys(open)
                face_relations[cubeface][3] = faces[(i-1, j)]
                face_relations[faces[(i-1, j)]][1] = cubeface
            end
            if j > 1 && (i, j-1) in keys(open)
                face_relations[cubeface][2] = faces[(i, j-1)]
                face_relations[faces[(i, j-1)]][0] = cubeface
            end
        end
    end

    h = maximum(values(bottom))
    w = maximum(values(right))

    # println([top[j] for j in range(1, w)])
    # println(start)
    # println(instr)

    facing = 0 # 0r, 1d, 2l, 3u

    whereami = start

    ix = 1
    while ix <= length(instr)
        if !isdigit(instr[ix])
            if instr[ix] == 'L'
                facing = mod(facing - 1, 4)
            elseif instr[ix] == 'R'
                facing = mod(facing + 1, 4)
            end
            ix += 1
            continue
        end

        ixs = findnext(r"\d+", instr, ix)
        # println(ixs)
        n = parse(Int, instr[ixs])
        # println(n)
        for _ in range(1, n)
            i, j = whereami
            if facing == 0
                next =  j == right[i] ? (i, left[i]) : (i, j+1)
            elseif facing == 1
                next = i == bottom[j] ? (top[j], j) : (i+1, j)
            elseif facing == 2
                next =   j == left[i] ? (i, right[i]) : (i, j-1)
            else
                next =    i == top[j] ? (bottom[j], j) : (i-1, j)
            end
            # println(next, ": ", open[next])
            if open[next]
                whereami = next
            else
                break
            end
        end

        ix = ixs[length(ixs)] + 1
        # println(ix, "; ", facing, whereami)
        
        # ix <= 7 || break
    end
    
    println(ix, "; ", facing, whereami)
    i, j = whereami
    println(1000 * i + 4 * j + facing)

    println(face_origins)
    println(face_relations)

    function propagate!(cf, dir, to_prop, face_relations)
        n_cf = cf
        println(cf, ": ", dir)
        while dir in keys(face_relations[n_cf]) && face_relations[n_cf][dir] != cf
            println(n_cf)
            if n_cf == face_relations[n_cf][dir]
                break
                println("break: ", n_cf)
            end
            n_cf = face_relations[n_cf][dir]
            for prop in to_prop
                if prop in keys(face_relations[n_cf])
                    face_relations[cf][prop] = face_relations[n_cf][prop]
                end
            end
        end
    end

    # for cf in keys(face_relations)        
    #     propagate!(cf, 0, [1, 3], face_relations)
    #     propagate!(cf, 2, [1, 3], face_relations)
    #     propagate!(cf, 1, [0, 2], face_relations)
    #     propagate!(cf, 3, [0, 2], face_relations)
    # end
    # println(face_relations)

    facing = 0 # 0r, 1d, 2l, 3u

    whereami = start

    ix = 1
    while ix <= length(instr)
        if !isdigit(instr[ix])
            if instr[ix] == 'L'
                facing = mod(facing - 1, 4)
            elseif instr[ix] == 'R'
                facing = mod(facing + 1, 4)
            end
            ix += 1
            continue
        end

        ixs = findnext(r"\d+", instr, ix)
        # println(ixs)
        n = parse(Int, instr[ixs])
        # println(n)
        for _ in range(1, n)
            i, j = whereami
            nf = facing
            if facing == 0
                if j == right[i]
                    if faces[whereami] == 2
                        ri, rj = whereami .- face_origins[2]
                        next = face_origins[5] .+ (49 - ri, 49)
                        nf = 2
                    elseif faces[whereami] == 3
                        ri, rj = whereami .- face_origins[3]
                        next = face_origins[2] .+ (49, ri)
                        nf = 3
                    elseif faces[whereami] == 5
                        ri, rj = whereami .- face_origins[5]
                        next = face_origins[2] .+ (49 - ri, 49)
                        nf = 2
                    elseif faces[whereami] == 6
                        ri, rj = whereami .- face_origins[6]
                        next = face_origins[5] .+ (49, ri)
                        nf = 3
                    end
                else
                    next = (i, j+1)
                end
            elseif facing == 1
                if i == bottom[j]
                    if faces[whereami] == 2
                        ri, rj = whereami .- face_origins[2]
                        next = face_origins[3] .+ (rj, 49)
                        nf = 2
                    elseif faces[whereami] == 5
                        ri, rj = whereami .- face_origins[5]
                        next = face_origins[6] .+ (rj, 49)
                        nf = 2
                    elseif faces[whereami] == 6
                        ri, rj = whereami .- face_origins[6]
                        next = face_origins[2] .+ (0, rj)
                    end
                else
                    next = (i+1, j)
                end
            elseif facing == 2
                if j == left[i]
                    if faces[whereami] == 1
                        ri, rj = whereami .- face_origins[1]
                        next = face_origins[4] .+ (49 - ri, 0)
                        nf = 0
                    elseif faces[whereami] == 3
                        ri, rj = whereami .- face_origins[3]
                        next = face_origins[4] .+ (0, ri)
                        nf = 1
                    elseif faces[whereami] == 4
                        ri, rj = whereami .- face_origins[4]
                        next = face_origins[1] .+ (49 - ri, 0)
                        nf = 0
                    elseif faces[whereami] == 6
                        ri, rj = whereami .- face_origins[6]
                        next = face_origins[1] .+ (0, ri)
                        nf = 1
                    end
                else
                    next = (i, j - 1)
                end
            elseif facing == 3
                if i == top[j]
                    if faces[whereami] == 4
                        ri, rj = whereami .- face_origins[4]
                        next = face_origins[3] .+ (rj, 0)
                        nf = 0
                    elseif faces[whereami] == 1
                        ri, rj = whereami .- face_origins[1]
                        next = face_origins[6] .+ (rj, 0)
                        nf = 0
                    elseif faces[whereami] == 2
                        ri, rj = whereami .- face_origins[2]
                        next = face_origins[6] .+ (49, rj)
                    end
                else
                    next = (i-1, j)
                end
            end
            # println(next, ": ", open[next])
            if open[next]
                whereami = next
                facing = nf
            else
                break
            end
        end

        ix = ixs[length(ixs)] + 1
        # println(ix, "; ", facing, whereami)
        
        # ix <= 7 || break
    end

    i, j = whereami
    println(1000 * i + 4 * j + facing)

end

@time monkey_map()