#include <iostream>
#define int int64_t
using namespace std;

const bool OO = 1;
#define p(x) if (OO) { cout << #x << ": " << x << endl;}
#define p_vec(x) if (OO) { cout << #x << ": "; for (auto val: x) { cout << val << " "; }; cout << endl;}
#define p_2dvec(x) if (OO) { cout << #x << ":\n"; for (auto v: x) { for (auto val: v) cout << val << " "; cout << endl; }}

template<typename ... Args>
std::string string_format( const std::string& format, Args ... args )
{
    int size_s = std::snprintf( nullptr, 0, format.c_str(), args ... ) + 1; // Extra space for '\0'
    if( size_s <= 0 ){ throw std::runtime_error( "Error during formatting." ); }
    auto size = static_cast<size_t>( size_s );
    std::unique_ptr<char[]> buf( new char[ size ] );
    std::snprintf( buf.get(), size, format.c_str(), args ... );
    return std::string( buf.get(), buf.get() + size - 1 ); // We don't want the '\0' inside
}

signed main() {
    string word;
    int num;
    cin >> word; // times
    vector<int> times;
    for (int i = 0; i < 4; i++) {
        cin >> num;
        times.push_back(num);
    }
    cin >> word; // distances
    vector<int> dists;
    for (int i = 0; i < 4; i++) {
        cin >> num;
        dists.push_back(num);
    }
    p_vec(times);
    p_vec(dists);
    int prod = 1;
    for (int i = 0; i < times.size(); i++) {
        int time = times.at(i);
        int dist = dists.at(i);
        p(time/2 + sqrt(time*time - 4*dist))
        p(time/2 - sqrt(time*time - 4*dist))
        int ways_to_win = 0;
        for (int hold = 0; hold <= time; hold++) {
            int my_dist = (time - hold) * hold;
            if (my_dist > dist)
                ways_to_win++;
        }
        p(ways_to_win);
        prod *= ways_to_win;
        p(prod)
    }
    // part 2
    int long_time = stoll(string_format("%d%d%d%d", times.at(0), times.at(1), times.at(2), times.at(3)));
    int long_dist = stoll(string_format("%d%d%d%d", dists.at(0), dists.at(1), dists.at(2), dists.at(3)));
    p(long_time);
    p(long_dist);

    int ways_to_win = 0;
        for (int hold = 0; hold <= long_time; hold++) {
            int my_dist = (long_time - hold) * hold;
            if (my_dist > long_dist)
                ways_to_win++;
        }
        p(ways_to_win);

    return 0;
}