package com.woxthebox.draglistview.sample;

/**
 * Created by pivotal on 4/21/17.
 */
public class Story {
    public String name;
    public boolean dropHighlight = false;
    public long id;

    public Story(long id, String name) {
        this.id = id;
        this.name = name;
        this.dropHighlight = false;
    }
}
