import java.util.*;

public class CP {

    List<Token> polis;
    List<Digit> var_table = new LinkedList<>();

    Stack<Token> stack = new Stack<>();

    CP(List<Token> _polis){
        polis = _polis;
    }

    //вычисление полиса
    void calculation(){

        Token var = new Token();
        boolean sw = false; // переход

        int polis_size = polis.size();
        for (int i = 0; i < polis_size; i++){

            Token item = polis.get(i);

            if(item.type == "VAR" || item.type == "NUM" || item.type == "JUMP"){ //указывают ли ссылки на один и тот-же объект
                stack.add(item); // добавляем объект
            }

            if(item.type == "ASSIGN_OP"){
                var = stack.pop(); //Извлекает верхний элемент удаляя его из стека.
            }

            if(item.type == "OP"){
                ops(var.token, stack.pop().token, stack.pop().token, item.token);
            }

            if (item.type == "LOG_OP"){
                sw = log_ops(item.token, stack.pop().token, stack.pop().token);
            }


            if (item.type == "IF"){

                System.out.println("\n" + !sw + "");
                for (Digit itemm : var_table){
                    System.out.println(itemm.name + " " + itemm.value);
                }



                int jp = Integer.parseInt(stack.pop().token);

                if(jp > i){        // если кол-во переходов больше
                    if(!sw){
                        i = jp;
                        System.out.println("\nreturn \n");
                    }
                }


            }
        }

    }

    //вычисление арифметического выражения
    void ops(String var, String first , String second, String op){


        double _first, _second;


        try {
            _first = Double.parseDouble(first); //отслеживаются ошибки
        }catch (Exception var2){
            _first = find_var(first); //  обрабатываем ошибку
        }

        try {
            _second = Double.parseDouble(second);
        }catch (Exception var2){
            _second = find_var(second);
        }

        double result = 0;

        switch (op){
            case "+":
                result = _first + _second;
                break;
            case "-":
                result = _first - _second;
                break;
            case "*":
                result = _first * _second;
                break;
            case "/":
                result = _first / _second;
                break;

        }
        stack.add(new Token("NUM", String.valueOf(result)));
        if(contain_var(var))
            change_var(var, result);
        else
            var_table.add(new Digit( var, result));
    }

    //вычисление логического выражения
    boolean log_ops(String op, String second, String first) {
        double _first, _second;

        try {
            _first = Double.parseDouble(first);
        } catch (Exception var2) {
            _first = find_var(first);
        }

        try {
            _second = Double.parseDouble(second);
        } catch (Exception var2) {
            _second = find_var(second);
        }

        boolean result = false;

        switch (op) {
            case "==":
                result = _second == _first;
            break;
            case ">":
                result = _first > _second;
                break;
            case "<":
                result = _first < _second;
                break;
            case ">=":
                result = _first >= _second;
                break;
            case "<=":
                result = _first <= _second;
                break;
            case "!=":
                result = _first != _second;
                break;
        }
        return result;

    }

    //возвращает значение переменной var из таблицы переменных
    double find_var(String var){

        for (Digit item : var_table){
            if(item.name.equals(var))
                return item.value;
        }

        return 0;
    }

    // проверяет есть ли переменная var в таблице переменных
    boolean contain_var(String var){
        for (Digit item : var_table){
            if(item.name.equals(var) )
                return true;
        }

        return false;
    }

    //меняет значение переменной var
    void change_var(String var, double value){
        for (Digit item : var_table){
            if(item.name.equals(var))
                item.value = value;
        }
    }
}
