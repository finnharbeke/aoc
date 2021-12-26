#include <iostream>
#include <string>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {

    int d = 0;
    int x = 0;
    int  a = 0;
    string cmd;
    while (cin >> cmd) {
        int n;
        cin >> n;
        if (cmd.at(0) == 'f') {
            d += a * n;
            x += n;
        } else if (cmd.at(0) == 'd') {
            a += n;
        } else {
            a -= n;
        }
    }

    p(d)p(x)
    p(d*x)

    return 0;
}