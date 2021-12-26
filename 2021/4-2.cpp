#include <iostream>
#include <vector>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {

    vector<int> draws;
    int n;
    char c;
    while (cin >> n) {
        draws.push_back(n);
        cin >> c;
        if (c != ',')
            break;
    }

    p_vec(draws)

    vector<vector<vector<int> > > boards;
    vector<vector<vector<bool> > > bingo;
    vector<bool> won;
    int i = 0;
    while (cin >> n) {
        int b = (int)(i / 25);
        int r = (int)((i % 25) / 5);
        if (i % 25 == 0) {
            boards.push_back(vector<vector<int> >());
            bingo.push_back(vector<vector<bool> >());
            won.push_back(false);
        }
        if (i % 5 == 0) {
            boards.back().push_back(vector<int>());
            bingo.back().push_back(vector<bool>());
        }
        boards.back().back().push_back(n);
        bingo.back().back().push_back(false);

        i++;
    }

    for (int x : draws) {
        int bi = 0;
        for (auto b : boards) {
            int ri = 0;
            for (auto row : b) {
                for (i = 0; i < row.size(); i++) {
                    if (row[i] == x)
                        bingo.at(bi).at(ri).at(i) = true;
                }
                ri ++;
            }
            bi++;
        }

        vector<vector<int > > winner;
        vector<vector<bool > > winnerB;
        bool found = false;

        bi = 0;
        for (auto b : bingo) {
            bool bingo = false;
            for (auto row : b) {
                bingo = true;
                for (auto v : row) {
                    bingo = v & bingo;
                }
                if (bingo) {
                    won.at(bi) = true;
                    bool all = true;
                    for (auto w:won)
                        all = all & w;
                    if (all) {
                        winnerB = b;
                        winner = boards.at(bi);
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
            for (int col = 0; col < 5; col++) {
                bingo = true;
                for (int row = 0; row < 5; row++) {
                    bingo = bingo & b.at(row).at(col);
                }
                if (bingo) {
                    won.at(bi) = true;
                    bool all = true;
                    for (auto w:won)
                        all = all & w;
                    if (all) {
                        winnerB = b;
                        winner = boards.at(bi);
                        found = true;
                        break;
                    }
                }
            }


            if (found)
                break;

            bi++;
        }

        if (found) {
            p_2dvec(winner)
            p_2dvec(winnerB)

            int score = 0;
            int ri = 0;
            for (auto row : winnerB) {
                i = 0;
                for (auto v : row) {
                    if (!v) score += winner.at(ri).at(i);
                    i++;
                }

                ri++;
            }

            p(score)
            p(x*score)

            break;
        }

    }

    p(boards.size())

    return 0;
}