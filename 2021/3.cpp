#include <iostream>
#include <string>
#include <math.h>
#include <unordered_set>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int n = 12;
    int z[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int o[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    string line;
    unordered_set<string> o2;
    unordered_set<string> co2;
    unordered_set<string> all;
    while(cin >> line)  {
        o2.insert(line);
        co2.insert(line);
        all.insert(line);
        if (line.at(0) == 'q')
            break;
        //p(line)
        for (int i = 0; i < n; i++) {
        if (line.at(i) == '1')
            o[i]++;
        else
            z[i]++;
        }
    }

    p_vec(z)
    p_vec(o)

    int i = 0;
    while (o2.size() > 1) {
        int o = 0;
        int z = 0;
        for (std::unordered_set<string>::iterator itr = o2.begin(); itr != o2.end(); ++itr) {
            if ((*itr).at(i) == '1')
                o++;
            else
                z++;
        }
        for (std::unordered_set<string>::iterator itr = all.begin(); itr != all.end(); ++itr) {
            if (((*itr).at(i) == '1' && o < z) or (o >= z && (*itr).at(i) == '0'))
                o2.erase(*itr);
        }

        i++;
    }
    i = 0;
    while (co2.size() > 1) {
        int o = 0;
        int z = 0;
        for (std::unordered_set<string>::iterator itr = co2.begin(); itr != co2.end(); ++itr) {
            if ((*itr).at(i) == '1')
                o++;
            else
                z++;
        }
        for (std::unordered_set<string>::iterator itr = all.begin(); itr != all.end(); ++itr) {
            if (((*itr).at(i) == '1' && o >= z) or (o < z && (*itr).at(i) == '0'))
                co2.erase(*itr);
        }

        i++;
    }
    p(*o2.begin())
    p(*co2.begin())

    int g = 0;
    int e = 0;
    int o2r = 0;
    int co2r = 0;
    for (int i = 0; i < n; i++) {
        if ((*o2.begin()).at(i) == '1')
            o2r += pow(2, n-i-1);
        if ((*co2.begin()).at(i) == '1')
            co2r += pow(2, n-i-1);
        if (o[i] > z[i])
            g += pow(2, n-i-1);
        else
            e += pow(2, n-i-1);
    }

    p(e)
    p(g)
    p(e*g)
    p(o2r)
    p(co2r)
    p(o2r*co2r)

    return 0;
}