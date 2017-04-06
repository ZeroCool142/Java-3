package ru.geekbrains.java3.l3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class TextReader {
    private int pageSize = 1800;
    private Scanner sc = new Scanner(System.in);

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void start() {
        printHelp();
        String command;

        while (true) {
            System.out.print("\nCommand: ");
            command = sc.nextLine();
            if (command.startsWith("/open")) {
                open(command.substring(command.indexOf(" ") + 1));
            } else if (command.startsWith("/help")) {
                printHelp();
            } else if (command.startsWith("/exit")) {
                return;
            } else if (command.startsWith("/set")) {
                try {
                    pageSize = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
                } catch (NumberFormatException e) {
                    System.out.print("Invalid command!\n");
                }
            } else {
                System.out.print("Invalid command!\n");
            }
        }
    }

    private void open(String fileName) {
        long maxPage, curPage;
        File file = new File(fileName);
        String command;
        if (file.isFile()) {
            try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
                maxPage = f.length() / pageSize;
                curPage = 0;
                workingWithFileHelp();
                System.out.println();
                printPage(f, curPage);
                while (true) {
                    System.out.printf("\n(0-%d) (%d): ", maxPage, curPage);
                    command = sc.nextLine();

                    if (command.startsWith("n")) {
                        if (curPage < maxPage) {
                            curPage++;
                            printPage(f, curPage);
                        }
                    }
                    else if (command.startsWith("p")) {
                        if (curPage > 0) {
                            curPage--;
                            printPage(f, curPage);
                        }
                    }
                    else if (command.startsWith("h")) {
                        workingWithFileHelp();
                    }
                    else if (command.startsWith("e")) {
                        return;
                    }
                    else {
                        try {
                            int temp = Integer.parseInt(command);
                            if (temp >= 0 && temp <= maxPage) {
                                curPage = temp;
                                printPage(f, curPage);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid command");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("File not available");
            }
        } else {
            System.out.println("Invalid file!\n");
        }
    }

    private void printPage(RandomAccessFile f, long page) throws IOException {
        int b;
        long i = 0;
        f.seek(page * pageSize);
        while ((b = f.read()) != -1 && i < pageSize) {
            System.out.write(b);
            i++;
        }
    }

    private void printHelp() {
        System.out.println("/open <file name> \t\t- open file");
        System.out.println("/set <count> \t\t- set page size");
        System.out.println("/help \t\t- print this message");
        System.out.println("/exit \t\t- exit");
    }

    private void workingWithFileHelp() {
        System.out.println("((min)-(max) (current):");
        System.out.println("n \t\t- next page");
        System.out.println("p \t\t- previous page");
        System.out.println("<number> \t\t- page number");
        System.out.println("h \t\t- print this message");
        System.out.println("e \t\t- exit");
    }
}
