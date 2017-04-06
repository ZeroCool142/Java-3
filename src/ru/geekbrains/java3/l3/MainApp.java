package ru.geekbrains.java3.l3;

import java.io.*;
import java.util.*;

/**
        Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;

        Последовательно сшить 5 файлов в один (файлы также ~100 байт).
        Может пригодиться следующая конструкция:
        ArrayList<InputStream> al = new ArrayList<>();
        ...
        Enumeration<InputStream> e = Collections.enumeration(al);

        Написать консольное приложение, которое умеет постранично читать текстовые файлы
        (размером > 10 mb), вводим страницу, программа выводит ее в консоль (за страницу можно
        принять 1800 символов). Время чтения файла должно находится в разумных пределах
        (программа не должна загружаться дольше 10 секунд), ну и чтение тоже не должно занимать
        >5 секунд.
*/


public class MainApp {
    public static void main(String[] args) {

        File arr = new File("array.txt");
        ArrayList<Integer> arrayList = new ArrayList<>();
        long start, end;

        start = System.currentTimeMillis();
        try (Scanner sc = new Scanner(arr)) {
            while (sc.hasNext()){
                arrayList.add(sc.nextInt());
            }
        } catch (FileNotFoundException e){
            System.out.println("File " + arr.getName() + " not found!");
        }
        end = System.currentTimeMillis() - start;
        System.out.printf("Reading array of int from file(%dKb): %dms.\n", arr.length()/1024, end);

        start = System.currentTimeMillis();
        int mergedFiles = mergeAllTxt(".", "merged.txt", ".txt");
        end = System.currentTimeMillis()-start;
        System.out.printf("Merge files: %dms.\n", end);
        if(mergedFiles>0){
            System.out.println(mergedFiles + " files merged!");
        }

        TextReader tr = new TextReader();
        tr.start();
    }

    /**
     * Merge all files in directory
     *
     * @param inputDir Directory for search files
     * @param outputName Name of output file
     * @param extension Extension of files for search
     * @return Count of merged files, 0 if files not found, -1 if exception occurred
     */
    private static int mergeAllTxt(String inputDir, String outputName, String extension){
        File dir = new File(inputDir);
        File output = new File(outputName);
        long sizeOfFiles =0;
        if (!dir.isDirectory()){
            return -1;
        }

        if (output.exists()){
            output.delete();
        }

        File[] filesToMerge = dir.listFiles(file -> file.toString().endsWith(extension));
        if(filesToMerge.length == 0) return 0;

        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(outputName))){
            for (File f : filesToMerge) {
                sizeOfFiles+=f.length()/1024;
                try (InputStream is = new BufferedInputStream(new FileInputStream(f))){
                    int r = is.read();
                    while (r != -1){
                        os.write(r);
                        r = is.read();
                    }
                    os.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.printf("Total size: %dKb\n", sizeOfFiles);
        return filesToMerge.length;
    }
}


