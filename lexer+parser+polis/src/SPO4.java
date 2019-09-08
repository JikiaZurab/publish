import java.util.*;

public class SPO4 {
    public SPO4() {

    }

    public static void main(String[] args) throws Exception {
        Lexems lex = new Lexems();
        Queue<Token> tokens = new LinkedList();


        String test =   "a = 2 + 3; b = a + 1;  While(a<10) { a = a + 10;}  While(b<10) { b = b + 5; }"; //регулярное выражение

        //
        // ЛЕКСЕР


        lex.sort(test, tokens);


        for (Token item : tokens){
            System.out.println(item.type + " " + item.token);
        }

        Queue<Token> new_tokens = new LinkedList(tokens);

        //
        // ПАРСЕР
        //

        Parser parser = new Parser();
        parser.parse(tokens);


        //
        // ПРЕОБРАЗОВАНИЕ В ПОЛИЗ
        //

        Polis p = new Polis(new_tokens);
        List<Token> polis;
        polis = p.set_polis();

        for (int i = 0; i<polis.size(); i++){
            Token item = polis.get(i);
            System.out.println(item.type + " " + item.token);
        }
        for (Token item : polis){

        }

        //
        // ВЫЧИСЛЕНИЕ ПОЛИЗА
        //

        CP cp = new CP(polis);

        cp.calculation();



    }
}
