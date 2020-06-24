package net.thumbtack.school.ttschool;

import java.util.*;

public class Group {
    private String name;
    private String room;
    private List<Trainee> traineeList;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        traineeList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) throws TrainingException {
        if (room == null || room.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return traineeList;
    }

    public void addTrainee(Trainee trainee) {
        traineeList.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!traineeList.remove(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTrainee(int index) throws TrainingException {
        if (index >= traineeList.size()) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        traineeList.remove(index);
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee trainee : traineeList) {
            if (trainee.getFirstName().equals(firstName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        for (Trainee trainee : traineeList) {
            if (trainee.getFullName().equals(fullName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant() {
        Collections.sort(traineeList, Comparator.comparing(Trainee::getFirstName));
    }

    public void sortTraineeListByRatingDescendant() {
        Collections.sort(traineeList, Comparator.comparingInt(Trainee::getRating));
        Collections.reverse(traineeList);
    }

    public void reverseTraineeList() {
        Collections.reverse(traineeList);
    }

    public void rotateTraineeList(int positions) {
        Collections.rotate(traineeList, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        if (traineeList.isEmpty()) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        Trainee tmp;
        int tmpMaxRating = 0;
        List<Trainee> traineesWithMaxRating = new ArrayList<>();
        for (Trainee trainee : traineeList) {
            tmp = trainee;
            if (tmp.getRating() == tmpMaxRating) {
                traineesWithMaxRating.add(tmp);
            }
            if (tmp.getRating() > tmpMaxRating) {
                traineesWithMaxRating.clear();
                tmpMaxRating = tmp.getRating();
                traineesWithMaxRating.add(tmp);
            }
        }
        return traineesWithMaxRating;
    }

    public boolean hasDuplicates() {
        return new HashSet<>(traineeList).size() < traineeList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(room, group.room) &&
                Objects.equals(traineeList, group.traineeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, traineeList);
    }
}
