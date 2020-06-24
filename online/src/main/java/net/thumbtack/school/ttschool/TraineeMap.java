package net.thumbtack.school.ttschool;

import java.util.*;

public class TraineeMap {
    private Map<Trainee, String> map;

    public TraineeMap() {
        map = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (map.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
        map.put(trainee, institute);
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        String value = map.replace(trainee, institute);
        if (value == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        String value = map.remove(trainee);
        if (value == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public int getTraineesCount() {
        return map.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        String value = map.get(trainee);
        if (value == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return value;
    }

    public Set<Trainee> getAllTrainees() {
        return map.keySet();
    }

    public Set<String> getAllInstitutes() {
        return new HashSet<>(map.values());
    }

    public boolean isAnyFromInstitute(String institute) {
        return map.containsValue(institute);
    }

}
