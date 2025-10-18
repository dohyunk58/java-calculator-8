package calculator;

import java.util.regex.Pattern;

public class StringCalculator {
    public long add(String str) {
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
            validateCustomDelimiter(customDelimiter);

            str = str.substring(endIndexOfDelimiter + 2);
        }

        // 정규표현식을 사용하기 위한 패턴 구분자 패턴
        String delimiterPattern = ",|:";
        // 커스텀 구분자가 있는 경우 구분자 패턴에 추가, Patter.quote() 사용해 특수문자도 일반 문자열 취급
        if(customDelimiter != null) {
            delimiterPattern = delimiterPattern + "|" + Pattern.quote(customDelimiter);
        }

        // 빈 문자열도 포함되도록 문자열 분리 (ex. "1:2," -> "1", "2", "")
        String[] numbers = str.split(delimiterPattern,-1);

        return sumNumbers(numbers);
    }

    public void validateCustomDelimiter(String customDelimiter) {
        // 커스텀 구분자에 숫자가 포함되어 있는지 확인
        char[] charArr = customDelimiter.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (Character.isDigit(charArr[i])) throw new IllegalArgumentException("커스텀 구분자 오류: 숫자는 불가합니다");
        }
    }

    public long sumNumbers(String[] numbers) {
        /*
         * 숫자 합계 구하기
         * 입력값이 ""이 아닌 경우 아래 if문에 진입하여 숫자별로 sum에 더함
         * 입력값이 ""인 경우, 아래 if문을 넘어 sum이 0으로 출력됨
         */
        long sum = 0;
        if (!(numbers.length == 1 && numbers[0].isEmpty())) {
            for(int i = 0; i < numbers.length; i++) {
                // 구분자 사이 숫자가 없는 경우
                if (numbers[i].isEmpty()) throw new IllegalArgumentException("입력값 오류: 구분자 사이에 숫자가 없습니다");

                // 숫자로 변환할 수 없는 문자가 있는 경우
                long num;
                try {
                    num = Long.parseLong(numbers[i]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("입력값 오류: 숫자로 변환할 수 없는 문자가 포함되어 있습니다");
                }

                // 양수가 아닌 경우
                if (num <= 0) throw new IllegalArgumentException("입력값 오류: "+num+"은 양수가 아닙니다");

                sum += num;
            }
        }

        return sum;
    }
}
