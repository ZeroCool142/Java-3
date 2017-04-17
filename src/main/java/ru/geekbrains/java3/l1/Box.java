package ru.geekbrains.java3.l1;

import ru.geekbrains.java3.l1.fruits.Fruit;

import java.util.ArrayList;

/**
 * Сделать метод {@link #getWeight()} который высчитывает вес коробки, зная кол-во фруктов и вес
 * одного фрукта;
 * <p>
 * Сделать метод {@link #compare(Box)}, который позволяет сравнить текущую коробку
 * с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в
 * противном случае;
 * <p>
 * Написать метод {@link #shift(Box)}, который позволяет пересыпать фрукты из текущей коробки в другую коробку,
 * соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты,
 * которые были в этой коробке;
 */

public class Box<T extends Fruit> {

    private ArrayList<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    /**
     * Put a fruit into box
     *
     * @param fruit
     */
    public void put(T fruit) {
        fruits.add(fruit);
    }

    /**
     * Put fruits to another box
     *
     * @param destBox the destination box
     */
    public void shift(Box<T> destBox) {
        destBox.fruits.addAll(fruits);
        fruits.clear();
    }

    /**
     * Get weight of the box
     *
     * @return weight of box
     */
    public float getWeight() {
        if (fruits.isEmpty()) return 0;
        return fruits.size() * fruits.get(0).getWeight();
    }

    /**
     * Compare two boxes by weight
     *
     * @param anotherBox
     * @return true if weight equals, else - false
     */
    public boolean compare(Box<?> anotherBox) {
        return getWeight() == anotherBox.getWeight();
    }
}
