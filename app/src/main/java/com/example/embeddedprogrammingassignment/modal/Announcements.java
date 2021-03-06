package com.example.embeddedprogrammingassignment.modal;

public class Announcements {

    String title, caption, url, urlToImage, id, numberOfClicks, numberOfLikes;

    public void setNumberOfClicks(String numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
    }

    public String getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(String numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getNumberOfClicks() { return numberOfClicks; }

    public String getId() { return id; }

    @Override
    public String toString() {
        return "Announcements{" +
                "title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", id='" + id + '\'' +
                ", numberOfClicks='" + numberOfClicks + '\'' +
                ", numberOfLikes='" + numberOfLikes + '\'' +
                '}';
    }
}
