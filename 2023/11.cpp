#include <iostream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

void d_sum_by_age(vector<bool> row_is_empty, vector<bool> col_is_empty, vector<pair<int, int> > galaxies, int age) {
    int d_sum = 0;
    for (int i = 0; i < galaxies.size() - 1; i++) {
        for (int j = i+1; j < galaxies.size(); j++) {
            pair<int, int> g1 = galaxies.at(i);
            pair<int, int> g2 = galaxies.at(j);
            // d_sum += abs(g1.first - g2.first) + abs(g1.second - g2.second);
            int d = 0;
            for (int x = min(g1.second, g2.second) + 1; x <= max(g1.second, g2.second); x++) {
                if (col_is_empty.at(x))
                    d += age;
                d++;
            }
            for (int y = min(g1.first, g2.first) + 1; y <= max(g1.first, g2.first); y++) {
                if (row_is_empty.at(y))
                    d += age;
                d++;
            }
            d_sum += d;
        }
    }

    cout << d_sum << endl;
}

signed main() {
    vector<string> map;
    string line;
    vector<bool> row_is_empty;
    while (cin >> line) {
        map.push_back(line);
        bool alldots = true;
        for (auto c : line)
            if (c != '.')
                alldots = false;
        row_is_empty.push_back(alldots);
        // if (alldots)
        //     map.push_back(line);
    }
    vector<bool> col_is_empty;
    for (int j = 0; j < map.at(0).size(); j++) {
        bool alldots = true;
        for (int i = 0; i < map.size(); i++) {
            if (map.at(i).at(j) != '.')
                alldots = false;
        }
        col_is_empty.push_back(alldots);
        // if (alldots) {
        //     for (int i = 0; i < map.size(); i++)
        //         map.at(i).insert(j, ".");
        //     j++;
        // }
    }

    if (OO) {
        for (string line : map)
            cout << line << endl;
    }

    vector<pair<int, int>> galaxies;
    for (int y = 0; y < map.size(); y++) {
        for (int x = 0; x < map.at(0).size(); x++) {
            if (map.at(y).at(x) == '#')
                galaxies.push_back(pair<int, int>{y, x});
        }
    }

    d_sum_by_age(row_is_empty, col_is_empty, galaxies, 1);
    d_sum_by_age(row_is_empty, col_is_empty, galaxies, 999999);
    
    return 0;
}