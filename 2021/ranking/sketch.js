let STEPS;
let BREAK;
let TOTAL;
let O_TOTAL;
let P_R; 
let O_P_R;
let TOPN; // 80
let O_TOPN;
const NAME_X = 100; // 35
const NUM_X = 75; // 30
let FONT;
const STOP = new Date('Dec 25 2021') > new Date() ? 2 * (new Date().getDate()) : 50;
const FULL_COLOR_CHANGE = 5;
let G, R, N;
let star;
let mems;
let timer;
let SORT_BY_TIME = true;
let json;
function preload() {
    json = loadJSON("951576.json");
}

function reset() {
    star = 0;
    timer = O_TOTAL;
    mems.forEach(m => m.reset());
    loop();
}

function inputs() {
    TOTAL = createSlider(10, 200, 120, 10);
    TOTAL.position(width / 2, height / 2);
    P_R = createSlider(0, 9, 5, 1);
    P_R.position(width / 2, height / 2 + 30);
    TOPN = createSlider(10, 180, 20, 1);
    TOPN.position(width / 2, height / 2 + 60);
    RST = createButton("RESET");
    RST.position(width / 2, height / 2 + 90);
    RST.mousePressed(reset);
    adapt();
}

function adapt() {
    if (O_TOTAL != TOTAL.value() || O_P_R != P_R.value() || O_TOPN != TOPN.value()) {
        O_TOTAL = TOTAL.value();
        O_P_R = P_R.value();
        O_TOPN = TOPN.value();
        FONT = height / (O_TOPN);
        textSize(FONT);
        STEPS = O_TOTAL * (10 - O_P_R) / 10;
        BREAK = O_TOTAL * O_P_R / 10;
    }
}

function setup() {
    G = color(21, 191, 78);
    R = color(191, 27, 21);
    N = color(201, 208, 221);
    createCanvas(windowWidth, windowHeight);
    inputs();
    json = json.members;
    mems = [];
    for (key in json) {
        let name;
        if (json[key].name == undefined)
            name = "#" + key;
        else
            name = json[key].name;
        
        m = new Member(key, name, json[key]);
        mems.push(m);
    };
    reset();
}

function draw() {
    background(40, 44, 52);
    if (timer == O_TOTAL) {
        findRanks();
    }
    drawStatic();
    textAlign(LEFT, TOP);
    mems.forEach(m => {
        if (timer > BREAK)
            m.update();
        else if (timer == BREAK) {
            m.run_pts = m.pts; 
        }
        m.show();
    });
    timer--;
    if (timer == 0) {
        adapt();
        star++;
        timer = O_TOTAL;
        if (star == STOP)
            noLoop();
    }
}

function drawStatic() {
    textAlign(RIGHT, TOP);
    fill(97, 175, 239); // blue numbers
    for (let rnk = 0; rnk < O_TOPN; rnk++) {
        text((rnk >= 10 ? "" : " ") + (rnk+1) + ")", NUM_X, rankY(rnk));
    }
    textSize(25);
    textAlign(LEFT, TOP);
    let d = 150;
    text((O_TOTAL != TOTAL.value() ? "soon " : "") + TOTAL.value() + " frames / star", width/2+d, height / 2);
    text((O_P_R != P_R.value() ? "soon " : "") + P_R.value() * 10 + "% Pause", width/2+d, height / 2 + 30);
    text((O_TOPN != TOPN.value() ? "soon " : "") + "TOP " + TOPN.value() + " places" , width/2+d, height / 2 + 60);


    fill(198, 120, 221); // violet day, star
    textAlign(CENTER, TOP);
    textSize(height / 15);
    text("day " + (int(star / 2) + 1) + ", star " + (star%2 + 1), width / 2, height/25);
    textSize(FONT);


}

function rankY(rnk) {
    if (rnk < O_TOPN) {
        return rnk * ((height-10) / O_TOPN);
    }
    else return height + 1;
}

function findRanks() {
    SORT_BY_TIME = true;
    mems.sort(Member.compareTo);
    let i = 0;
    mems.every(m => {
        m.lastRank = i;
        if (m.done(star))
            m.addPts();
        else
            m.v_pts = 0;
        i++;
        return true;
    });
    SORT_BY_TIME = false;
    mems.sort(Member.compareTo);
    i = 0;
    mems.forEach(m => {
        lr = m.rank;
        m.rank = i;
        if (lr >= 0) {
            let diff = m.rank - lr;
            m.c = changeColor(diff);
            m.change = diff;
        }
        m.y = round(m.y);
        m.v = (rankY(m.rank) - m.y) / STEPS;
        i++;
    });
}

function changeColor(change) {
    if (change >= 0)
        return color(map(change, 0, FULL_COLOR_CHANGE, red(N), red(R)),
                     map(change, 0, FULL_COLOR_CHANGE, green(N), green(R)),
                     map(change, 0, FULL_COLOR_CHANGE, blue(N), blue(R)));
    else
        return color(map(change, 0, -FULL_COLOR_CHANGE, red(N), red(G)),
                     map(change, 0, -FULL_COLOR_CHANGE, green(N), green(G)),
                     map(change, 0, -FULL_COLOR_CHANGE, blue(N), blue(G)));
}
