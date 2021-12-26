int star = 0;
ArrayList<Member> mems;
int timer;
final int STEPS = 60;
final int BREAK = 60;
final int TOPN = 20; // 80
boolean SORT_BY_TIME = true;
final int NAME_X = 100; // 35
final int NUM_X = 75; // 30
final int FONT = 36; // 0
final int STOP = 18;
import java.util.Collections;
boolean wait = true;
final color g = color(21, 191, 78);
final color r = color(191, 27, 21);
final color n = color(201, 208, 221);

void setup() {
    JSONObject json = loadJSONObject("951576.json").getJSONObject("members");
    size(1024, 768); // 256, 768
    textSize(FONT);
    mems = new ArrayList<Member>();
    for (Object key : json.keys()) {
        String k = (String)key;
        String name;
        if (json.getJSONObject(k).isNull("name"))
            name = "#" + k;
        else
            name = json.getJSONObject(k).getString("name");
        
        Member m = new Member(k, name, json.getJSONObject(k));
        mems.add(m);
        println(m);
    }
    println(mems.size());
    timer = BREAK + STEPS;
}

void keyPressed() {
  if (timer == BREAK + STEPS && star == 0)
    wait = false;
}

void draw() {
    background(40, 44, 52);
    if (timer == BREAK + STEPS && star == 0 && wait) {
      return;
    }
    if (timer == BREAK + STEPS) {
        findRanks();
    }
    textAlign(RIGHT, TOP);
    fill(97, 175, 239);
    for (int r = 0; r < TOPN; r++) {
        text((r >= 10 ? "" : " ") + (r+1) + ")", NUM_X, rankY(r));
    }
    fill(198, 120, 221);
    text("day " + (int(star / 2) + 1) + ", star " + (star%2 + 1), width, 0);
    textAlign(LEFT, TOP);
    fill(240);
    for (Member m : mems) {
        if (timer > BREAK)
            m.update();
        else if (timer == BREAK) {
            m.run_pts = m.pts; 
        }
        m.show();
    }
    timer--;
    if (timer == 0) {
        star++;
        timer = BREAK + STEPS;
        if (star == STOP)
            noLoop();
    }
}

float rankY(int r) {
    if (r < TOPN) {
        return r * ((height-10) / (float)TOPN);
    }
    else return height + 1;
}

void findRanks() {
    SORT_BY_TIME = true;
    Collections.sort(mems);
    int i = 0;
    for (Member m : mems) {
        m.lastRank = i;
        if (m.done(star)) {
            m.addPts();
        }
        else {
            println("not done: " + m);
            break;
        }
        i++;
    }
    SORT_BY_TIME = false;
    Collections.sort(mems);
    i = 0;
    for (Member m : mems) {
        int lr = m.rank;
        m.rank = i;
        if (lr >= 0) {
            int diff = m.rank - lr;
            m.c = changeColor(diff);
            m.change = diff;
        }
        m.y = round(m.y);
        m.v = (rankY(m.rank) - m.y) / STEPS;
        i++;
        if (i == mems.size())
            println(m);
    }
}

color changeColor(int change) {

    final int mc = 5;
    if (change >= 0)
        return color(map(change, 0, mc, red(n), red(r)), map(change, 0, mc, green(n), green(r)), map(change, 0, mc, blue(n), blue(r)));
    else
        return color(map(change, 0, -mc, red(n), red(g)), map(change, 0, -mc, green(n), green(g)), map(change, 0, -mc, blue(n), blue(g)));
}
