package lesson1.barriers;

public class Treadmill extends Barriers implements Resultable {

    public Treadmill(String name, Integer length) {
        super(name, length);
    }

    @Override
    public void isSucceedAction(int size) {
        if (size > this.size) System.out.println("Running succeed");
        else System.out.println("Running failed");
    }
}
