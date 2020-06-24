package net.thumbtack.school.concert.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Song {

    private String author;
    private String name;
    private List<String> composers;
    private List<String> poets;
    private String artist;
    private int time;
    private Map<String, Integer> rating;

    public Song(String author, String name, List<String> composers, List<String> poets, String artist, int time) {
        this.author = author;
        this.name = name;
        this.composers = composers;
        this.poets = poets;
        this.artist = artist;
        this.time = time;
        rating = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getComposers() {
        return composers;
    }

    public List<String> getPoets() {
        return poets;
    }

    public String getArtist() {
        return artist;
    }

    public int getTime() {
        return time;
    }

    public Map<String, Integer> getRatingsMap() {
        return rating;
    }

    public int getRatingSum() {
        return rating.values().stream().reduce(0, (left, right) -> left + right);
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return time == song.time &&
                Objects.equals(name, song.name) &&
                Objects.equals(composers, song.composers) &&
                Objects.equals(poets, song.poets) &&
                Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, composers, poets, artist, time);
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", composers=" + composers +
                ", poets=" + poets +
                ", artist='" + artist + '\'' +
                ", time=" + time +
                '}';
    }
}