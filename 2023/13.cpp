#include <iostream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

int mirror(vector<string> rows, int original = 0) {
    vector<string> cols;
    for (int i = 0; i < rows.at(0).size(); i++) {
        string col = "";
        for (int j = 0; j < rows.size(); j++)
            col += rows.at(j).at(i);
        cols.push_back(col);
    }
    // vertical mirror
    for (int start = 0; start < cols.size() - 1; start++) {
        bool reflection = true;
        for (int left = start, right = start+1; left >= 0 && right < cols.size(); left--, right++) {
            if (cols.at(left) != cols.at(right)) {
                reflection = false;
                break;
            }
        }
        if (reflection && start + 1 != original)
            return start + 1;
    }
    // horizontal mirror
    for (int start = 0; start < rows.size() - 1; start++) {
        bool reflection = true;
        for (int top = start, bot = start+1; top >= 0 && bot < rows.size(); top--, bot++) {
            if (rows.at(top) != rows.at(bot)) {
                reflection = false;
                break;
            }
        }
        if (reflection && (start + 1) * 100 != original)
            return (start + 1) * 100;
    }

    return 0;
}

int smudge(vector<string> rows) {
    int original = mirror(rows);
    for (int i = 0; i < rows.size(); i++) {
        for (int j = 0; j < rows.at(i).size(); j++) {
            if (rows.at(i).at(j) == '.')
                rows.at(i).at(j) = '#';
            else
                rows.at(i).at(j) = '.';
            int mir = mirror(rows, original);
            if (mir != 0 && mir != original)
                return mir;
            if (rows.at(i).at(j) == '#')
                rows.at(i).at(j) = '.';
            else
                rows.at(i).at(j) = '#';
        }
    }
    return 0;
}

signed main() {
    vector<string> rows;
    string line;
    int res = 0;
    int res2 = 0;
    while (getline(cin, line)) {
        if (line == "") {
            res += mirror(rows);
            res2 += smudge(rows);
            // p(res2)
            rows = vector<string>();
        } else
            rows.push_back(line);
    }
    res += mirror(rows);
    res2 += smudge(rows);

    cout << res << endl;
    cout << res2 << endl;
    return 0;
}