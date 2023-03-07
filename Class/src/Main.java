import java.util.List;
import java.util.Scanner;

public class Main {
       public static void main(String[] args) throws Exception {

            String[] intString = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            String[] rimString = {"I", "V", "X", "L", "C", "D", "M"};


            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine(); // 10+11 (без исключения) или 10+11+9(с исключением)
            String[] splitString = text.split(""); //[1,0,+,X]


            /////////////////////////////////////////////////////////////////////////////////////////
            // 10+X [1,0,+,X]
            // С консоли приходит текст
            // Нужно пройтись по тексту используя каждое значение из массивов ints и rimString на нахождение нужных значений
            // Как понять что в тексте есть и цифры и римские буквы?
            boolean intsBoolean = false;
            boolean rimBoolean = false;
            for (int splitStringIndex = 0; splitStringIndex < text.length(); splitStringIndex++) { //[1,0,+,X]
                for (String s : intString) {
                    if (splitString[splitStringIndex].equals(s)) {
                        intsBoolean = true;
                        break;
                    }
                }
                for (String s : rimString) {
                    if (splitString[splitStringIndex].equals(s)) {
                        rimBoolean = true;
                        break;
                    }
                }
            }
            if (intsBoolean && rimBoolean) {
                throw new Exception("используются одновременно разные системы счисления");
            }
            /////////////////////////////////////////////////////////////////////////////////////////

            int indexPlus = text.indexOf("+");
            int indexMinus = text.indexOf("-");
            if (indexPlus == -1 && indexMinus == -1) {
                throw new Exception("строка не является математической операцией");
            }
            if (indexPlus > 0) { // Если + найден в тексте входим внутрь if блока
                calc(text, "+", indexPlus, rimBoolean);
            }
            if (indexMinus > 0) { // Если - найден в тексте входим внутрь if блока
                calc(text, "-", indexMinus, rimBoolean);
            }
        }

        public static void calc(String text, String operator, int indexOperator, boolean rimBoolean) throws Exception {
            int lastIndexOfPlus = text.lastIndexOf(operator); // Пытаемся найти любой другой (+, -, /, *) в тексте.
            if (indexOperator != lastIndexOfPlus) { // Если (+, -, /, *) найден под другим индексом, кидаем исключение
                throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }
            String substring1 = text.substring(0, text.indexOf(operator)); // Находим подстроку в строке до оператора
            String substring2 = text.substring(text.indexOf(operator) + 1); // Находим подстроку в строке после оператора
            int number1;
            int number2;
            if (rimBoolean) { // Если true то конвертим римские цифры в обычные
                number1 = romanToArabic(substring1);
                number2 = romanToArabic(substring2);
            } else { // Иначе работаем с обычными цифрами
                number1 = Integer.parseInt(substring1);
                number2 = Integer.parseInt(substring2);
            }
            switch (operator) {
                case "+":
                    System.out.println(number1 + number2);
                    break;
                case "-":
                    int result = number1 - number2;
                    if (rimBoolean && result < 0) {
//
                        throw new Exception("в римской системе нет отрицательных чисел");
                    }
                    System.out.println(result);
                    break;
                case "/":
                    System.out.println(number1 / number2);
                    break;
                case "*":
                    System.out.println(number1 * number2);
                    break;
            }
        }

        public static int romanToArabic(String input) {
            String romanNumeral = input.toUpperCase();
            int result = 0;
            List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
            int i = 0;
            while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
                RomanNumeral symbol = romanNumerals.get(i);
                if (romanNumeral.startsWith(symbol.name())) {
                    result += symbol.getValue();
                    romanNumeral = romanNumeral.substring(symbol.name().length());
                } else {
                    i++;
                }
            }
            if (romanNumeral.length() > 0) {
                throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
            }
            return result;
        }
    }
