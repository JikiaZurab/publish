import java.util.Scanner;

public class PP_1 {

    static My_all_threads tr;


    public  static  void  main(String args[]){

        Scanner in = new Scanner(System.in);

        System.out.println("Введите количество потоков: ");
        int threads = in.nextInt();

        tr = new My_all_threads(threads);
        tr.run();
    }
}













































