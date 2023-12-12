#include <iostream>
#include <sstream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

int DP(string line) {
    int comma = line.find(",");
    while (comma != string::npos) {
        line = line.replace(comma, 1, " ");
        comma = line.find(",");
    }
    p(line)
    stringstream ss(line);
    string array;
    ss >> array;
    int num;
    int n_damaged = 0;
    vector<int> damaged;
    while (ss >> num) {
        damaged.push_back(num);
        n_damaged += num;
    }
    p(array)
    p(n_damaged)
    p_vec(damaged)

    vector<bool> must_be_dmgd;
    for (int j = 0, ix = 0, current = 0; j <= n_damaged; j++) {
        if (current == 0)
            must_be_dmgd.push_back(false);
        else if (current < damaged.at(ix))
            must_be_dmgd.push_back(true);
        else if (current == damaged.at(ix)) {
            must_be_dmgd.push_back(false);
            current = 0;
            ix++;
        }
        current++;
    }
    p_vec(must_be_dmgd);
    vector<vector<int>> dp;
    p_2dvec(dp);
    for (int len = 0; len <= array.size(); len++) {
        dp.push_back(vector<int>());
        for (int j = 0, dmg = 1; j <= n_damaged && j <= len;) {
            if (len == 0)
                dp.at(len).push_back(1 - dmg);
            else if (j == 0) {
                if (dmg == 1 || array.at(len-1)=='#')
                    dp.at(len).push_back(0);
                else 
                    dp.at(len).push_back(dp.at(len-1).at(j*2+1));
            } else {
                if (array.at(len-1) == '.') {
                    if (dmg || must_be_dmgd.at(j) || j == len)
                        dp.at(len).push_back(0);
                    else if (j < len)
                        dp.at(len).push_back(
                            dp.at(len-1).at(j*2) // last damaged, same amount
                            + dp.at(len-1).at(j*2+1) // last notdamaged, same amount
                        );
                } else if (array.at(len-1) == '#') {
                    if (!dmg || j == 0)
                        dp.at(len).push_back(0);
                    else {
                        if (!must_be_dmgd.at(j-1))
                            dp.at(len).push_back(dp.at(len-1).at((j-1)*2+1));
                        else
                            dp.at(len).push_back(dp.at(len-1).at((j-1)*2));
                    }
                        // dp.at(len).push_back(
                        //       dp.at(len-1).at((j-1)*2) * (must_be_dmgd.at(j)) // last damaged, one less
                        //     + dp.at(len-1).at((j-1)*2+1) * (!must_be_dmgd.at(j-1)) // last notdamaged, one less
                        // );
                } else if (array.at(len-1) == '?') {
                    if (must_be_dmgd.at(j)) {
                        if (!must_be_dmgd.at(j-1))
                            dp.at(len).push_back(
                                dp.at(len-1).at((j-1)*2+1) * (dmg)
                            );
                        else
                            dp.at(len).push_back(
                                dp.at(len-1).at((j-1)*2) * (dmg)
                            );
                    } else if (dmg)
                        dp.at(len).push_back(
                            dp.at(len-1).at((j-1)*2+1) * (!must_be_dmgd.at(j-1))
                            + dp.at(len-1).at((j-1)*2) * (must_be_dmgd.at(j-1))
                        );
                    else if (j < len) // only possible if space
                        dp.at(len).push_back(
                                dp.at(len-1).at(j*2)
                            + dp.at(len-1).at(j*2+1)
                        );
                    else 
                        dp.at(len).push_back(0);
                } else
                    p("nono")
            }


            if (dmg)
                dmg = 0;
            else {
                j++;
                dmg = 1;
            }
        }
    }
    // p_2dvec(dp);

    int ways = dp.at(array.size()).at(2*n_damaged) + dp.at(array.size()).at(2*n_damaged+1);
    p(ways)
    return ways;
}

signed main() {
    string line;
    int res = 0;
    int res2 = 0;
    while (getline(cin, line)) {
        int ways = DP(line);
        res += ways;
        string longline;
        stringstream l(line);
        string arr;
        string nums;
        l >> arr;
        l >> nums;
        longline = arr;
        for (int i = 0; i < 4; i++)
            longline += "?" + arr;
        longline += " " + nums;
        for (int i = 0; i < 4; i++)
            longline += "," + nums;
        p(longline)
        int moreways = DP(longline);
        res2 += moreways;
    }
    cout << res << endl;
    cout << res2 << endl;
    return 0;
}