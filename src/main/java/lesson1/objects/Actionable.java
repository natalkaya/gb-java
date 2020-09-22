package lesson1.objects;

public interface Actionable {
    default int run(int distance) {
        System.out.printf("runs %d\n", distance);
        return distance;
    }

    default int jump(int height) {
        System.out.printf("jumps %d\n", height);
        return height;
    }
}
