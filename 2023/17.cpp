#include <iostream>
#include <queue>
#include <set>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}


struct tile {
    int x;
    int y;
    int total_loss;
    int dir;
    int dir_total;
};

signed main() {

    vector<string> city;
    string line;
    while (cin >> line)
        city.push_back(line);
    
    vector<vector<map<int, int>>> visited;
    for (int i = 0; i < city.size(); i++) {
        visited.push_back(vector<map<int, int>>());
        for (int j = 0; j < city.at(0).size(); j++)
            visited.at(i).push_back(map<int, int>());
    }
    // bool[city.size()][city.at(0).size()] visited;

    auto cmp = [](tile a, tile b) {
        return a.total_loss > b.total_loss;
    };

    priority_queue<tile, vector<tile>, decltype(cmp)> djikstra(cmp);
    
    djikstra.push({0, 0, 0, 1, 0});
    while (djikstra.size() > 0) {
        tile mytile = djikstra.top();
        djikstra.pop();
        if (visited.at(mytile.y).at(mytile.x).contains(mytile.dir)
            && visited.at(mytile.y).at(mytile.x)[mytile.dir] <= mytile.dir_total)
        // if (visited.at(mytile.y).at(mytile.x).contains({mytile.dir, mytile.dir_total}))
            continue;
        visited.at(mytile.y).at(mytile.x)[mytile.dir] = mytile.dir_total;
        if (mytile.x == city.at(0).size() - 1 && mytile.y == city.size() - 1) {
            p(mytile.total_loss);
            break;
        }
        for (int dir = 1; dir <= 4; dir++) {
            if (dir == mytile.dir && mytile.dir_total == 3)
                continue;
            int x = mytile.x, y = mytile.y;
            if (dir == 1)
                x++;
            else if (dir == 2)
                y++;
            else if (dir == 3)
                x--;
            else if (dir == 4)
                y--;
            if (x < 0 || x >= city.at(0).size() || y < 0 || y >= city.size()
                || abs(dir - mytile.dir) == 2)
                continue;
            int dir_total = (mytile.dir_total * (int)(dir == mytile.dir)) + 1;
            if (!visited.at(y).at(x).contains(dir) || dir_total < visited.at(y).at(x)[dir])
                djikstra.push({
                    x, y,
                    mytile.total_loss + (city.at(y).at(x) - '0'),
                    dir,
                    dir_total,
                });
        }
    }
    // part 2
    while (djikstra.size())
        djikstra.pop();

    vector<vector<set<pair<int, int>>>> vis;
    for (int i = 0; i < city.size(); i++) {
        vis.push_back(vector<set<pair<int, int>>>());
        for (int j = 0; j < city.at(0).size(); j++)
            vis.at(i).push_back(set<pair<int, int>>());
    }

    djikstra.push({0, 0, 0, -2, 4});
    while (djikstra.size() > 0) {
        tile mytile = djikstra.top();
        djikstra.pop();
        // p(mytile.x)
        // p(mytile.y)
        // p(mytile.total_loss)
        // p(mytile.dir)
        // p(mytile.dir_total)
        if (vis.at(mytile.y).at(mytile.x).contains({mytile.dir, mytile.dir_total}))
            continue;
        vis.at(mytile.y).at(mytile.x).insert({mytile.dir, mytile.dir_total});
        if (mytile.x == city.at(0).size() - 1 && mytile.y == city.size() - 1 && mytile.dir_total >= 4) {
            p(mytile.total_loss);
            break;
        }
        for (int dir = 1; dir <= 4; dir++) {
            if (mytile.dir_total < 4 && dir != mytile.dir)
                continue;
            if (dir == mytile.dir && mytile.dir_total == 10)
                continue;
            int x = mytile.x, y = mytile.y;
            if (dir == 1)
                x++;
            else if (dir == 2)
                y++;
            else if (dir == 3)
                x--;
            else if (dir == 4)
                y--;
            if (x < 0 || x >= city.at(0).size() || y < 0 || y >= city.size()
                || abs(dir - mytile.dir) == 2)
                continue;
            int dir_total = (mytile.dir_total * (int)(dir == mytile.dir)) + 1;
            if (!vis.at(y).at(x).contains({dir, dir_total}))
                djikstra.push({
                    x, y,
                    mytile.total_loss + (city.at(y).at(x) - '0'),
                    dir,
                    dir_total,
                });
        }
    }

    // cout << res << endl;
    return 0;
}