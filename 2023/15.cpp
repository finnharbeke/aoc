#include <iostream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    string input;
    cin >> input;
    int res = 0;
    int current = 0;
    for (auto c : input) {
        if (c == ',') {
            res += current;
            current = 0;
        } else {
            current += (int)c;
            current *= 17;
            current %= 256;
            // p(current);
        }
    }
    res += current;
    cout << res << endl;

    map<int, vector<pair<string, int>>> boxes;
    current = 0;
    string label = "";
    for (auto c : input) {
        if (c == '=')
            continue; // handle with focal len
        else if (0 <= c - '0' && c - '0' < 10) {
            p(current)
            p(c)
            int focal_len = c - '0';
            if (boxes.find(current) == boxes.end()) {
                boxes[current] = vector<pair<string, int>>();
            }
            bool found = false;
            for (int i = 0; i < boxes[current].size(); i++)
                if (boxes[current].at(i).first == label) {
                    found = true;
                    boxes[current].at(i).second = focal_len;
                }
            if (!found)
                boxes[current].push_back(pair<string, int>{ label, focal_len });
        } else if (c == '-') {
            p(current)
            p(c)
            if (boxes.find(current) != boxes.end())
                for (int i = 0; i < boxes[current].size(); i++)
                    if (boxes[current].at(i).first == label)
                        boxes[current].erase(next(boxes[current].begin(), i));
        } else if (c == ',') {
            current = 0;
            label = "";
        } else {
            current += (int)c;
            current *= 17;
            current %= 256;
            label += c;
        }
    }
    res = 0;
    for (auto const& keyval : boxes) {
        int box = keyval.first;
        vector<pair<string, int>> lenses = keyval.second;
        p(box)
        // p_vec(lenses);
        for (int i = 0; i < lenses.size(); i++) {
            int prod = (box + 1) * (i+1) * lenses.at(i).second;
            p(prod);
            res += prod;
        }
    }
    cout << res << endl;
    return 0;
}