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

        // 입력값 검증
        if(str == null) throw new IllegalArgumentException("문자열은 null일 수 없습니다");

        String customDelimiter = null;
        // 커스텀 구분자가 있는 경우
        if(str.startsWith("//")) {
            int endIndexOfDelimiter = str.indexOf("\\n");

            // 커스텀 구분자의 끝을 알리는 \n 가 없는 경우
            if(endIndexOfDelimiter == -1) throw new IllegalArgumentException("커스텀 구분자 형식 오류: '\\n'이 없습니다");

            // 커스텀 구분자 추가
            customDelimiter = str.substring(2, endIndexOfDelimiter);

            // 커스텀 구분자에 숫자가 포함되어 있는지 확인
            char[] charArr = customDelimiter.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                if (Character.isDigit(charArr[i])) throw new IllegalArgumentException("커스텀 구분자 오류: 숫자는 불가합니다");
            }
        }

        bw.write("str: "+str+"\n구분자: "+customDelimiter+"\n");
        bw.flush();
        bw.close();
    }
}
