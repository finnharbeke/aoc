#include <iostream>
#include <sstream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int sum = 0;
    int sum2 = 0;
    string line;
    while (getline(cin, line)) {
        int num;
        vector<vector<int> > values;
        values.push_back(vector<int>());
        stringstream ss(line);
        while (ss >> num)
            values.at(0).push_back(num);
        
        bool all_zeros = false;
        while (!all_zeros) {
            all_zeros = true;
            values.push_back(vector<int>());
            int j = values.size()-1;
            for (int i = 0; i < values.at(j-1).size() - 1; i++) {
                int diff = values.at(j-1).at(i+1) - values.at(j-1).at(i);
                values.at(j).push_back(diff);
                    if (diff != 0)
                        all_zeros = false;
            }
        }

        values.at(values.size()-1).push_back(0);
        values.at(values.size()-1).insert(values.at(values.size()-1).begin(), 0);

        for (int j = values.size() - 2; j >= 0; j--) {
            int next =
                values.at(j).at(values.at(j).size()-1)
                    +
                values.at(j+1).at(values.at(j+1).size()-1);
            int previous =
                values.at(j).at(0)
                    -
                values.at(j+1).at(0);
            values.at(j).push_back(next);
            values.at(j).insert(values.at(j).begin(), previous);
        }
        int pred = values.at(0).at(values.at(0).size()-1);
        sum += pred;
        int pred2 = values.at(0).at(0);
        sum2 += pred2;
        p(pred);
        p(pred2);
    }
    cout << sum << endl;
    cout << sum2 << endl;
    return 0;
}