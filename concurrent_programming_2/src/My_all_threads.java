import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class My_all_threads { //класс реализующиий все методы 

    static int n = 1000; //размерность матрицы 
    static Random rand = new Random();

    int[][] A; //начальная матрица А
    int[][] B; //начальная матрица Б
    int[][] C; //результирующая матрица

    int threads;

    long m_start;     //переменная с началом запуска потоков

    public ArrayList<Long> times = new ArrayList<>(); //список для храниения времени

    List<Integer> areas = new ArrayList<>();//список для хранения точек

    public My_all_threads(int threads) {
        A = create_matrix(); //заполнение матрицы А
        B = create_matrix(); //заполнение матрицы Б
        C = new int[n][]; //инициализация результирующей матрицы
        for (int i = 0; i < n; i++)
        {
            C[i] = new int[n];
        }

        this.threads = threads;

        int n_2 = n/threads; //делим размерность матрицы на количество потоков
        for (int i = 0; i < threads; i++){ //заполнение списка точками роботы потока
            areas.add(i * n_2);
            areas.add((i + 1) * n_2);
        }
    }

    protected void killer(ArrayList<Thread> threads, int w){ //функция убивающая потоки
        for(int i = 0; i < threads.size(); i++) {
            if(threads.get(i).isAlive()) {
                threads.get(i).interrupt();
                System.out.println(threads.get(i).getName() + " был убит " + threads.get(w).getName());
            }
        }
    }


    public void run() {
        ArrayList<Thread> my_threads = new ArrayList<>(); //список потоков

        
        for (int i = 0; i < areas.size(); i+=2) { //инициализируем потоки
            my_threads.add(new Thread(new Runner(areas.get(i), areas.get(i+1)))); //создание и добавление списка потоков
        }

        for (int i = 0; i < my_threads.size(); i++)
            my_threads.get(i).setName(String.format("Поток номер " + (i+1)));

        m_start = System.currentTimeMillis();
        int k = 0;

        for (Thread thread: my_threads) { //запускаем потоки на выполнение
            thread.start();
        }

        while (k == 0){
            for (int i = 0; i < threads; i++){
                if (!my_threads.get(i).isAlive()){
                    System.out.println(my_threads.get(i).getName() + " убийца");
                    killer(my_threads,i);
                    k++;
                }
            }
        }

    }


    static int[][] create_matrix() //создание матрицы
    {
        int[][] A = new int[n][];
        for (int i = 0; i < n; i++)
        {
            A[i] = new int[n];
            for (int j = 0; j < n; j++)
            {
                A[i][j] = rand.nextInt(100);
            }
        }
        return A;
    }

    private class Runner implements Runnable {

        int start, stop;

        Runner(int start, int stop){ //изменение точек
            this.start = start;
            this.stop = stop;
        }

        @Override
        public void run() { //вычесление частей матрицы
                for (int i = start; i < stop; ++i) {
                    for (int j = 0; j < n; ++j) {
                        C[i][j] = 0;
                        for (int k = 0; k < n; ++k) {
                            if(!Thread.interrupted()) C[i][j] += A[i][k] * B[k][j];
                            else return;
                        }
                    }
                }
        }
    }
}
