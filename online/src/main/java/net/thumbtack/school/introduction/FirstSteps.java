package net.thumbtack.school.introduction;

public class FirstSteps {
    public int sum(int x, int y) {
        return x + y;
    }

    public int mul(int x, int y) {
        return x * y;
    }

    public int div(int x, int y) {
        return x / y;
    }

    public int mod(int x, int y) {
        return x % y;
    }

    public boolean isEqual(int x, int y) {
        return x == y;
    }

    public boolean isGreater(int x, int y) {
        return x > y;
    }


    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y) {
        return x <= xRight && x >= xLeft && y >= yTop && y <= yBottom;
    }

    public int sum(int[] array) {
        int sum = 0;
        for (int elem : array) {
            sum += elem;
        }
        return sum;
    }

    public int mul(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int mul = 1;
        for (int elem : array) {
            mul *= elem;
        }
        return mul;
    }

    public int min(int[] array) {
        if (array.length == 0) {
            return Integer.MAX_VALUE;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                min = array[i];
            }
        }
        return min;
    }

    public int max(int[] array) {
        if (array.length == 0) {
            return Integer.MIN_VALUE;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                max = array[i];
            }
        }
        return max;
    }

    public double average(int[] array) {
        int sum = 0;
        if (array.length == 0) {
            return 0;
        }
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return (double) sum / array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        if (array.length == 0) {
            return true;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] >= array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public void cube(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] * array[i] * array[i];
        }
    }

    public boolean find(int[] array, int value) {
        for (int elem : array) {
            if (array[elem] == value) {
                return true;
            }
        }
        return false;
    }

    public void reverse(int[] array) {
        int t;
        for (int i = 0; i < array.length / 2; i++) {
            t = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = t;
        }
    }

    public boolean isPalindrome(int[] array) {
        if (array.length == 0) return true;
        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public int sum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum = sum + matrix[i][j];
            }
        }
        return sum;
    }

    public int max(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if (max(matrix[i]) > max) {
                max = max(matrix[i]);
            }
        }
        return max;
    }

    public int diagonalMax(int[][] matrix) {
        if (matrix.length == 0) {
            return Integer.MIN_VALUE;
        }
        int max = matrix[0][0];
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][i] > max) {
                max = matrix[i][i];
            }
        }
        return max;
    }

    public boolean isSortedDescendant(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (!isSortedDescendant(matrix[i])) {
                return false;
            }
        }
        return true;
    }
}
