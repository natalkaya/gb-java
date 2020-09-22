package lesson1.objects;

public class Robot extends Participant implements Actionable {
    public Robot(String name, Integer maxRunDistance, Integer maxJumpHeight) {
        super(name, maxRunDistance, maxJumpHeight);
    }
}
