#include <iostream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

bool compare(pair<int, int> q_part1, pair<int, int> q_part2) {
    return q_part1.first < q_part2.first;
}

int gcd(int a, int b) {
  int R;
  while ((a % b) > 0) {
    R = a % b;
    a = b;
    b = R;
  }
  return b;
}

signed main() {
    string instrs;
    cin >> instrs;
    string source;
    string equals;
    string left;
    string right;

    map<string, pair<string, string> > camelmap;
    vector<string> ghosts;
    while (cin >> source) {
        cin >> equals;
        cin >> left;
        left = left.substr(1,3);
        cin >> right;
        right = right.substr(0,3);
        camelmap[source] = pair<string, string>(left, right);
        if (source.at(2) == 'A')
            ghosts.push_back(source);
    }

    string pos = "AAA";
    int step = 0;
    while (pos != "ZZZ") {
        if (instrs.at(step % instrs.size()) == 'L')
            pos = camelmap.at(pos).first;
        else
            pos = camelmap.at(pos).second;
        step++;
    }
    cout << step << endl;
    step = 0;
    bool allZ = false;
    while (!allZ) {
        if (instrs.at(step % instrs.size()) == 'L')
            for (int i = 0; i < ghosts.size(); i++)
                ghosts.at(i) = camelmap.at(ghosts.at(i)).first;
        else
            for (int i = 0; i < ghosts.size(); i++)
                ghosts.at(i) = camelmap.at(ghosts.at(i)).second;
        allZ = true;
        for (int i = 0; i < ghosts.size(); i++)
            if (ghosts.at(i).at(2) != 'Z')
                allZ = false;
        
        step++;
    }
    cout << step << endl;
    vector<int> cycle_lens;
    vector<pair<int, int>> queue;
    for (int g = 0; g < ghosts.size(); g++) {
        step = 0;
        vector<pair<string, int>> path;
        string current = ghosts.at(g);
        bool been_there = false;
        while (!been_there) {
            for (auto before : path)
                if (before.first == current && before.second == step % instrs.size())
                    been_there = true;
            path.push_back(pair<string, int>{current, step % instrs.size()});
            if (instrs.at(step % instrs.size()) == 'L')
                current = camelmap.at(current).first;
            else
                current = camelmap.at(current).second;
            step++;
        }
        if (OO && false) {
            for (auto step : path)
                cout << step.first << ";" << step.second << " ";
            cout << endl;
        }

        
        int cycle_enter = find(path.begin(), path.end(), path.at(path.size()-1)) - path.begin();
        int cycle_len = path.size() - 1 - cycle_enter;
        cycle_lens.push_back(cycle_len);
        for (int i = cycle_enter; i < path.size() - 1; i++) {
            if (path.at(i).first.at(2) == 'Z')
                queue.push_back(pair<int,int>{i, g});
        }
        p(cycle_enter)
        p(cycle_len)
        // int cycle_len = path.size() - 
    }

    // bool same_step = false;
    // sort(queue.begin(), queue.end(), compare);
    // int round = 0;
    // step = queue.at(0).first;
    // while (!same_step) {
    //     if (round % 1000 == 0) {
    //         for (auto thing : queue)
    //             cout << thing.first << "(" << thing.second << ") ";
    //         cout << endl;
    //     }
    //     pair<int, int> next = queue.at(0);
    //     queue.erase(queue.begin());
    //     queue.push_back(pair<int, int>{ step + cycle_lens.at(next.second), next.second });
    //     sort(queue.begin(), queue.end(), compare);
    //     same_step = true;
    //     step = queue.at(0).first;
    //     for (int i = 0; i < ghosts.size() && same_step; i++) {
    //         bool found = false;
    //         for (int j = 0; j < queue.size(); j++)
    //             if (queue.at(j).second == i && queue.at(j).first == step)
    //                 found = true;
    //         if (!found)
    //             same_step = false;
    //     }
    //     round++;
    // }
    // cout << step << endl;

    // this is so stupid, lcm of cycle_lens works
    int n1 = cycle_lens.at(0);
    int n2;
    int ix = 1;
    int lcm = n1;
    while (ix < cycle_lens.size()) {
        n1 = lcm;
        n2 = cycle_lens.at(ix);
        ix++;
        int g = gcd(n1, n2);
        lcm = (int)(n1*n2/g);
        p(lcm);
    }
    return 0;
}