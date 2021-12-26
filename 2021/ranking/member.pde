class Member implements Comparable<Member> {
    String id, name;
    int rank = -1, lastRank, pts, change = 0;
    float v, y = height + 1, v_pts, run_pts;
    color c = n;
    JSONObject data;
    Member(String id, String name, JSONObject data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    void show() {
        if (y >= height)
            return;
        fill(c);
        int up = 9650;
        int down = 9660;
        String last = "";
        if (change != 0) {
            last += " ";
            if (change > 0)
                last += new String(Character.toChars(down));
            else
                last += new String(Character.toChars(up));
            last += " " + abs(change);
        }
        text(this.name + ": " + round(this.run_pts) + last, NAME_X, y);
    }
    
    void update() {
        run_pts += v_pts;
        y += v;
    }

    boolean done(int star) {
        return time(star) < 1700000000L;
    }

    long time(int star) {
        String day = str(int(star / 2) + 1);
        String part = str((star % 2) + 1);
        JSONObject c = data.getJSONObject("completion_day_level");
        if (!c.isNull(day) && !c.getJSONObject(day).isNull(part))
            return c.getJSONObject(day).getJSONObject(part).getLong("get_star_ts");
        else
            return 1700000000L;
    }

    void addPts() {
        this.pts += mems.size() - this.lastRank;
        this.v_pts = (float)(mems.size() - this.lastRank) / STEPS;
    }

    @Override
    public int compareTo(Member other) {
        if (SORT_BY_TIME)
            return int((this.time(star) - other.time(star)) % 100000);
        else
            return other.pts - this.pts;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", pts=" + pts + "]";
    }
}
