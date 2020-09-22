package lesson1;
/**
 * Требуется реализовать enum DayOfWeek, который будет представлять дни недели.
 *         С его помощью необходимо решить задачу определения кол-ва рабочих часов до конца недели по заданному текущему дню.
 *
 *         Возвращает кол-во оставшихся рабочих часов до конца
 *         недели по заданному текущему дню. Считается, что
 *         текущий день ещё не начался, и рабочие часы за него
 *         должны учитываться.
 *         Если заданный день выходной то вывести "Сегодня выходной"
 */


public class DayOfWeekMain {

    public static void main(String[] args) {
        System.out.println(DayOfWeek.getWorkingHours(DayOfWeek.MONDAY));
        System.out.println(DayOfWeek.getWorkingHours(DayOfWeek.FRIDAY));
        System.out.println(DayOfWeek.getWorkingHours(DayOfWeek.SATURDAY));
        System.out.println(DayOfWeek.getWorkingHours(DayOfWeek.SUNDAY));
    }
}
