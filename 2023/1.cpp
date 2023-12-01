#include <iostream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int sum1 = 0;
    int sum2 = 0;
    int iter = 0;
    string line = "first";
    string last_line = "firstfirst";
    while (line != last_line) {
        char c;
        if (iter > 0) {
            int left1 = -1;
            int right1;
            int left2 = -1;
            int right2;
            for (int i = 0; i < line.size(); i++) {
                c = line.at(i);
                int num = -1;
                if (line.size() - i >= 3) {
                    string next3 = line.substr(i, 3);
                    if (next3 == "one")
                        num = 1;
                    else if (next3 == "two")
                        num = 2;
                    else if (next3 == "six")
                        num = 6;
                }
                if (line.size() - i >= 4) {
                    string next3 = line.substr(i, 4);
                    if (next3 == "four")
                        num = 4;
                    else if (next3 == "five")
                        num = 5;
                    else if (next3 == "nine")
                        num = 9;
                }
                if (line.size() - i >= 5) {
                    string next3 = line.substr(i, 5);
                    if (next3 == "three")
                        num = 3;
                    else if (next3 == "seven")
                        num = 7;
                    else if (next3 == "eight")
                        num = 8;
                }
                if (num >= 0) {
                    if (left2 == -1)
                        left2 = num;
                    right2 = num;
                }

                if (c - '0' < 10) {
                    if (left1 == -1)
                        left1 = c - '0';
                    right1 = c - '0';
                    if (left2 == -1)
                        left2 = c - '0';
                    right2 = c - '0';
                }
            }
            p(left1)
            p(right1)
            p(left2)
            p(right2)
            sum1 += left1 * 10 + right1;
            sum2 += left2 * 10 + right2;
            p(sum1)
            p(sum2)
        }
        iter++;
        last_line = line;
        cin >> line;
        p(line)
        if (iter > 2000)
            break;
    }

    p(sum1);
    p(sum2);
    return 0;
}