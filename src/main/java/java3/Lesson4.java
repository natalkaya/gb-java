package java3;

/**
 * Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
 * Используйте wait/notify/notifyAll.
 */
public class Lesson4 {
    private final Object mon = new Object();
    final int count = 5;
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Lesson4 l = new Lesson4();
        Thread t1 = new Thread(l::printA);
        Thread t2 = new Thread(l::printB);
        Thread t3 = new Thread(l::printC);

        t1.start();
        t2.start();
        t3.start();
    }

    void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'A') {
                        mon.wait();
                    }
                    System.out.print('A');
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'B') {
                        mon.wait();
                    }
                    System.out.print('B');
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != 'C') {
                        mon.wait();
                    }
                    System.out.print('C');
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
