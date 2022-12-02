lines = readlines("test.txt")

score = 0
score2 = 0

function onescore2(s)
    theirs = s[1]
    mine = s[3]
    s = 0
    if mine == 'X'
        if theirs == 'A'
            return 3
        elseif theirs == 'B'
            return 1
        else
            return 2
        end
    elseif mine == 'Y'
        if theirs == 'A'
            return 4
        elseif theirs == 'B'
            return 5
        else
            return 6
        end 
    else
        if theirs == 'A'
            return 8
        elseif theirs == 'B'
            return 9
        else
            return 7
        end
    end
end

function onescore(s)
    theirs = s[1]
    mine = s[3]
    s = 0
    if mine == 'X'
        if theirs == 'C'
            return 7
        elseif theirs == 'A'
            return 4
        else
            return 1
        end
    elseif mine == 'Y'
        if theirs == 'A'
            return 8
        elseif theirs == 'B'
            return 5
        else
            return 2
        end 
    else
        if theirs == 'B'
            return 9
        elseif theirs == 'C'
            return 6
        else
            return 3
        end
    end
end

for line in lines
    global score, score2
    score += onescore(line)
    score2 += onescore2(line)
end
println(score)
println(score2)