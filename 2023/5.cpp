#include <iostream>
#include <sstream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

struct seedrange {
    int start;
    int len;
};

signed main() {
    bool first = true;
    vector<vector<int> > seeds;
    vector<vector<seedrange> > seedranges;
    for (string line; getline(cin, line);) {
        stringstream ss(line);
        int seed;
        int lastseed = -1;
        string word;
        p(line);
        if (first) {
            ss >> word; // "seeds:"
            seeds.push_back(vector<int>());
            seedranges.push_back(vector<seedrange>());
            bool odd = true;
            while (ss >> seed) {
                p(seed);
                p(odd);
                seeds.at(0).push_back(seed);
                if (!odd && lastseed > 0) {
                    p(lastseed);
                    p(seed);
                    seedranges.at(0).push_back((struct seedrange){ lastseed, seed });
                }
                odd = !odd;
                lastseed = seed;
            }
            first = false;
            getline(cin, line); // blank
            continue;
        }
        struct range
        {
            int src_start;
            int dst_start;
            int len;
        };
        vector<range> map;
        while(getline(cin, line) && line.size() > 0) {
            stringstream nums(line);
            int dst_start;
            nums >> dst_start;
            int src_start;
            nums >> src_start;
            int len;
            nums >> len;
            map.push_back((struct range){src_start, dst_start, len});
        }
        for (range rng : map) {
            p(rng.src_start);
            p(rng.dst_start);
            p(rng.len);
        }
        seeds.push_back(vector<int>());
        for (int last : seeds.at(seeds.size() - 2)) {
            bool mapped = false;
            for (range rng : map) {
                if (last >= rng.src_start && last < rng.src_start + rng.len) {
                    mapped = true;
                    seeds.at(seeds.size() -1).push_back(rng.dst_start + (last - rng.src_start));
                    break;
                }
            }
            if (!mapped)
                seeds.at(seeds.size() -1).push_back(last);
        }
        seedranges.push_back(vector<seedrange>());
        for (int i = 0; i < seedranges.at(seedranges.size() - 2).size(); i++) {
            bool mapped = false;
            seedrange last = seedranges.at(seedranges.size() - 2).at(i);
            for (range rng : map) {
                if (rng.src_start + rng.len <= last.start || last.start + last.len <= rng.src_start)
                    continue; // no match
                if (last.start >= rng.src_start && last.start + last.len <= rng.src_start + rng.len) // enclosed
                    seedranges.at(seedranges.size() - 1).push_back(
                        (struct seedrange){ rng.dst_start + (last.start - rng.src_start), last.len }
                    );
                else if (last.start < rng.src_start && last.start + last.len > rng.src_start + rng.len) {// encloses
                    seedranges.at(seedranges.size() - 1).push_back(
                        (struct seedrange){ rng.dst_start, rng.len } // middle
                    );
                    seedranges.at(seedranges.size() - 2).push_back(
                        (struct seedrange){ last.start, rng.src_start - last.start } // left exceedings
                    );
                    seedranges.at(seedranges.size() - 2).push_back(
                        (struct seedrange){ rng.src_start + rng.len, last.len - ((rng.src_start + rng.len) - last.start) } // right exceedings
                    );
                }
                else if (last.start < rng.src_start) { // right overlap
                    seedranges.at(seedranges.size() - 1).push_back(
                        (struct seedrange){ rng.dst_start, last.start + last.len - rng.src_start } // overlap
                    );
                    seedranges.at(seedranges.size() - 2).push_back(
                        (struct seedrange){ last.start, rng.src_start - last.start } // left exceedings
                    );
                }
                else if (last.start < rng.src_start) { // right overlap
                    seedranges.at(seedranges.size() - 1).push_back(
                        (struct seedrange){ rng.dst_start + (last.start - rng.src_start), rng.len - (last.start - rng.src_start) } // overlap
                    );
                    seedranges.at(seedranges.size() - 2).push_back(
                        (struct seedrange){ rng.src_start + rng.len, last.len - ((rng.src_start + rng.len) - last.start) } // right overlap
                    );
                }
                mapped = true;
            }
            if (!mapped)
                seedranges.at(seedranges.size() - 1).push_back(last);
        }
    }
    p_2dvec(seeds);
    for (auto gen : seedranges) {
        for (seedrange rng : gen) {
            cout << rng.start << "(" << rng.len << "), ";
        }
        cout << endl;
    }
    int min = seeds.at(seeds.size() - 1).at(0);
    for (int loc : seeds.at(seeds.size() - 1)) {
        if (loc < min)
            min = loc;
    }
    p(min);
    min = seedranges.at(seedranges.size() - 1).at(0).start;
    for (seedrange rng : seedranges.at(seedranges.size() - 1)) {
        if (rng.start < min)
            min = rng.start;
    }
    p(min);
    return 0;
}