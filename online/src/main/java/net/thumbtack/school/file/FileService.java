package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.RectButton;
import net.thumbtack.school.windows.v4.base.WindowException;

import java.io.*;

import static java.lang.Integer.valueOf;
import static net.thumbtack.school.windows.v4.base.WindowState.fromString;

public class FileService {
    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(array);
        }
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] bytes = new byte[(int) new File(fileName).length()];
            fis.read(bytes);
            return bytes;
        }
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        }
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(array.length)) {
            baos.write(array);
            byte[] buf = baos.toByteArray();
            byte[] bytes = new byte[buf.length / 2 + buf.length % 2];
            try (ByteArrayInputStream bais = new ByteArrayInputStream(buf)) {
                byte current;
                for (int index = 0; (current = (byte) bais.read()) != -1; index++) {
                    bytes[index] = current;
                    bais.skip(1);
                }
                return bytes;
            }
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bos.write(array);
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[(int) new File(fileName).length()];
            bis.read(bytes);
            return bytes;
        }

    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[(int) file.length()];
            bis.read(bytes);
            return bytes;
        }
    }

    public static void writeRectButtonToBinaryFile(File file, RectButton rectButton) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(rectButton.getTopLeft().getX());
            dos.writeInt(rectButton.getTopLeft().getY());
            dos.writeInt(rectButton.getBottomRight().getX());
            dos.writeInt(rectButton.getBottomRight().getY());
            dos.writeUTF(rectButton.getState().getStateString());
            dos.writeUTF(rectButton.getText());
        }
    }

    public static RectButton readRectButtonFromBinaryFile(File file) throws IOException, WindowException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            return new RectButton(new Point(dis.readInt(), dis.readInt()),
                    new Point(dis.readInt(), dis.readInt()),
                    fromString(dis.readUTF()),
                    dis.readUTF());
        }
    }

    public static void writeRectButtonArrayToBinaryFile(File file, RectButton[] rects) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (RectButton rectBut : rects) {
                dos.writeInt(rectBut.getTopLeft().getX());
                dos.writeInt(rectBut.getTopLeft().getY());
                dos.writeInt(rectBut.getBottomRight().getX());
                dos.writeInt(rectBut.getBottomRight().getY());
            }
        }
    }

    public static void modifyRectButtonArrayInBinaryFile(File file) throws IOException, WindowException {
        RectButton[] rectButtons = readRectButtonArrayFromBinaryFile(file);
        for (RectButton rect : rectButtons) {
            rect.moveRel(1, 0);
        }
        writeRectButtonArrayToBinaryFile(file, rectButtons);
    }

    public static RectButton[] readRectButtonArrayFromBinaryFile(File file) throws IOException, WindowException {
        int rectAmount = (int) (file.length() / 16);
        RectButton[] rectButtons = new RectButton[rectAmount];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            for (int i = 0; i < rectAmount; i++) {
                rectButtons[i] = new RectButton(
                        new Point(dis.readInt(), dis.readInt()),
                        new Point(dis.readInt(), dis.readInt()), fromString("ACTIVE"),
                        "OK");
            }
        }
        return rectButtons;
    }

    public static void writeRectButtonToTextFileOneLine(File file, RectButton rectButton) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter((file)))) {
            bw.write(rectButton.toString());
        }
    }

    public static RectButton readRectButtonFromTextFileOneLine(File file) throws IOException, WindowException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str = br.readLine();
            String[] array = str.split("\\s");
            return new RectButton(new Point(valueOf(array[0]), valueOf(array[1])),
                    new Point(valueOf(array[2]), valueOf(array[3])),
                    fromString(array[4]),
                    array[5]);
        }
    }

    public static void writeRectButtonToTextFileSixLines(File file, RectButton rectButton) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            bw.write("" + rectButton.getTopLeft().getX());
            bw.newLine();
            bw.write("" + rectButton.getTopLeft().getY());
            bw.newLine();
            bw.write("" + rectButton.getBottomRight().getX());
            bw.newLine();
            bw.write("" + rectButton.getBottomRight().getY());
            bw.newLine();
            bw.write(rectButton.getState().getStateString());
            bw.newLine();
            bw.write(rectButton.getText());
        }
    }

    public static RectButton readRectButtonFromTextFileSixLines(File file) throws IOException, WindowException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return new RectButton(new Point(valueOf(br.readLine()), valueOf(br.readLine())),
                    new Point(valueOf(br.readLine()), valueOf(br.readLine())),
                    fromString(br.readLine()),
                    br.readLine());
        }
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            bw.write(trainee.toString());
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String str = br.readLine();
            String[] array = str.split("\\s");
            return new Trainee(array[0], array[1], valueOf(array[2]));
        }
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            bw.write(trainee.getFirstName());
            bw.newLine();
            bw.write(trainee.getLastName());
            bw.newLine();
            bw.write("" + trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            return new Trainee(br.readLine(), br.readLine(), valueOf(br.readLine()));
        }
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(trainee);
        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Trainee) ois.readObject();
        }
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            gson.toJson(trainee, bw);
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException, TrainingException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            return gson.fromJson(br, Trainee.class);
        }
    }
}


