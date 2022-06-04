package edu.neu.madcourse.NUMAD22Su_ShashaWang;

public class LinkInfo {
    private String name;
    private String link;
    public LinkInfo(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName(){
        return name;
    }

    public String getLink(){
        return link;
    }
}
