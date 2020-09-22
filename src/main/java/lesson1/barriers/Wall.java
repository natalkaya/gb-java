package lesson1.barriers;

public class Wall extends Barriers implements Resultable {

    public Wall(String name, Integer height) {
        super(name, height);
    }

    @Override
    public void isSucceedAction(int size) {
        if (size > this.size) System.out.println("Jumping succeed");
        else System.out.println("Jumping failed");
    }

}
