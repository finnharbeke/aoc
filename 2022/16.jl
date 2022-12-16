function DP(n::Int, starts::Array{Int64}, dists::Matrix{Int64}, flow_rate::Array{Int64}, total_time::Int)
    PER = zeros(Int64, (total_time+1, n))
    INIT = zeros(Int64, (total_time+1, n))

    paths = Matrix{Vector{Int64}}(undef, (total_time+1, n))

    for t in range(1, total_time+1)
        for v in range(1, n)
            if (t == 1)
                PER[t, v] = 0
                INIT[t, v] = 0
                paths[t, v] = Int[]
                continue
            end
            INIT[t, v] = INIT[t-1, v] + PER[t-1, v]
            PER[t, v] = PER[t-1, v]
            paths[t, v] = paths[t-1, v]
            if (t - 1 - starts[v]) == 1
                PER[t, v] = flow_rate[v]
                paths[t, v] = [v]
                # println("$v: t=$t")
            end
            # DP[t+1, v] = max(0,  * flow_rate[v])
            for v2 in range(1, n)
                before = t - 1 - dists[v2, v]
                if before <= 0 || v == v2 || v in paths[before, v2] || !(v2 in paths[before, v2])
                    continue
                end
                per = PER[before, v2] + flow_rate[v]
                init = INIT[before, v2] + (t - before) * PER[before, v2]
                if per > PER[t, v] || (per == PER[t, v] && init > INIT[t, v])
                    PER[t, v] = per
                    INIT[t, v] = init
                    paths[t, v] = push!(copy(paths[before, v2]), v)
                end
            end
        end
    end
    return maximum(INIT[total_time+1, :])
end

function bfs(start::String, nexts::Dict{String, Vector{String}}, n, valves)
    visited = Set{String}()
    q = Vector{Pair{String, Int}}()
    push!(q, Pair(start, 0))
    push!(visited, start)
    dists = Array{Int64}(undef, n)
    while length(q) > 0
        v, d = popfirst!(q)
        if v in valves
            vi = findfirst(v .== valves)
            dists[vi] = d
        end
        push!(visited, v)
        for next in nexts[v]
            if !(next in visited)
                push!(q, Pair(next, d+1))
            end
        end

    end
    return dists
end

function main()
    lines = readlines("16.txt")

    nexts = Dict{String, Vector{String}}()
    flow_rate = Int64[]
    valves = String[]
    v = 1
    for line in lines
        if length(line) == 0
            continue
        end
        valve = line[7:8]
        fr = parse(Int64, line[findfirst(r"\d+", line)])
        if fr > 0
            v += 1
            push!(flow_rate, fr)
            push!(valves, valve)
        end
        if findfirst(r"valves .*", line) === nothing
            nexts[valve] = split(line[findfirst(r"valve .*", line)[7]:length(line)], ", ")
        else
            nexts[valve] = split(line[findfirst(r"valves .*", line)[8]:length(line)], ", ")
        end
    end
    # println(nexts)
    # println(flow_rate)
    
    time_left = 30
    time_left_together = 26
    n = length(valves)

    dists = zeros(Int64, (n, n))

    a_dists = bfs("AA", nexts, n, valves)
    for v in range(1, n)
        dists[v, :] = bfs(valves[v], nexts, n, valves)
    end
    println(n)
    # println(flow_rate)
    # println(a_dists)
    # println(dists)

    @time res = DP(n, a_dists, dists, flow_rate, time_left)
    println(res)
    max_released = 0
    for bin in range(0, 2^(n+1) - 1)
        me, ele = Int[], Int[]
        for j in range(1, n)
            if bin % 2 == 0
                push!(me, j)
            else
                push!(ele, j)
            end
            bin ÷= 2
        end
        nm, ne = length(me), length(ele)
        ix_me, ix_el = Array{Int}(undef, nm), Array{Int}(undef, ne)
        mi, ei = 1, 1
        for i in range(1, n)
            if i in me
                ix_me[mi] = i
                mi += 1
            else
                ix_el[ei] = i
                ei += 1
            end
        end
        starts_me, starts_el = Array{Int}(undef, nm), Array{Int}(undef, ne)
        for i in range(1, nm)
            starts_me[i] = a_dists[ix_me[i]]
        end
        for i in range(1, ne)
            starts_el[i] = a_dists[ix_el[i]]
        end
        fr_me, fr_el = Array{Int}(undef, nm), Array{Int}(undef, ne)
        for i in range(1, nm)
            fr_me[i] = flow_rate[ix_me[i]]
        end
        for i in range(1, ne)
            fr_el[i] = flow_rate[ix_el[i]]
        end
        
        dists_me, dists_el = zeros(Int64, (nm, nm)), zeros(Int64, (ne, ne))
        for i in range(1, nm)
            for j in range(1, nm)
                dists_me[i, j] = dists[ix_me[i], ix_me[j]]
            end
        end
        for i in range(1, ne)
            for j in range(1, ne)
                dists_el[i, j] = dists[ix_el[i], ix_el[j]]
            end
        end

        released =  nm > 0 ? DP(nm, starts_me, dists_me, fr_me, time_left_together) : 0
        released += ne > 0 ? DP(ne, starts_el, dists_el, fr_el, time_left_together) : 0
        max_released = max(max_released, released)
    end
    println(max_released)
end

@time main()