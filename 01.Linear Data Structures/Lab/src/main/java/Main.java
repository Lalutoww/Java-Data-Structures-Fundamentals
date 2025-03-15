import implementations.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> customArrayList = new ArrayList<>();
        customArrayList.add(0);
        customArrayList.add(1);
        customArrayList.add(2);
        customArrayList.add(3);
        customArrayList.add(4);
        customArrayList.add(5);
        System.out.println(customArrayList.size()); // 6

        customArrayList.add(2, 5); // [0, 1, 5, 2, 3, 4, 5]

    }
}
