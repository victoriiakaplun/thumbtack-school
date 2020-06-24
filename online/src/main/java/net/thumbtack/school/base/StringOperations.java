package net.thumbtack.school.base;

public class StringOperations {
    public static int getSummaryLength(String[] strings) {
        int sumLength = 0;
        for (String s : strings) {
            sumLength += s.length();
        }
        return sumLength;
    }

    public static String getFirstAndLastLetterString(String string) {
        String result = new String("");
        result += Character.toString(string.charAt(0)) + Character.toString(string.charAt(string.length() - 1));
        return result;
    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        return string1.charAt(index) == string2.charAt(index);
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character) == string2.indexOf(character);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return string1.lastIndexOf(character) == string2.lastIndexOf(character);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.indexOf(str) == string2.indexOf(str);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2) < 0;
    }

    public static String concat(String string1, String string2) {
        String result = new String("");
        return result.concat(string1).concat(string2);
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return string1.startsWith(prefix) && (string2.startsWith(prefix));
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return string1.endsWith(suffix) == string2.endsWith(suffix);
    }

    public static String getCommonPrefix(String string1, String string2) {
        String commonPrefix = "";
        int index = 0;
        while (index < string1.length() && index < string2.length()){
            if (string1.charAt(index) != string2.charAt(index)) {
                return commonPrefix;
            } else {
                commonPrefix = commonPrefix.concat(Character.toString(string2.charAt(index)));
            }
            index++;
        }
        return commonPrefix;
    }

    public static String reverse(String string) {
        StringBuilder str = new StringBuilder(string);
        return str.reverse().toString();
    }

    public static boolean isPalindrome(String string) {
        String str = new String(string);
        return reverse(string).equals(str);
    }

    public static boolean isPalindromeIgnoreCase(String string) {
        StringBuilder str = new StringBuilder(string);
        return str.reverse().toString().equalsIgnoreCase(string);
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String longest = "";
        for (int pos = 0; pos < strings.length; pos++) {
            if (isPalindromeIgnoreCase(strings[pos]) && strings[pos].length() > longest.length()) {
                longest = strings[pos];
            }
        }
      return longest;
    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        if (index + length - 1 >= string1.length() || index + length - 1 >= string2.length()) {
            return false;
        }
        return string1.substring(index, index + length - 1).equals(string2.substring(index, index + length - 1));
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1,
                                                        String string2, char replaceInStr2, char replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));

    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1,
                                                     String string2, String replaceInStr2, String replaceByInStr2) {
        return string1.replaceAll(replaceInStr1, replaceByInStr1).equals(string2.replaceAll(replaceInStr2, replaceByInStr2));
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        String str = new String(string.replaceAll("\\s+", ""));
        return isPalindromeIgnoreCase(str);
    }

    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return string1.trim().equals(string2.trim());
    }

    public static String makeCsvStringFromInts(int[] array) {
        if (array == null) {
            return "";
        }
        String csvString = String.format("");
        for (int pos = 0; pos < array.length; pos++) {
            csvString = csvString.concat(String.valueOf(array[pos]));
            if (pos < array.length - 1) {
                csvString = csvString.concat(",");
            }
        }
        return csvString;
    }

    public static String makeCsvStringFromDoubles(double[] array) {
        if (array == null) {
            return "";
        }
        String csv = new String("");
        for (int pos = 0; pos < array.length; pos++) {
            csv = csv.concat(String.format("%.2f", array[pos]));
            if (pos < array.length - 1) {
                csv = csv.concat(",");
            }
        }
        return csv;

    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        if (array == null) {
            return new StringBuilder("");
        }
        return new StringBuilder(makeCsvStringFromInts(array));
    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        if (array == null) {
            return null;
        }
        return new StringBuilder(makeCsvStringFromDoubles(array));
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder str = new StringBuilder(string);
        for (int pos = 0; pos < positions.length; pos++) {
            str.setCharAt(positions[pos], ' ');
        }
        return new StringBuilder(str.toString().replaceAll("\\s", ""));
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int offset = 0;
        for (int i = 0; i < positions.length; i++) {
            stringBuilder.insert(positions[i] + offset++, characters[i]);
        }
        return stringBuilder;
    }
}
