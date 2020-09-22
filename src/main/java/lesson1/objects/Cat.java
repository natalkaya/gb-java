package lesson1.objects;

public class Cat extends Participant implements Actionable {
    public Cat(String name, Integer maxRunDistance, Integer maxJumpHeight) {
        super(name, maxRunDistance, maxJumpHeight);
    }
}
