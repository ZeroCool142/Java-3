package ru.geekbrains.java3.l2;

import java.sql.*;
import java.util.Random;

public class SQLHandler implements AutoCloseable {
    /**
     * 1. Сформировать таблицу товаров (id, prodid, title, cost) запросом из Java приложения.
     * id - порядковый номер записи, первичный ключ
     * prodid - уникальный номер товара
     * title - название товара
     * cost - стоимость
     * <p>
     * 2. При запуске приложения очистить таблицу и заполнить 10.000 товаров вида:
     * id_товара 1 товар1 10
     * id_товара 2 товар2 20
     * id_товара 3 товар3 30
     * ...
     * id_товара 10000 товар10000 100010
     *
     * 3. Написать консольное приложение, которое позволяет узнать цену товара по его имени, либо
     * вывести сообщение "Такого товара нет" если товара не в базе.
     * Консольная команда: "/цена товар545"
     *
     * 4. Добавить возможность изменения цены товара (указываем имя товара и новую цену).
     * Консольная команда: "/сменитьцену товар10 10000"
     *
     * 5. Вывести товары в заданном ценовом диапазоне. Консольная команда: "/товарыпоцене 100 600"
     */
    private Connection connection;
    private Statement stmt;
    private PreparedStatement goodsInPriceRange;
    private PreparedStatement goodPrice;
    private PreparedStatement updatePrice;
    private PreparedStatement addGood;

    public SQLHandler(String dbName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        createTable();
        addGood = connection.prepareStatement("INSERT INTO products (good_id, title, cost) values (?, ?, ?);");
        goodPrice = connection.prepareStatement("SELECT cost FROM products WHERE title=?;");
        updatePrice = connection.prepareStatement("UPDATE products SET cost = ? WHERE title = ?;");
        goodsInPriceRange = connection.prepareStatement("SELECT good_id, title, cost " +
                                                        "FROM products " +
                                                        "WHERE cost >= ? AND cost <= ? " +
                                                        "ORDER BY cost;");
    }


    private void createTable() throws SQLException {
        stmt = connection.createStatement();

        stmt.execute("DROP TABLE IF EXISTS products;");

        stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "good_id INTEGER UNIQUE," +
                "title TEXT," +
                "cost REAL" +
                ");");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public void initGoods(int count) throws SQLException {
        //"INSERT INTO products (goods_id, title, cost) values (?, ?, ?)"
        Random rnd = new Random();
        connection.setAutoCommit(false);
        for (int i = 0; i < count; i++) {
            addGood.setInt(1, 10000 + i);
            addGood.setString(2, "good_" + i);
            addGood.setFloat(3, rnd.nextFloat() * 100);
            addGood.addBatch();
        }
        addGood.executeBatch();
        connection.setAutoCommit(true);
    }

    public ResultSet showAll() throws SQLException {
        return stmt.executeQuery("SELECT * FROM products;");
    }

    public float price(String title) throws SQLException {
        //SELECT price FROM products WHERE title=?

        goodPrice.setString(1, title);
        ResultSet rs = goodPrice.executeQuery();

        if (rs.next()) {
            return rs.getFloat(1);

        } else return -1;
    }

    public int changePrice(String goodsName, float cost) throws SQLException {
        //UPDATE products SET cost = ? WHERE title = ?;
        updatePrice.setFloat(1, cost);
        updatePrice.setString(2, goodsName);

        return updatePrice.executeUpdate();

    }

    public ResultSet showInRange(float min, float max) throws SQLException {
        //SELECT good_id, title, price FROM products WHERE price >= ? AND price <= ? ORDER BY price;
        if (min > max) {
            float t = min;
            min = max;
            max = t;
        }
        goodsInPriceRange.setFloat(1, min);
        goodsInPriceRange.setFloat(2, max);

        return goodsInPriceRange.executeQuery();
    }
}
