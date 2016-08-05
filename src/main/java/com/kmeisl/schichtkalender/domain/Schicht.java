package com.kmeisl.schichtkalender.domain;

import java.util.Date;

public final class Schicht {
    private long id;
    private String title;
    private Date releaseDate;

    public void setId(final long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getTitle() {
        return title;
    }


    public long getId() {
        return id;
    }

}
