lines = readlines("7.txt")

cd = ""
files = Dict()
dirs = Dict()
parents = Dict()
all_dirs = Set()
i = 1
while i <= length(lines)
    global cd, files, dirs, i
    cmds = split(lines[i])
    if cmds[2] == "cd"
        println(cmds[3])
        if cmds[3] == ".."
            cd = parents[cd]
        elseif cmds[3] == "/"
            cd = cmds[3]
        else
            new_cd = cd * cmds[3] * "/"
            if !(new_cd in keys(parents))
                parents[new_cd] = cd
            end
            if !(new_cd in dirs[cd])
                push!(dirs[cd], new_cd)
            end
            cd = new_cd            
        end
        if !(cd in keys(files))
            files[cd] = []
            dirs[cd] = []
        end
        i += 1
        
    elseif cmds[2] == "ls"
        fs = []
        i += 1
        while i <= length(lines) && lines[i][1] != '$'
            size, name = split(lines[i])
            if size == "dir"
                dir_name = cd * name * "/"
                if !(dir_name in dirs[cd])
                    push!(dirs[cd], dir_name)
                end
            else
                size = parse(Int64, size)
                if !(name in [t[1] for t in files[cd]])
                    push!(fs, (name, size))
                end
            end
            i += 1
        end
        append!(files[cd], fs)
    else
        println("error")
    end
    push!(all_dirs, cd)
end

direct_sizes = Dict()
for key in keys(files)
    sum = 0
    for (name, size) in files[key]
        sum += size
    end
    direct_sizes[key] = sum
end
sizes = Dict()

function getsize(key)
    if key in keys(dirs) && length(dirs[key]) > 0
        return get(direct_sizes, key, 0) + sum([getsize(child) for child in dirs[key]])
    else
        return get(direct_sizes, key, 0)
    end
end

total = 0
for key in all_dirs
    global total, getsize
    sizes[key] = getsize(key)
    # println(sizes[key], " ", key)
    if sizes[key] < 100000
        total += sizes[key]
    end
end

# println(files)
# println(dirs)
# println(direct_sizes)
# println(sizes)
println(total)
to_delete = sizes["/"] - 40000000
min_amount = 70000000
for key in keys(sizes)
    global min_amount
    if sizes[key] >= to_delete
        min_amount = min(min_amount, sizes[key])
    end
end
println(min_amount)