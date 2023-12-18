#include <iostream>
#include <set>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

char add_turn(char last_dir, char dir, map<char, int> &turn_count) {
    int L = turn_count['L'];
    if (dir == '0') {
        if (last_dir == '1')
            turn_count['L']++;
        else if (last_dir == '3')
            turn_count['R']++;
    } else if (dir == '1') {
        if (last_dir == '0')
            turn_count['R']++;
        else if (last_dir == '2')
            turn_count['L']++;
    } else if (dir == '2') {
        if (last_dir == '1')
            turn_count['R']++;
        else if (last_dir == '3')
            turn_count['L']++;
    } else if (dir == '3') {
        if (last_dir == '0')
            turn_count['L']++;
        else if (last_dir == '2')
            turn_count['R']++;
    }
    if (turn_count['L'] > L)
        return 'L';
    return 'R';
}

signed main() {
    string word;
    int min_x = 0, max_x = 0;
    int min_y = 0, max_y = 0;
    int x = 0, y = 0;
    int x2 = 0, y2 = 0;
    set<pair<int, int>> dug;
    vector<pair<pair<pair<int, int>, pair<int, int>>, pair<bool, bool>>> rights;
    vector<pair<pair<pair<int, int>, pair<int, int>>, pair<bool, bool>>> lefts;
    map<char, int> turn_count;
    turn_count['L'] = 0;
    turn_count['R'] = 0;
    dug.insert({x, y});
    char last_dir = '4';
    char first_dir = '4';
    char dir;

    int dug_sum = 0;

    while (cin >> word) {
        dir = word.at(0);
        cin >> word;
        int steps = stoi(word);
        cin >> word;
        for (int i = 0; i < steps; i++) {
            if (dir == 'R')
                x++;
            if (dir == 'L')
                x--;
            if (dir == 'D')
                y++;
            if (dir == 'U')
                y--;
            dug.insert({x, y});
        }
        max_x = max(x, max_x);
        min_x = min(x, min_x);
        max_y = max(y, max_y);
        min_y = min(y, min_y);
        string color = word.substr(2, 7);
        int dist = stoi(color.substr(0, 5), nullptr, 16);
        dir = color.at(5);
        int n_x2 = x2, n_y2 = y2;
        if (dir == '0')
            n_x2 += dist;
        else if (dir == '1')
            n_y2 += dist;
        else if (dir == '2')
            n_x2 -= dist;
        else if (dir == '3')
            n_y2 -= dist;
        char turn = add_turn(last_dir, dir, turn_count);
        dug_sum += dist;
        // if (!dir_count.contains(dir))
        //     dir_count[dir] = 1;
        // else
        //     dir_count[dir]++;

        if (last_dir == '2') {
            lefts.at(lefts.size() - 1).second.first = (turn == 'R');
        } else if (last_dir == '0') {
            rights.at(rights.size() - 1).second.second = (turn == 'R');
        }

        if (first_dir == '4')
            first_dir = dir;
        last_dir = dir;
        if (dir == '0')
            rights.push_back({{{x2, y2}, {n_x2, n_y2}}, {turn == 'R', false}});
        if (dir == '2') {
            lefts.push_back({{{x2, y2}, {n_x2, n_y2}}, {false, turn == 'R'}});
        }
        x2 = n_x2;
        y2 = n_y2;
    }
    char turn = add_turn(dir, first_dir, turn_count);
    if (first_dir == '2') {
        lefts.at(0).second.second = (turn == 'R');
    } else if (first_dir == '0') {
        rights.at(0).second.first = (turn == 'R');
    }
    p(min_x)
    p(max_x)
    p(min_y)
    p(max_y)
    for (auto turn : turn_count) {
        p(turn.first)
        p(turn.second)
    }
    // conclusion: we're going right way round (clockwise)

    int edges = dug.size();

    set<pair<int, int>> visited;
    for (int i = min_y; i <= max_y; i++) {
        for (int j = min_x; j <= max_x; j++) {
            if (visited.contains({j, i}) || dug.contains({j, i}))
                continue;
            set<pair<int, int>> this_run;
            vector<pair<int, int>> stack;
            stack.push_back({j,i});
            bool free = false;
            while (!stack.empty()) {
                pair<int, int> xy = stack.at(stack.size() - 1);
                stack.pop_back();
                if (this_run.contains(xy))
                    continue;
                this_run.insert(xy);
                visited.insert(xy);
                x = xy.first;
                y = xy.second;
                // p(x)
                // p(y)
                if (x <= min_x || x >= max_x || y <= min_y || y >= max_y)
                    free = true;
                for (int ay = -1; ay <= 1; ay++)
                    for (int ax = -1; ax <= 1; ax++) {
                        if (x + ax < min_x || x + ax > max_x || y + ay < min_y || y + ay > max_y)
                            continue;
                        if (abs(ax + ay) != 1)
                            continue;
                        if (dug.contains({x+ax, y+ay}) || this_run.contains({x+ax, y+ay}) || visited.contains({x+ax, y+ay}))
                            continue;
                        stack.push_back({ x+ax, y+ay });
                    }
            }
            // p(j)
            // p(i)
            // p(this_run.size())
            // p(free)
            for (auto pos : this_run) {
                if (!free)
                    dug.insert(pos);
            }
        }
    }

    cout << dug.size() << endl;

    auto cmp = [](pair<pair<pair<int, int>, pair<int, int>>, pair<bool, bool>> a, pair<pair<pair<int, int>, pair<int, int>>, pair<bool, bool>> b) {
        return a.first.first.second < b.first.first.second;
    };

    sort(rights.begin(), rights.end(), cmp);
    sort(lefts.begin(), lefts.end(), cmp);
    p("rights")
    for (auto r : rights) {
        p(r.first.first.first)
        p(r.first.second.first)
        p(r.first.first.second)
        p(r.second.first)
        p(r.second.second)
    }
    p("lefts")
    for (auto l : lefts) {
        p(l.first.first.first)
        p(l.first.second.first)
        p(l.first.first.second)
        p(l.second.first)
        p(l.second.second)
    }

    p(dug_sum);

    for (int i = 0; i < rights.size(); i++) {
        auto top = rights.at(i);

        int left = top.first.first.first+top.second.first;
        int right = top.first.second.first-top.second.second;
        int top_y = top.first.first.second+1;

        vector<pair<int, int>> handled;

        for (int j = 0; j < lefts.size(); j++) {
            auto bot = lefts.at(j);
            int bot_y = bot.first.first.second-1;
            if (bot_y < top_y)
                continue;
            int b_l = max(bot.first.second.first+(bot.second.first), left);
            int b_r = min(bot.first.first.first-(bot.second.second), right);
            vector<pair<int, int>> to_be_handled;
            to_be_handled.push_back({b_l, b_r});
            int ix = 0;
            while (ix < to_be_handled.size()) {
                auto lr = to_be_handled.at(to_be_handled.size() - 1);
                to_be_handled.pop_back();
                bool did_smth = false;
                for (auto done : handled) {
                    if (done.first > lr.second || done.second < lr.first)
                        continue;
                    if (!(done.first <= lr.first && lr.second <= done.second)) {
                        if (lr.first < done.first)
                            to_be_handled.push_back({lr.first, done.first - 1});
                        if (done.second < lr.second)
                            to_be_handled.push_back({done.second + 1, lr.second});
                    }
                    did_smth = true;
                    break;
                }
                if (!did_smth) {
                    ix++;
                    to_be_handled.push_back(lr);
                }
            }
            // p(to_be_handled.size())
            // p(i)
            // p(j)
            // p(top_y)
            // p(bot_y)
            for (auto lr : to_be_handled) {
                // p(lr.first)
                // p(lr.second)
                if (lr.first > lr.second)
                    continue;
                handled.push_back(lr);
                int add = (lr.second - lr.first + 1) * (bot_y - top_y + 1);
                // p(add)
                dug_sum += add;
            }
        }
    }

    cout << dug_sum << endl;
    return 0;
}


// R 6 (#70c710)
// D 5 (#0dc571)
// L 2 (#5713f0)
// D 2 (#d2c081)
// R 2 (#59c680)
// D 2 (#411b91)
// L 5 (#8ceee2)
// U 2 (#caa173)
// L 1 (#1b58a2)
// U 2 (#caa171)
// R 2 (#7807d2)
// U 3 (#a77fa3)
// L 2 (#015232)
// U 2 (#7a21e3)