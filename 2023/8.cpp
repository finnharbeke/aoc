#include <iostream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

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
    // while (pos != "ZZZ") {
    //     if (instrs.at(step % instrs.size()) == 'L')
    //         pos = camelmap.at(pos).first;
    //     else
    //         pos = camelmap.at(pos).second;
    //     step++;
    // }
    // cout << step << endl;
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
    return 0;
}