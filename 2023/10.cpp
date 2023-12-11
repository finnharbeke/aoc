#include <iostream>
#include <assert.h>
#define int int64_t
using namespace std;

const bool OO = 0;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

struct pos {
    int x;
    int y;
};

// directions
//     1
//    4 2
//     3

pos direction(int d) {
    if (d == 1)
        return (struct pos){ 0, -1 };
    else if (d == 2)
        return (struct pos){ 1, 0 };
    else if (d == 3)
        return (struct pos){ 0, 1 };
    else if (d == 4)
        return (struct pos){ -1, 0 };
    p("not good")
    return (struct pos){ 0, 0 };
}

bool is_floor(const char c) {
    return c == '.';
}

int opposite(int dir) {
    if (dir <= 2)
        return dir + 2;
    return dir - 2;
}

int other_dir(const char c, int dir) {
    if (c == '|') {
        assert(dir == 1 || dir == 3);
        if (dir == 1)
            return 3;
        return 1;
    } else if (c == '-') {
        assert(dir == 2 || dir == 4);
        if (dir == 2)
            return 4;
        return 2;
    } else if (c == 'L') {
        assert(dir == 1 || dir == 2);
        if (dir == 1)
            return 2;
        return 1;
    } else if (c == 'J') {
        assert(dir == 1 || dir == 4);
        if (dir == 1)
            return 4;
        return 1;
    } else if (c == 'F') {
        assert(dir == 2 || dir == 3);
        if (dir == 2)
            return 3;
        return 2;
    } else if (c == '7') {
        assert(dir == 4 || dir == 3);
        if (dir == 4)
            return 3;
        return 4;
    }
    p("not goood")
    return 0;
}

bool open_north(const char c) {
    return c == '|' || c == 'L' || c == 'J';
}

bool open_east(const char c) {
    return c == '-' || c == 'L' || c == 'F';
}

bool open_south(const char c) {
    return c == '|' || c == '7' || c == 'F';
}

bool open_west(const char c) {
    return c == '-' || c == '7' || c == 'J';
}

int h = 0, w = 0;

int check_for_pipe(vector<string> &map, pos where, int from) {
    if (where.x < 0 || where.x >= w || where.y < 0 || where.y >= h)
        return -1;
    const char c = map.at(where.y).at(where.x);
    // p(where.x)
    // p(where.y)
    // p(c)
    // p(from)
    if (c == 'S')
        return 0;
    if (is_floor(c))
        return -1;
    if (from == 1 && !open_north(c))
        return -1;
    if (from == 2 && !open_east(c))
        return -1;
    if (from == 3 && !open_south(c))
        return -1;
    if (from == 4 && !open_west(c))
        return -1;
    int to = other_dir(c, from);
    // p(to)
    pos diff = direction(to);
    pos next = { where.x + diff.x, where.y + diff.y };
    int pipe_steps = check_for_pipe(map, next, opposite(to));
    return pipe_steps + (pipe_steps >= 0);
}

// part 2

void run_along(vector<string> &old_map, vector<string> &loop, pos where, int from, pos start) {
    const char c = old_map.at(where.y).at(where.x);
    loop.at(where.y).at(where.x) = c;
    if (where.y == start.y && where.x == start.x) {
        return;
    }
    int to = other_dir(c, from);
    pos diff = direction(to);
    pos next = { where.x + diff.x, where.y + diff.y };
    run_along(old_map, loop, next, opposite(to), start);
}

// corner within tile

// 12
// 34

vector<vector<int> > reachable_corners_in_dirs(const char c, int corner) {
    if (c == '.' || c == 'O' || c == 'I') {
        return {
            {3, 4}, // 34 in north
            {1, 3}, // 13 in east...
            {1, 2}, // 12 in south...
            {2, 4}, // 13 in west...
        };
    }
    if (c == '|') {
        if (corner == 1 || corner == 3) {
            return {
                {3},
                {},
                {1},
                {2, 4},
            };
        } else {
            return {
                {4},
                {1, 3},
                {2},
                {},
            };
        }
    }
    if (c == '-') {
        if (corner == 1 || corner == 2) {
            return {
                {3, 4},
                {1},
                {},
                {2},
            };
        } else {
            return {
                {},
                {3},
                {1, 2},
                {4},
            };
        }
    }
    if (c == 'J') {
        if (corner == 1) {
            return {
                {3},
                {},
                {},
                {2},
            };
        } else {
            return {
                {4},
                {1, 3},
                {1, 2},
                {4},
            };
        }
    }
    if (c == 'L') {
        if (corner == 2) {
            return {
                {4},
                {1},
                {},
                {},
            };
        } else {
            return {
                {3},
                {3},
                {1, 2},
                {2, 4},
            };
        }
    }
    if (c == '7') {
        if (corner == 3) {
            return {
                {},
                {},
                {1},
                {4},
            };
        } else {
            return {
                {3, 4},
                {1, 3},
                {2},
                {2},
            };
        }
    }
    if (c == 'F') {
        if (corner == 4) {
            return {
                {},
                {3},
                {2},
                {},
            };
        } else {
            return {
                {3, 4},
                {1},
                {1},
                {2, 4},
            };
        }
    }
    p("nono good")
    return vector<vector<int> >();
}

