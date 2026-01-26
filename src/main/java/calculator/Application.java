package calculator;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해 주세요.");

        // 입력값을 저장할 변수 str
        String str = Console.readLine();

        StringCalculator calculator = new StringCalculator();
        long result = calculator.add(str);

        System.out.print("결과 : " + result);
    }
}
