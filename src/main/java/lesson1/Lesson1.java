package lesson1;

import lesson1.barriers.Barriers;
import lesson1.barriers.Treadmill;
import lesson1.barriers.Wall;
import lesson1.objects.Cat;
import lesson1.objects.Human;
import lesson1.objects.Participant;
import lesson1.objects.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 1. Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса. Эти классы должны уметь бегать
 * и прыгать (методы просто выводят информацию о действии в консоль).
 * 2. Создайте два класса: беговая дорожка и стена, при прохождении через которые, участники должны выполнять
 * соответствующие действия (бежать или прыгать), результат выполнения печатаем в консоль (успешно пробежал, не смог пробежать и т.д.).
 * 3. Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор препятствий.
 * 4*. У препятствий есть длина (для дорожки) или высота (для стены), а участников ограничения на бег и прыжки. Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.
 * 5* . Выполнить задание с перечислениями( файл прикреплен в материалах)
 */
public class Lesson1 {
    public static void main(String[] args) {
        Random random = new Random();
        final int boundDistance = 30;
        final int boundHeight = 5;

        final int boundWallHeight = 7;
        final int boundRunDistance = 42;

        List<Participant> participants = new ArrayList<>(List.of(
                new Cat("Barsik", random.nextInt(boundDistance), random.nextInt(boundHeight)),
                new Human("BraveMan", random.nextInt(boundDistance), random.nextInt(boundHeight)),
                new Robot("Robocop", random.nextInt(boundDistance), random.nextInt(boundHeight))
        ));

        List<Barriers> barriers = new ArrayList<>(List.of(
                new Wall("Wall#1", random.nextInt(boundWallHeight)),
                new Treadmill("Treadmill#1", random.nextInt(boundRunDistance)),
                new Wall("Wall#2", random.nextInt(boundWallHeight)),
                new Treadmill("Treadmill#2", random.nextInt(boundRunDistance))
        ));

        participants.forEach(participant -> {
            System.out.println(participant);
            barriers.forEach(barrier -> {
                if (barrier instanceof Wall) {
                    participant.jump(barrier.getSize());
                    barrier.isSucceedAction(participant.getMaxJumpHeight());
                } else if (barrier instanceof Treadmill) {
                    participant.run(barrier.getSize());
                    barrier.isSucceedAction(participant.getMaxRunDistance());
                }
            });
        });

    }
}
