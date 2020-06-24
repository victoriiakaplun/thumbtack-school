package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.Queue;

public class TraineeQueue {
    private Queue<Trainee> traineeQueue;

    public TraineeQueue() {
        traineeQueue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee) {
        traineeQueue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException {
    	Trainee trainee = traineeQueue.poll();
        if (trainee == null) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
        return trainee;
    }

    public boolean isEmpty() {
        return traineeQueue.isEmpty();
    }

}