bool dfs(vector<string> &map, vector<vector<bool> > &visited, pos where, int corner) {
    if (OO)
        cout << where.y << ' ' << where.x << ':' << corner << endl;
    const char c = map.at(where.y).at(where.x);
    if (c == 'O')
        return true;
    if (c == 'I')
        return false;
    if (visited.at(where.y).at(where.x))
        return false;
    visited.at(where.y).at(where.x) = true;
    // p(where.x)
    // p(where.y)
    if (where.x == 0 && (
        corner == 1 || corner == 3 ||
        (corner == 2 && c != '|' && c != 'L') ||
        (corner == 4 && c != '|' && c != 'F')
    ))
        return true;
    if (where.x == w - 1 && (
        corner == 2 || corner == 4 ||
        (corner == 1 && c != '|' && c != 'J') ||
        (corner == 3 && c != '|' && c != '7')
    ))
        return true;
    if (where.y == 0 && (
        corner == 1 || corner == 2 ||
        (corner == 3 && c != '-' && c != '7') ||
        (corner == 4 && c != '-' && c != 'F')
    ))
        return true;
    if (where.y == h - 1 && (
        corner == 3 || corner == 4 ||
        (corner == 1 && c != '-' && c != 'J') ||
        (corner == 2 && c != '-' && c != 'L')
    ))
        return true;
    vector<vector<int> > neigh_corners = reachable_corners_in_dirs(map.at(where.y).at(where.x), corner);
    for (int dir = 1; dir <= 4; dir++) {
        pos diff = direction(dir);
        pos next = { where.x + diff.x, where.y + diff.y };
        for (auto next_corner : neigh_corners.at(dir-1)) {
            // p(next.x)
            // p(next.y)
            // p(next_corner)

            if (dfs(map, visited, next, next_corner))
                return true;
        }
    }
    return false;
}

bool can_get_out(vector<string> &map, pos where) {
    const char c = map.at(where.y).at(where.x);
    assert(c == '.');
    vector<vector<bool> > vis;
    for (int i = 0; i < h; i++) {
        vis.push_back(vector<bool>());
        for (int j = 0; j < w; j++)
            vis.at(vis.size()-1).push_back(false);
    }
    bool can = dfs(map, vis, where, 1);

    if (OO) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (where.y == i && where.x == j)
                    cout << 'X';
                else if (vis.at(i).at(j))
                    cout << '*';
                else
                    cout << '.';
            }
            cout << endl;
        }
        cout << endl;
    }
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++)
            if (map.at(where.y).at(where.x) == '.' && vis.at(where.y).at(where.x)) {
                if (can)
                    map.at(where.y).at(where.x) = 'O';
                else
                    map.at(where.y).at(where.x) = 'I';
            }
    }
    
    if (OO) {
        for (auto line : map)
            cout << line << endl;
        cout << endl;
    }
    return can;
}

signed main() {
    vector<string> map;
    string line;
    pos start;
    int y = 0;
    while (cin >> line) {
        map.push_back(line);
        int x = line.find('S');
        if (x != string::npos)
            start = (struct pos){ x, y };
        y++;
    }
    w = map.at(0).size();
    h = map.size();

    p(start.x)
    p(start.y)

    pos pipe_start;
    vector<int> dirs;
    int from_start = 1;

    int pipe_steps;
    for (int dir = 1; dir <= 4; dir++) {
        pos diff = direction(dir);
        int temp_from = opposite(dir);
        pos temp = (struct pos){ start.x + diff.x, start.y + diff.y };
        p(temp.x)
        p(temp.y)
        pipe_steps = check_for_pipe(map, temp, temp_from) + 1;
        if (pipe_steps > 0) {
            dirs.push_back(dir);
            pipe_start = temp;
            from_start = temp_from;
        } else
            p("not a pipe start")
    }

    cout << start.x << " " << start.y << endl;
    cout << pipe_start.x << " " << pipe_start.y << endl;
    cout << pipe_steps << endl;
    cout << pipe_steps / 2 << endl;

    sort(dirs.begin(), dirs.end());
    p_vec(dirs);
    if (dirs == vector<int>{1, 2})
        map.at(start.y).at(start.x) = 'L';
    else if (dirs == vector<int>{1, 3})
        map.at(start.y).at(start.x) = '|';
    else if (dirs == vector<int>{1, 4})
        map.at(start.y).at(start.x) = 'J';
    else if (dirs == vector<int>{2, 3})
        map.at(start.y).at(start.x) = 'F';
    else if (dirs == vector<int>{2, 4})
        map.at(start.y).at(start.x) = '-';
    else if (dirs == vector<int>{3, 4})
        map.at(start.y).at(start.x) = '7';
    else
        p("huh")

    int trapped = 0;
    vector<string> loop;
    for (int y = 0; y < h; y++) {
        loop.push_back("");
        for (int x = 0; x < w; x++)
            loop.at(y).append(".");
    }

    run_along(map, loop, pipe_start, from_start, start);
    for (auto line : loop)
        cout << line << endl;
    cout << endl;


    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
            if (loop.at(y).at(x) != '.') {
                if (loop.at(y).at(x) == 'I')
                    trapped++;
                continue;
            }
            if (!can_get_out(loop, (struct pos){ x, y }))
                trapped++;
        }
    }
    for (auto line : loop)
        cout << line << endl;
    cout << endl;
    cout << trapped << endl;
    return 0;
}