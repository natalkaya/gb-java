package lesson1.barriers;

public abstract class Barriers implements Resultable {
    protected final String name;
    protected final Integer size;

    public Barriers(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }
}
