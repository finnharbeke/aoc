#include <iostream>
#include <sstream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int max_r = 12, max_g = 13, max_b = 14;
    int id_sum = 0;
    int power_sum = 0;
    int game = 1;
    for (string line; getline(cin, line);) {
        stringstream ss(line);
        string word;
        ss >> word; // game
        ss >> word; // id
        int high_r = 0, high_g = 0, high_b = 0;
        while(ss >> word) {
            /* do stuff with word */
            int n = stoi(word);
            ss >> word;
            if (word.rfind("red", 0) == 0)
                high_r = max(high_r, n);
            else if (word.rfind("green", 0) == 0)
                high_g = max(high_g, n);
            else if (word.rfind("blue", 0) == 0)
                high_b = max(high_b, n);
        }
        if (high_r <= max_r && high_g <= max_g && high_b <= max_b)
            id_sum += game;
        int power = high_r * high_g * high_b;
        power_sum += power;
        game++;
    }


    cout << id_sum << endl;
    cout << power_sum << endl;
    return 0;
}