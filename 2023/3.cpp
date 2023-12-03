#include <iostream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { std::cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { std::cout << #x << ": "; for (auto val: x) { std::cout << val << " "; }; std::cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    vector<string> plan;
    map<pair<int, int>, vector<int> > gears;
    for (string line; getline(cin, line);) {
        plan.push_back(line);
    }
    p(plan.size())
    int part_sum = 0;
    for (int i = 0; i < plan.size(); i++) {
        //p(i)
        for (int j = 0; j < plan.at(i).size(); j++) {
            if (plan.at(i).at(j) - '0' < 10 && plan.at(i).at(j) - '0' >= 0) {
                int start = j;
                while (j < plan.at(i).size() && plan.at(i).at(j) - '0' < 10 && plan.at(i).at(j) - '0' >= 0)
                    j++;
                //p(plan.at(i).substr(start, j - start));
                int num = stoi(plan.at(i).substr(start, j - start));
                p(start)
                p(j)
                bool free = true;
                if (start > 0 && plan.at(i).at(start-1) != '.') {
                    if (plan.at(i).at(start-1) == '*') {
                        pair<int, int> loc(i, start-1); 
                        if (gears.find(loc) == gears.end())
                            gears[loc] = vector<int>();
                        gears[loc].push_back(num);
                    }
                    free = false;
                }
                else if (j < plan.at(i).size() && plan.at(i).at(j) != '.') {
                    if (plan.at(i).at(j) == '*') {
                        pair<int, int> loc(i, j); 
                        if (gears.find(loc) == gears.end())
                            gears[loc] = vector<int>();
                        gears[loc].push_back(num);
                    }
                    p(i);p(j);
                    free = false;
                }
                else if (i > 0) {// top row
                    for (int k = max(start-1, (int)0); k < min((int)plan.at(i-1).size(), j+1); k++)
                        if (plan.at(i-1).at(k) != '.') {
                            if (plan.at(i-1).at(k) == '*') {
                                pair<int, int> loc(i-1, k); 
                                if (gears.find(loc) == gears.end())
                                    gears[loc] = vector<int>();
                                gears[loc].push_back(num);
                            }
                            free = false;
                            p(i+1)
                            p(k)
                            break;
                        }
                }
                if (i < plan.size() - 1 && free) {
                    for (int k = max(start-1, (int)0); k < min((int)plan.at(i+1).size(), j+1); k++)
                        if (plan.at(i+1).at(k) != '.') {
                            if (plan.at(i+1).at(k) == '*') {
                                pair<int, int> loc(i+1, k); 
                                if (gears.find(loc) == gears.end())
                                    gears[loc] = vector<int>();
                                gears[loc].push_back(num);
                            }
                            free = false;
                            p(i+1)
                            p(k)
                            break;
                        }
                }
                if (!free) {
                    p(num)
                    part_sum += num;
                } else {
                    p("nono")
                    p(num)
                }
            }
        }
    }
    int gearsum = 0;
    for (map<pair<int, int>, vector<int> >::iterator i = gears.begin(); i != gears.end(); i++) {
        if (i->second.size() == 2) {
            p_vec(i->second)
            gearsum += i->second.at(0) * i->second.at(1);
        }
    }
    std::cout << part_sum << endl;
    std::cout << gearsum << endl;
    return 0;
}