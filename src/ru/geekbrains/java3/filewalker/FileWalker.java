package ru.geekbrains.java3.filewalker;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;


/**
 * Write all .java files in subpackages in one .txt
 *
 */
public class FileWalker {
    public static void main(String[] args) {
        String path = args[0];
        File[] directories = new File(path).listFiles(File::isDirectory);

        for (File dir : directories) {
            try (FileWriter fw = new FileWriter("dz_"+ dir.getName()+ ".txt")){
                Files.find(Paths.get(dir.toString()), 999, (p, bfa) -> bfa.isRegularFile() &&
                        p.getFileName().toString().contains(".java")).forEach((file) ->{
                    try {
                        fw.append("\n//:~ " + file+"\r\n");
                        FileReader fr = new FileReader(file.toString());
                        int tempChar;
                        while ((tempChar = fr.read()) != -1){
                            fw.append((char)tempChar);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
