#include <iostream>
#include <sstream>
#include <map>
#include <queue>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

struct pulse {
    string from;
    string to;
    bool hi;
};

struct mod {
    bool broadcaster;
    bool is_flip;
    bool on; // flipflop
    map<string, bool> inputs;
    vector<string> outputs;
};

signed main() {
    string line;
    map<string, mod> modules;
    while (getline(cin, line)) {
        stringstream ss(line);
        string token, word;
        ss >> token;
        vector<string> outs;
        ss >> word; // ->
        while (ss >> word) {
            if (word.at(word.size() - 1) == ',')
                word = word.substr(0, word.size()-1);
            outs.push_back(word);
        }
        mod m;
        if (token.at(0) == '%') {
            m = {false, true, false, map<string, bool>(), outs};
            token = token.substr(1);
        } else if (token.at(0) == '&') {
            m = {false, false, false, map<string, bool>(), outs};
            token = token.substr(1);
        } else if (token.at(0) == 'b') {
            m = {true, false, false, map<string, bool>(), outs};
        }
        modules[token] = m;
    }
    for (auto kp : modules)
        for (auto out : kp.second.outputs)
            if (modules.find(out) != modules.end())
                modules[out].inputs[kp.first] = false;

    string state;
    vector<string> states;
    vector<pair<int, int>> lohis;
    while (states.size() < 1000000) {
        state = "state:";
        for ( auto m : modules ) {
            if (m.second.broadcaster)
                continue;
            if (m.second.is_flip)
                state += m.first + (m.second.on ? "1":"0");
            else {
                state += m.first + ";";
                for (auto in : m.second.inputs)
                    state += in.first + (in.second ? "1":"0");
            }
            state += ",";
        }
        if (find(states.begin(), states.end(), state) != states.end()) {
            break;
        }
        int los = 0;
        int his = 0;
        bool started_machine = false;
        queue<pulse> Q;
        Q.push({ "button", "broadcaster", 0 });
        while (!Q.empty()) {
            pulse p = Q.front();
            Q.pop();
            // cout << p.from << " -> " << p.to << "; " << p.hi << endl;
            if (p.to == "rx" && !p.hi) {
                started_machine = true;
            }
            his += p.hi;
            los += !p.hi;
            if (modules.find(p.to) == modules.end())
                continue;
            mod &m = modules[p.to];

            if (m.broadcaster) {
                for (auto out : m.outputs)
                    Q.push({ p.to, out, p.hi });
            } else if (m.is_flip) {
                if (p.hi)
                    continue;
                m.on = !m.on;
                for (auto out : m.outputs)
                    Q.push({ p.to, out, m.on });
            } else {
                m.inputs[p.from] = p.hi;
                bool all_hi = true;
                for (auto in : m.inputs)
                    if (!in.second) {
                        all_hi = false;
                        break;
                    }
                for (auto out : m.outputs)
                    Q.push({ p.to, out, !all_hi });

            }
        }
        // p(los)
        // p(his)
        states.push_back(state);
        lohis.push_back({los, his});
        if (started_machine)
            break;
    }
    // p_vec(states);
    p(states.size());

    int n = 1000, total_los = 0, total_his = 0;
    int cycles = n / states.size();
    int rest = n - cycles * states.size();
    for (int i = 0; i < states.size(); i++) {
        total_los += lohis.at(i).first * cycles;
        total_his += lohis.at(i).second * cycles;
        if (i < rest) {
            total_los += lohis.at(i).first;
            total_his += lohis.at(i).second;
        }
    }
    cout << total_los << ", " << total_his << "; " << total_los*total_his << endl;
    return 0;
}