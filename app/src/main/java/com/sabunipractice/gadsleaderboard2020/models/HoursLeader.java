package com.sabunipractice.gadsleaderboard2020.models;

public class HoursLeader implements Comparable<HoursLeader> {
    private String name;
    private int hours;
    private String country;
    private String badgeUrl;

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getCountry() {
        return country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    @Override
    public int compareTo(HoursLeader o) {
        return o.getHours() - this.hours;
    }
}
