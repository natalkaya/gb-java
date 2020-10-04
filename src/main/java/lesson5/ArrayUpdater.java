package lesson5;

public class ArrayUpdater {

    public static final int SIZE = 10000000;
    public static final int H = SIZE / 2;

    private static float getUpdatedValue(float currentVal, int index) {
        return (float) (currentVal * Math.sin(0.2f + 5 / (float) index) * Math.cos(0.2f + index / (float) 5) * Math.cos(0.4f + index / (float) 2));
    }

    public static float[] updateByOneArr(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getUpdatedValue(arr[i], i);
        }
        System.out.println(System.currentTimeMillis() - a);
        return arr;
    }


    public static float[] updateByTwoArr(float[] arr) {
        long a = System.currentTimeMillis();
        float[] a1 = new float[H];
        float[] a2 = new float[H];

        System.arraycopy(arr, 0, a1, 0, H);
        System.arraycopy(arr, H, a2, 0, H);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < H; i++) {
                a1[i] = getUpdatedValue(a1[i], i);
            }
        });

        Thread t2 = new Thread(() -> {
            int j = H;
            while (j < SIZE) {
                for (int i = 0; i < H; i++) {
                    a2[i] = getUpdatedValue(a2[i], j);
                    j++;
                }
            }
        });

        t1.start();
        t2.start();

        System.arraycopy(a1, 0, arr, 0, H);
        System.arraycopy(a2, 0, arr, H, H);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - a);
        return arr;
    }

}

