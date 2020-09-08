package com.sabunipractice.gadsleaderboard2020.models;

public class SkillIQLeader implements Comparable<SkillIQLeader> {
    private String name;
    private int score;
    private String country;
    private String badgeUrl;


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    /**
     * Sort objects
     * */
    @Override
    public int compareTo(SkillIQLeader o) {
        return  o.getScore() - this.score;
    }
}
