#include <iostream>
#include <set>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

// dirs:
// -> 1
// <- 2
// v  3
// ^  4

void dfs(int x, int y, int dir, vector<string> &map, vector<vector<bool>> &energized, set<pair<pair<int, int>, int>> &visited) {
    pair<pair<int, int>, int> where = {{x, y}, dir};
    if (visited.contains(where))
        return;
    visited.insert(where);
    if (x < 0 || x >= map.at(0).size() || y < 0 || y >= map.size())
        return;
    energized.at(y).at(x) = true;
    const char c = map.at(y).at(x);
    if (c == '.' ||
        (c == '|' && (dir == 3 || dir == 4)) ||
        (c == '-' && (dir == 1 || dir == 2))
        ) {
        if (dir == 1)
            x++;
        else if (dir == 2)
            x--;
        else if (dir == 3)
            y++;
        else if (dir == 4)
            y--;
        dfs(x, y, dir, map, energized, visited);
    } else if (c == '/') {
        if (dir == 1) {
            y--;
            dir = 4;
        } else if (dir == 2) {
            y++;
            dir = 3;
        } else if (dir == 3) {
            x--;
            dir = 2;
        } else if (dir == 4) {
            x++;
            dir = 1;
        }
        dfs(x, y, dir, map, energized, visited);
    } else if (c == '\\') {
        if (dir == 1) {
            y++;
            dir = 3;
        } else if (dir == 2) {
            y--;
            dir = 4;
        } else if (dir == 3) {
            x++;
            dir = 1;
        } else if (dir == 4) {
            x--;
            dir = 2;
        }
        dfs(x, y, dir, map, energized, visited);
    } else if (c == '|') {
        dfs(x, y+1, 3, map, energized, visited);
        dfs(x, y-1, 4, map, energized, visited);
    } else if (c == '-') {
        dfs(x+1, y, 1, map, energized, visited);
        dfs(x-1, y, 2, map, energized, visited);
    }
    // p(c)
}

int start(int x, int y, int dir, vector<string> &map) {
    vector<vector<bool>> energized;
    for (auto line : map) {
        energized.push_back(vector<bool>());
        for (auto c : line)
            energized.at(energized.size() - 1).push_back(false);
    }
    set<pair<pair<int, int>, int>> visited;
    dfs(x, y, dir, map, energized, visited);
    int res = 0;
    for (auto row : energized)
        for (auto b : row)
                res += b;
    return res;
}

signed main() {
    vector<string> map;
    vector<vector<bool>> energized;
    string line;
    while (cin >> line) {
        map.push_back(line);
        energized.push_back(vector<bool>());
        for (int i = 0; i < line.size(); i++)
            energized.at(energized.size()-1).push_back(false);
    }
    set<pair<pair<int, int>, int>> visited;
    dfs(0, 0, 1, map, energized, visited);
    int res = start(0, 0, 1, map);
    cout << res << endl;
    int max = res;
    for (int x = 0; x < map.at(0).size(); x++) {
        res = start(x, 0, 3, map);
        if (res > max)
            max = res;
        res = start(x, map.size()-1, 4, map);
        if (res > max)
            max = res;
    }
    for (int y = 0; y < map.size(); y++) {
        res = start(0, y, 1, map);
        if (res > max)
            max = res;
        res = start(map.at(0).size()-1, y, 2, map);
        if (res > max)
            max = res;
    }
    cout << max << endl;
    return 0;
}