class Member {
    rank = -1;
    lastRank;
    pts = 0;
    change = 0;
    v;
    y = height + 1; v_pts = 0; run_pts = 0;
    c = N;
    constructor(id, name, data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    reset() {
        this.rank = -1;
        this.pts = 0;
        this.run_pts = 0;
        this.change = 0;
        this.c = N;
    }

    show() {
        if (this.y >= height)
            return;
        fill(this.c);
        let last = "";
        if (this.change != 0) {
            last += " ";
            if (this.change > 0)
                last += "\u25bc";
            else
                last += "\u25b2";
            last += " " + abs(this.change);
        }
        text(this.name + ": " + round(this.run_pts) + last, NAME_X, this.y);
    }
    
    update() {
        this.run_pts += this.v_pts;
        this.y += this.v;
    }

    done(star) {
        return this.time(star) < 1700000000;
    }

    time(star) {
        let day = str(int(star / 2) + 1);
        let part = str((star % 2) + 1);
        let cdl = this.data.completion_day_level;
        if (cdl[day] != undefined && cdl[day][part] != undefined)
            return cdl[day][part].get_star_ts;
        else
            return 1700000000;
    }

    addPts() {
        this.pts += mems.length - this.lastRank;
        this.v_pts = (float)(mems.length - this.lastRank) / STEPS;
    }

    static compareTo(a, b) {
        if (SORT_BY_TIME)
            return int((a.time(star) - b.time(star)) % 100000);
        else
            return b.pts - a.pts;
    }

    toString() {
        return "[id=" + this.id + ", name=" + this.name + ", pts=" + this.pts + "]";
    }
}
