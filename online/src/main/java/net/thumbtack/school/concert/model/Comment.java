package net.thumbtack.school.concert.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Comment {
    private int songId;
    private String author;
    private String comment;
    private Set<String> joinedUsers;

    public Comment(int songId, String author, String comment) {
        this.songId = songId;
        this.author = author;
        this.comment = comment;
        joinedUsers = new HashSet<>();
    }

    public Comment(int songId, String author, String comment, Set<String> joinedUsers) {
        this.songId = songId;
        this.author = author;
        this.comment = comment;
        this.joinedUsers = joinedUsers;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<String> getJoinedUsers() {
        return joinedUsers;
    }

    public int getSongId() {
        return songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return songId == comment1.songId &&
                Objects.equals(author, comment1.author) &&
                Objects.equals(comment, comment1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, author, comment);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "songId=" + songId +
                ", author='" + author + '\'' +
                ", comment='" + comment + '\'' +
                ", joinedUsers=" + joinedUsers +
                '}';
    }
}
