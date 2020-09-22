package lesson1.objects;

public class Human extends Participant implements Actionable {
    public Human(String name, Integer maxRunDistance, Integer maxJumpHeight) {
        super(name, maxRunDistance, maxJumpHeight);
    }
}
