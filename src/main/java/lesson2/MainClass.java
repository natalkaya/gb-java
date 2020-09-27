package lesson2;

import lesson2.MyException.MyArrayDataException;
import lesson2.MyException.MyArrayException;

/**
 * Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4.
 * При подаче массива другого размера необходимо бросить исключение MyArraySizeException.
 * Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать.
 * Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
 * должно быть брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
 * В методе main() вызвать полученный метод, обработать возможные исключения MyArraySizeException и MyArrayDataException
 * и вывести результат расчета.
 */

public class MainClass {

    public static void setArray(String[][] array) throws MyArrayException, MyArrayDataException {
        if (array.length != MyArrayException.ARRAY_SIZE) {
            throw new MyArrayException();
        } else {
            System.out.println("parsed array:");
            for (String[] strings : array) {
                for (int j = 0; j < array.length; j++) {
                    try {
                        System.out.println(Integer.parseInt(strings[j]));
                    } catch (NumberFormatException ex) {
                        throw new MyArrayDataException();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        final String[][] arr3x4 = {
                {"1", "1", "1", "1"},
                {"2", "2", "2", "2"},
                {"3", "3", "3", "3"}
        };

        final String[][] arr4x4 = {
                {"1", "1", "1", "1"},
                {"2", "aaaaa2", "2", "2"},
                {"3", "3", "3", "3"},
                {"4", "4", "4", "4asd"}
        };


        try {
            setArray(arr3x4);
        } catch (MyArrayException | MyArrayDataException e) {
            System.out.println("error occurs: " + e.getMessage());
        }

        try {
            setArray(arr4x4);
        } catch (MyArrayException | MyArrayDataException e) {
            System.out.println("error occurs: " + e.getMessage());
        }
    }
}
