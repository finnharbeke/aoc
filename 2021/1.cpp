#include <iostream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int last3 = -1;
    int last2 = -1;
    int last = -1;
    int n = -1;
    int c = 0;
    for (int i = 0; i  < 2000; i ++) {
        cin >> n;
        if (i >= 3 && n + last + last2 > last + last2 + last3) {
            //p(n)p(last3)
            c ++;
        }
        last3 = last2;
        last2 = last;
        last = n;
    }
    cout << c << endl;
    return 0;
}