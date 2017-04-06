package ru.geekbrains.java3.l2;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {

    static final String DB_NAME = "goods.db";
    static final String HELP_MSG = "\nUSING:\n" +
            "/price <good title> \t\t for view price of good.\n" +
            "/cprice <good title> <cost> \t\t for change cost of good.\n" +
            "/byprice <min> <max> \t\t select goods in range.\n" +
            "/help \t\t print this message\n" +
            "/exit \t\t for exit.\n";

    public static void main(String[] args) {

        try (SQLHandler db = new SQLHandler(DB_NAME)) {
            db.initGoods(10000);
            Scanner sc = new Scanner(System.in);
            String command;
            System.out.println(HELP_MSG);
            while (true) {
                command = sc.nextLine();
                if (command.startsWith("/price")) {
                    showPrice(db, command);
                } else if (command.startsWith("/cprice")) {
                    changePrice(db, command);
                } else if (command.startsWith("/byprice")) {
                    showByPrice(db, command);
                } else if (command.startsWith("/show")) {
                    showAll(db);
                } else if (command.startsWith("/help")) {
                    System.out.println(HELP_MSG);
                } else if (command.startsWith("/exit")) {
                    System.out.println("Bye bye!");
                    return;
                } else System.out.println("Invalid command!");
            }
        } catch (SQLException e) {
            System.out.println("DataBase ERROR!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Oops! Something went wrong!");
            e.printStackTrace();
        }
    }

    private static void showAll(SQLHandler base) throws SQLException {
        ResultSet rs = base.showAll();
        while (rs.next()) {
            System.out.println(rs.getString("title") +
                    " \t-\t" + rs.getFloat("cost"));
        }
    }

    private static void showByPrice(SQLHandler base, String command) throws SQLException {
        ResultSet rs;
        try {
            String min = command.split("\\s")[1];
            String max = command.split("\\s")[2];
            rs = base.showInRange(Float.parseFloat(min), Float.parseFloat(max));
            while (rs.next()) {
                System.out.println(rs.getInt("good_id") + "\t"
                        + rs.getString("title") + "\t" +
                        rs.getFloat("cost") + "\n");
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            System.out.println("Invalid command!");
        }
    }

    private static void changePrice(SQLHandler base, String command) throws SQLException {
        try {
            String goodsName = command.split("\\s")[1];
            String price = command.split("\\s")[2];
            if (base.changePrice(goodsName, Float.parseFloat(price)) > 0) {
                System.out.println(goodsName + " now cost " + price);
            } else System.out.println("Item not found!");
        } catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            System.out.println("Invalid command!");
        }
    }

    private static void showPrice(SQLHandler base, String command) throws SQLException {
        try {
            command = command.split("\\s")[1];
        } catch (Exception e) {
            System.out.println("Invalid command!");
            return;
        }

        float price = base.price(command);
        if (price >= 0) {
            System.out.println(command + " cost " + price);
        } else System.out.println("Item not exists");
    }

}
