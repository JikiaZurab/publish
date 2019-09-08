import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexems {
    static Map<String, String> lexems = new HashMap();

    public Lexems() {
        lexems.put("VAR", "^[a-z]+$");
        lexems.put("NUM", "^0|[1-9][0-9]*$");
        lexems.put("OP", "^[-|+|/|*]$");
        lexems.put("ASSIGN_OP", "^=$");

        lexems.put("LOG_OP", "^[==|<|>|>=|<=|!=]$");
        lexems.put("WHILE", "^While$");
        lexems.put("OPEN_BR", "^[/(]$");
        lexems.put("CLOSE_BR", "^[/)]$");
        lexems.put("OPEN_BRACE", "^[/{]$");
        lexems.put("CLOSE_BRACE", "^[/}]$");

        lexems.put("END", "^[;]$");

    }

    void sort(String test, Queue<Token> tokens) {

        String str_1 = "";
        for(int i = 0; i < test.length(); ++i) {   //алгоритм прохода по выражению

            if (test.toCharArray()[i] != ' ') {

                str_1 = str_1 + test.toCharArray()[i];

                String str_2 = "";

                if (i < test.length() - 1) {
                    str_2 = str_1 + test.toCharArray()[i + 1];
                }

                Iterator it = lexems.keySet().iterator(); // поочередно проходим по элементам

                while(it.hasNext()) {  //есть ли следующий элемент и не достигнут ли конец коллекции(true/false)

                    String key = (String)it.next(); // возвращает следующий элемент в коллекции.

                    Pattern p = Pattern.compile((String)lexems.get(key)); //получение операторов из Lexems
                    Matcher m_1 = p.matcher(str_1);       //          проверка правильности строки 1
                    Matcher m_2 = p.matcher(str_2); // проверка правильности строки 2

                    if (m_1.find() && !m_2.find()) {

                        tokens.add(new Token(key, str_1));
                        str_1 = "";

                    }

                }
            }

        }

    }
}
