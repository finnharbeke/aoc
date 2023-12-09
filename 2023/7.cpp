#include <iostream>
#include <map>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

struct hand {
    string cards;
    int bid;
};

int type(hand myhand) {
    map<char, int> counts;
    for (char card : myhand.cards) {
        if (counts.find(card) != counts.end())
            counts[card]++;
        else
            counts[card] = 1;
    }
    vector<int> values;
    for (auto it = counts.begin(); it != counts.end(); it++) {
        values.push_back(it->second);
    }
    sort(values.begin(), values.end()); // ascending;
    if (values.at(values.size()-1) == 5)
        return 6; 
    if (values.at(values.size()-1) == 4)
        return 5; 
    if (values.at(values.size()-1) == 3 && values.at(values.size()-2) == 2)
        return 4; 
    if (values.at(values.size()-1) == 3 && values.at(values.size()-2) == 1)
        return 3; 
    if (values.at(values.size()-1) == 2 && values.at(values.size()-2) == 2)
        return 2; 
    if (values.at(values.size()-1) == 2 && values.at(values.size()-2) == 1)
        return 1;
    return 0;
}

int type_part2(hand myhand) {
    map<char, int> counts;
    for (char card : myhand.cards) {
        if (counts.find(card) != counts.end())
            counts[card]++;
        else
            counts[card] = 1;
    }
    int Js = 0;
    if (auto J = counts.find('J'); J != counts.end()) {
        Js = counts['J'];
        counts.erase('J');
    }
    vector<int> values;
    for (auto it = counts.begin(); it != counts.end(); it++) {
        values.push_back(it->second);
    }
    sort(values.begin(), values.end()); // ascending;
    if (Js == 5)
        return 6;
    if (values.at(values.size()-1) == 5) // can't have J
        return 6; 
    if (values.at(values.size()-1) == 4)
        if (Js == 1)
            return 6;
        else
            return 5; 
    if (values.at(values.size()-1) == 3 && values.size() > 1 && values.at(values.size()-2) == 2) // can't have J
        return 4; 
    if (values.at(values.size()-1) == 3) {

        if (Js == 2)
            return 6; // five
        if (Js == 1)
            return 5; // four
        return 3; // three
    }
    if (values.at(values.size()-1) == 2 && values.size() > 1  && values.at(values.size()-2) == 2) {
        if (Js == 1) // full house
            return 4;
        return 2; 
    }
    if (values.at(values.size()-1) == 2) {
        if (Js >= 2)
            return (Js + 2) + 1; // 5 or 6 pts
        if (Js == 1)
            return 3; // 3 of a kind
        return 1;
    }
    if (Js == 4)
        return 6;
    if (Js == 3)
        return 5;
    if (Js == 2)
        return 3;
    if (Js == 1)
        return 1;
    return 0;
}

bool compare(const hand& hand1, const hand& hand2) {
    int type1 = type(hand1);
    int type2 = type(hand2);
    if (type1 < type2)
        return true;
    if (type1 > type2)
        return false;
    const string order = "23456789TJQKA";
    for (int i = 0; i < 5; i++) {
        int cardrank1 = order.find(hand1.cards.at(i));
        int cardrank2 = order.find(hand2.cards.at(i));
        if (cardrank1 < cardrank2)
            return true;
        if (cardrank1 > cardrank2)
            return false;
    }
    return false;
}

bool compare_part2(const hand& hand1, const hand& hand2) {
    int type1 = type_part2(hand1);
    int type2 = type_part2(hand2);
    if (type1 < type2)
        return true;
    if (type1 > type2)
        return false;
    const string order = "J23456789TQKA";
    for (int i = 0; i < 5; i++) {
        int cardrank1 = order.find(hand1.cards.at(i));
        int cardrank2 = order.find(hand2.cards.at(i));
        if (cardrank1 < cardrank2)
            return true;
        if (cardrank1 > cardrank2)
            return false;
    }
    return false;
}

signed main() {
    string cards;
    int bid;
    vector<hand> hands;
    while (cin >> cards) {
        cin >> bid;
        hand myhand = { cards, bid };
        hands.push_back(myhand);
    }
    sort(hands.begin(), hands.end(), compare);
    p(hands.at(0).cards);
    p(hands.at(hands.size() - 1).cards);
    int winnings = 0;
    for (int i = 0; i < hands.size(); i++) {
        winnings += (i+1) * hands.at(i).bid;
    }
    p(winnings);
    sort(hands.begin(), hands.end(), compare_part2);
    p(hands.at(0).cards);
    p(hands.at(hands.size() - 1).cards);
    winnings = 0;
    for (int i = 0; i < hands.size(); i++) {
        winnings += (i+1) * hands.at(i).bid;
    }
    p(winnings);
    return 0;
}