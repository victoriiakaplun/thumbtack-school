package net.thumbtack.school.matrix;

import java.util.*;

public class MatrixNonSimilarRows {
    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i];
        }
    }

    public List<int[]> getNonSimilarRows() {
        Map<Set<Integer>, int[]> setMap = new HashMap<>();
        for (int[] array : matrix) {
            Set<Integer> tmpSet = new HashSet<>();
            for (int el : array) {
                tmpSet.add(el);
            }
            setMap.put(tmpSet, array);
        }
        return new ArrayList<>(setMap.values());
    }
}
