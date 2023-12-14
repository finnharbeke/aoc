#include <iostream>
// #include <Eigen/Dense>
#include <Eigen/Sparse>
#define int int64_t
using namespace std;
// using namespace Eigen/Dense;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

vector<string> transpose(vector<string> other) {
    vector<string> res;
    for (int i = 0; i < other.at(0).size(); i++) {
        string col = "";
        for (int j = 0; j < other.size(); j++)
            col += other.at(j).at(i);
        res.push_back(col);
    }
    return res;
}

void climb(vector<string> &rows) {
    int h = rows.size();
    int w = rows.at(0).size();
    for (int r = 0; r < h; r++) {
        int last_cube = -1;
        int round = 0;
        for (int i = 0; i <= w; i++) {
            if (i == w || rows.at(r).at(i) == '#') {
                for (int j = last_cube + 1; j < i; j++) {
                    if (j - last_cube <= round) {
                        rows.at(r).at(j) = 'O';
                    } else
                        rows.at(r).at(j) = '.';
                }
                last_cube = i;
                round = 0;
            } else if (rows.at(r).at(i) == 'O') {
                round++;
            }
        }
    }
}

void fall(vector<string> &rows) {
    int h = rows.size();
    int w = rows.at(0).size();
    for (int r = 0; r < h; r++) {
        int last_cube = w;
        int round = 0;
        for (int i = w-1; i >= -1; i--) {
            if (i == -1 || rows.at(r).at(i) == '#') {
                for (int j = last_cube - 1; j > i; j--) {
                    if (last_cube - j <= round) {
                        rows.at(r).at(j) = 'O';
                    } else
                        rows.at(r).at(j) = '.';
                }
                last_cube = i;
                round = 0;
            } else if (rows.at(r).at(i) == 'O') {
                round++;
            }
        }
    }
}

int north_weight(vector<string> &cols) {
    int h = cols.at(0).size();
    int res = 0;
    for (auto col : cols)
        for (int i = 0; i < col.size(); i++)
            if (col.at(i) == 'O')
                res += h - i;
    return res;
}

signed main() {
    vector<string> rows;
    string line;
    while (cin >> line) {
        rows.push_back(line);
    }
    int w = rows.at(0).size();
    int h = rows.size();

    vector<string> cols = transpose(rows);
    climb(cols);
    p(north_weight(cols));

    map<string, int> history;
    bool skipped_time = false;
    int n_loops = 1e9;
    for (int i = 0; i < n_loops; i++) {
        p(i);
        string record = "";
        for (auto row : rows)
            record += row;
        if (true && !skipped_time && history.find(record) != history.end()) {
            int last = history[record];
            int loop = i - last;
            p(last);
            p(loop);
            for (auto row : rows)
                cout << row << endl;
            cout << endl;
            int skip = 0;
            while (i < n_loops - loop) {
                i += loop;
                skip++;
            }
            p(i);
            p(skip);
            skipped_time = true;
            i--;
            continue;
        } else if (!skipped_time) {
            history[record] = i;
        }
        cols = transpose(rows);
        climb(cols);
        rows = transpose(cols);
        climb(rows);
        cols = transpose(rows);
        fall(cols);
        rows = transpose(cols);
        fall(rows);
    }

    for (auto row : rows)
        cout << row << endl;
    cout << endl;

    cols = transpose(rows);
    p(north_weight(cols));

    // Eigen::MatrixXf m(w*h, w*h);

    // cout << m << endl;
    // for (int i = 0; i < h; i++) {
    //     for (int j = 0; j < w; j++) {
    //         if (rows.at(i).at(j) == '#')
    //             continue;
    //         int y = i, x = j;
    //         // north
    //         while (y > 0 && rows.at(y-1).at(x) != '#')
    //             y--;
    //         // west
    //         while (x > 0 && rows.at(y).at(x-1) != '#')
    //             x--;
    //         // south
    //         while (y < h-1 && rows.at(y+1).at(x) != '#')
    //             y++;
    //         // east
    //         while (x < w-1 && rows.at(y).at(x+1) != '#')
    //             x++;
    //         m(i*w+j, y*w+x) = 1;
    //     }
    // }
    // cout << m << endl;

    // vector<string> empty;
    // for (auto row : rows) {
    //     empty.push_back(row);
    //     for (int i = 0; i < w; i++)
    //         if (row.at(i) == 'O')
    //             empty.at(empty.size()-1).at(i) = '.';
    // }
    // for (auto row : rows)
    //     cout << row << endl;
    // cout << endl;

    // for (auto row : empty)
    //     cout << row << endl;
    // cout << endl;

    // vector<string> spun;
    // for (auto row : empty)
    //     spun.push_back(row);

    // for (int i = 0; i < h; i++) {
    //     for (int j = 0; j < w; j++) {
    //         if (rows.at(i).at(j) == 'O')
    //             for (int ix = 0; ix < w*h; ix++)
    //                 if (m(i*w+j, ix) == 1)
    //                     spun.at(ix/w).at(ix%w) = 'O';
    //     }
    // }

    // for (auto row : spun)
    //     cout << row << endl;
    // cout << endl;



    return 0;
}