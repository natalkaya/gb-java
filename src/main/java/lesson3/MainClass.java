package lesson3;

import java.util.*;

/**
 * 1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать, сколько раз встречается каждое слово.
 * <p>
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер
 * телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 */
public class MainClass {

    public static List<String> words = new ArrayList<>(Arrays.asList(
            "android", "world", "hi", "new",
            "array", "lesson", "hi", "new",
            "Java", "main", "argument", "new"
    ));

    public static void main(String[] args) {
        Set<String> uniqueWords = new HashSet<>(words);
        System.out.println("Unique words: " + uniqueWords);

        Map<String, Integer> wordPerCount = new HashMap<>();

        for (String word : words) {
            Integer currentWordCount = wordPerCount.getOrDefault(word, 1);
            wordPerCount.put(word, ++currentWordCount);
        }

        System.out.println("Words count: " + wordPerCount);

        System.out.println("Task 2");
        PhoneDictionary phoneDictionary = new PhoneDictionary();
        phoneDictionary.add("Ivanov", "+78120000000");
        phoneDictionary.add("Ivanov", "+78120000001");
        phoneDictionary.add("Petrova", "+78120000002");
        phoneDictionary.add("Sidorov", "+78120000003");
        phoneDictionary.add("Sidorov", "+78120000004");

        System.out.println(phoneDictionary.get("Ivanov"));
        System.out.println(phoneDictionary.get("Petrova"));
        System.out.println(phoneDictionary.get("Sidorov"));
        System.out.println(phoneDictionary.get("Sidorova"));
    }
}
