package lesson1.objects;

public abstract class Participant implements Actionable {
    protected final String name;
    private final Integer maxRunDistance;
    private final Integer maxJumpHeight;

    public Integer getMaxRunDistance() {
        return maxRunDistance;
    }

    public Integer getMaxJumpHeight() {
        return maxJumpHeight;
    }

    protected Participant(String name, Integer maxRunDistance, Integer maxJumpHeight) {
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public String toString() {
        return this.name + ": ";
    }
}
