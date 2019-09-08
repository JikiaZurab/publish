import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Polis {    //преобразование в полис

    List<Token> polis = new LinkedList<>();

    Queue<Token> tokens;
    Token token;

    Polis(Queue<Token> _tokens){
        tokens = _tokens;
    }

    List<Token> set_polis(){

        while (!tokens.isEmpty()){ //пока есть токен
            expr();
        }

        return polis;

    }

    // обработчик выражения
    void expr(){

        token = tokens.poll();

        if(token.type == "VAR"){
            var();
        }

        if(token.type == "WHILE"){
            cycle();
        }
    }

    // обработчик переменной/числа
    void var(){
        polis.add(token);             //добавили переменную
        polis.add(tokens.poll());     //добавили =
        polis.add(tokens.poll());     //добавили 1 элемент правой части


        token = tokens.peek();

        while (token.type != "END"){                       //если  правая честь не заканчивается после 1 элемента

            token = tokens.poll();
            polis.add(tokens.poll());
            polis.add(token);

            token = tokens.peek();

        }

        token = tokens.poll();
    }

    // обработчик цикла
    void cycle(){

        int cycle_start = polis.size();
        log_op();
        token = tokens.poll();              //открывающая скобка цикла
        int p = p();
        polis.add(new Token("JUMP", "" + (p-1)));
        polis.add(new Token("IF", "!F"));


        expr();

        polis.add(new Token("JUMP", "" + (cycle_start-1)));
        polis.add(new Token("IF", "!F"));
        token = tokens.poll();              //закрывающая скобка цикла
    }
    // обработчик логической операции
    void log_op(){
        token = tokens.poll();          //открывающая скобка лог выражения
        polis.add(tokens.poll()); //1 элемент лог выражения
        token = tokens.poll();          //лог операция
        polis.add(tokens.poll()); //2 элемент лог выражения
        polis.add(token);

        token = tokens.peek();
        while (token.type != "CLOSE_BR"){
            token = tokens.poll();
            polis.add(tokens.poll());
            polis.add(token);
            token = tokens.peek();
        }
        token = tokens.poll();          //закрывающая скобка лог выражения
    }

    // индекс перехода в цикле
    int p(){

        int p = polis.size();

        Queue<Token> newtokens = new LinkedList<>(tokens);
        Token newtoken = newtokens.poll();

        while (newtoken.type != "CLOSE_BRACE"){
            newtoken = newtokens.poll();
            p++;
        }
        p++;
        return p;
    }

}
