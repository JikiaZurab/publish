import java.util.Random;
import java.util.Scanner;

class Matrix { //класс для работы с матрицами
    int[][] a, b, c;
    boolean[] aFlags, bFlags;

    public Matrix(int[][] a, int[][] b, int[][] c) {
        this.a = a;
        this.b = b;
        this.c = c;
        aFlags = new boolean[a.length];
        bFlags = new boolean[b.length];
    }
}

class Matrix1 extends Thread {
    int n;
    Matrix matrix;
    Scanner scanner = new Scanner(System.in);

    public Matrix1(Matrix matrix) {
        this.matrix = matrix;
        this.n = matrix.a.length;
    }

    public void run() {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Введите а[" + i +"][" + j + "] элемент >>");
                matrix.a[i][j] = scanner.nextInt();
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            matrix.aFlags[i] = true;
            synchronized (matrix) {
                matrix.notifyAll();
            }
        }
    }
}

class Matrix2 extends Thread {
    int n;
    Matrix matrix;

    public Matrix2(Matrix matrix) {
        this.matrix = matrix;
        this.n = matrix.b.length;
    }

    public void run() {
        Random r = new Random();
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                matrix.b[i][j] = r.nextInt(10);
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            matrix.bFlags[j] = true;
            synchronized (matrix) {
                matrix.notifyAll();
            }
        }
    }
}

class Multiply extends Thread{
    int n;
    Matrix matrix;

    public Multiply(Matrix matrix) {
        this.matrix = matrix;
        this.n = matrix.a.length;
    }


    public void run() {
        for (int row = 0; row < n ; row++) {
            System.out.println();
            for (int col = 0; col < n; col++) {
                matrix.c[row][col] = calcSingleValue(row, col);
                for (int w = 0; w < n; w++ ) { //вывод
                    System.out.println();
                    for (int k = 0; k < n; k++) System.out.print(matrix.c[w][k] + " ");
                }
                System.out.println();
            }
        }
    }

    private int calcSingleValue(int row, int col) {
        int c = 0;
        while (true) {
            if (matrix.aFlags[row] && matrix.bFlags[col]) {
                for (int i = 0; i < n; i++) {
                    c += matrix.a[row][i] * matrix.b[i][col];
                }
                return c;
            } else {
                try {
                    synchronized (matrix) {
                        matrix.wait();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}

public class lab3 {//Запуск и работа с потоками
    static int n = 3;
    public static int[][] multiply(Matrix matrix) {
        //посчитаем сколько строк результирующей матрицы будет считать каждый поток
        //создаем и запускаем потоки
        Matrix1 a = new Matrix1(matrix);
        Matrix2 b = new Matrix2(matrix);
        Multiply c = new Multiply(matrix);

        a.start();
        b.start();
        c.start();
        //ждем завершения
        try {
            a.join();
            b.join();
            c.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        return matrix.c;
    }

    public static void main(String[] args) {
        Random r = new Random();
        int[][] a = new int[n][n];//создание матрицы а
        int[][] b = new int[n][n];//создание матрицы b
        int[][] c = new int[n][n];//создание результирущей матрицы
        Matrix matrix = new Matrix(a, b, c);//создание объекта класса Matrix
        c = multiply(matrix);
        System.out.println("C end mat = ");
        for (int i  = 0; i < n; i++) {//вывод результ матрицы
            System.out.print("        ");
            for (int j = 0; j < n; j++)
                System.out.print(c[i][j] + " ");
            System.out.println();
        }
    }
}
