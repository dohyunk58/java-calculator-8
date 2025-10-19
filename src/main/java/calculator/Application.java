package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Application {
    public static void main(String[] args) throws IOException {
        // 출력을 위한 BufferedWriter 객체 선언
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write("덧셈할 문자열을 입력해 주세요.\n");
        bw.flush();

        // 입력값을 저장할 변수 str
        String str = Console.readLine();

        StringCalculator calculator = new StringCalculator();
        long result = calculator.add(str);

        bw.write("결과 : " + result);
        bw.flush();
        bw.close();
    }
}
