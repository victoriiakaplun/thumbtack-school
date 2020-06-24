package net.thumbtack.school.ttschool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class School {
    private String name;
    private int year;
    private Set<Group> groupSet;

    public School(String name, int year) throws TrainingException {
        setName(name);
        setYear(year);
        groupSet = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("") || name.equals(this.name)) {
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Group> getGroups() {
        return groupSet;
    }

    public void addGroup(Group group) throws TrainingException {
        if (containsGroup(group)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        }
        groupSet.add(group);
    }

    public void removeGroup(Group group) throws TrainingException {
        if (!groupSet.remove(group)) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public void removeGroup(String name) throws TrainingException {
        Iterator<Group> groupIterator = groupSet.iterator();
        Group tmp;
        boolean flag = false;
        while (groupIterator.hasNext()) {
            tmp = groupIterator.next();
            if (tmp.getName().equals(name)) {
                removeGroup(tmp);
                flag = true;
            }
        }
        if (!flag) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public boolean containsGroup(Group group) {
        for (Group groups : groupSet) {
            if (groups.getName().equals(group.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year &&
                Objects.equals(name, school.name) &&
                Objects.equals(groupSet, school.groupSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groupSet);
    }
}
