package ru.geekbrains.java3.filewalker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Write all .java files in subpackages in one .txt
 */
public class FileWalker {
    public static void main(String[] args) {
        String path = args[0];
        File[] directories = new File(path).listFiles(File::isDirectory);

        for (File dir : directories) {
            try (DataOutputStream fw = new DataOutputStream(new FileOutputStream("dz_" + dir.getName() + ".txt"))) {
                Files.find(Paths.get(dir.toString()), 999,
                        (p, bfa) -> bfa.isRegularFile() && p.getFileName().toString().contains(".java"))
                        .forEach((file) -> {

                            try (FileInputStream dis = new FileInputStream(file.toFile())) {
                                fw.writeBytes("//:~ " + file + "\r\n");
                                while (dis.available() > 0) {
                                    fw.write(dis.read());
                                    fw.flush();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
