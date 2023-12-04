#include <iostream>
#include <sstream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

signed main() {
    int point_sum = 0;
    int card = 1;
    int total_cards = 0;
    vector<int> copies;
    for (string line; getline(cin, line);) {
        if (copies.size() < card)
            copies.push_back(1); // original
        else
            copies.at(card-1)++; // original
        stringstream ss(line);
        string word;
        ss >> word; // card
        ss >> word; // card nr:
        vector<int> winners;
        vector<int> mine;
        bool reading_winners = true;
        while (ss >> word) {
            if (word == "|")
                reading_winners = false;
            else if (reading_winners)
                winners.push_back(stoi(word));
            else
                mine.push_back(stoi(word));
        }
        int points = 0;
        int matches = 0;
        for (int my_num : mine) {
            if (find(winners.begin(), winners.end(), my_num) != winners.end()) {
                if (points > 0)
                    points *= 2;
                else
                    points = 1;
                matches++;
            }
        }
        for (int next_card = card + 1; next_card <= card + matches; next_card++) {
            if (copies.size() < next_card)
                copies.push_back(copies.at(card-1)); // copies
            else
                copies.at(next_card-1) += copies.at(card-1); // copies
        }
        p(points);
        p(copies.at(card-1));
        point_sum += points;
        total_cards += copies.at(card-1);
        card++;
    }
    cout << point_sum << endl;
    cout << total_cards << endl;
    return 0;
}