package net.thumbtack.school.concert.model;

import java.util.List;
import java.util.Objects;

public class ConcertData {
    private Song song;
    private User author;
    private double average;
    private List<Comment> comments;

    public ConcertData(Song song, User author, double average, List<Comment> comments) {
        this.song = song;
        this.author = author;
        this.average = average;
        this.comments = comments;
    }

    public Song getSong() {
        return song;
    }

    public User getAuthor() {
        return author;
    }

    public double getAverage() {
        return average;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcertData that = (ConcertData) o;
        return average == that.average &&
                Objects.equals(song, that.song) &&
                Objects.equals(author, that.author) &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(song, author, average, comments);
    }

    @Override
    public String toString() {
        return "ConcertData{" +
                "song=" + song +
                ", author=" + author +
                ", average=" + average +
                ", comments=" + comments +
                '}';
    }
}
