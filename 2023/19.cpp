#include <iostream>
#include <sstream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

const string ACC = "A";
const string REJ = "R";

struct pattern {
    bool direct;
    char category;
    bool larger;
    int than;
    string gonext;
};

struct part {
    int x, m, a, s;
};

signed main() {
    string line;
    bool reading_wfs = true;

    map<string, vector<pattern>> workflows;
    vector<part> parts;

    while (getline(cin, line)) {
        if (line.size() == 0)
            reading_wfs = false;
        else if (reading_wfs) {
            int curly = line.find('{');
            string token = line.substr(0, curly);
            string patterns = line.substr(curly, line.find('}')-curly);

            vector<pattern> wf;
            int next_comma = 0;
            while (next_comma != string::npos) {
                // p(next_comma)
                int start = next_comma + 1;
                next_comma = patterns.find(',', start);
                if (next_comma != string::npos) {
                    char cat = patterns.at(start);
                    bool larger = patterns.at(start+1) == '>';
                    int colon = patterns.find(':', start);
                    int than = stoi(patterns.substr(start+2, colon));
                    string gonext = patterns.substr(colon+1, next_comma - (colon+1));
                    wf.push_back({ false, cat, larger, than, gonext });
                } else {
                    wf.push_back({ true, 'x', true, 0, patterns.substr(start)});
                }
            }
            workflows[token] = wf;
        } else {
            stringstream ss(line);
            char c;
            int x,m,a,s;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++)
                    ss >> c;
                if (i == 0)
                    ss >> x;
                else if (i == 1)
                    ss >> m;
                else if (i == 2)
                    ss >> a;
                else if (i == 3)
                    ss >> s;
            }
            parts.push_back({x,m,a,s});
        }
    }

    int res = 0;
    for (auto prt : parts) {
        string wf = "in";
        while (wf != ACC && wf != REJ) {
            for (auto pat : workflows[wf]) {
                int n = 0;
                if (pat.category == 'x')
                    n = prt.x;
                if (pat.category == 'm')
                    n = prt.m;
                if (pat.category == 'a')
                    n = prt.a;
                if (pat.category == 's')
                    n = prt.s;

                if (pat.direct || (pat.larger && n > pat.than) || (!pat.larger && n < pat.than)) {
                    wf = pat.gonext;
                    break;
                }
            }
        }
        if (wf == ACC) {
            res += prt.x + prt.m + prt.a + prt.s;
        }
    }
    cout << res << endl;
    return 0;
}