package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        // 각 테스트가 실행되기 전에 계산기 객체를 새로 생성
        calculator = new StringCalculator();
    }

    @Test
    @DisplayName("null을 입력하면 IllegalArgumentException 예외가 발생한다")
    void nullInputException() {
        assertThatThrownBy(() -> {
            calculator.add(null);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("문자열은 null일 수 없습니다");
    }

    @Test
    @DisplayName("빈 문자열을 입력하면 0을 반환한다")
    void emptyInput() {
        assertThat(calculator.add("")).isZero();
    }

    @Test
    @DisplayName("숫자 하나만 있는 문자열을 입력하면 해당 숫자를 반환한다")
    void singleNumber() {
        assertThat(calculator.add("5")).isEqualTo(5);
    }

    @Test
    @DisplayName("기본 구분자(쉼표, 콜론)로 분리된 숫자의 합을 반환한다")
    void defaultDelimiters() {
        assertThat(calculator.add("1,2:3")).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자로 분리된 숫자의 합을 반환한다")
    void customDelimiter() {
        assertThat(calculator.add("//;\\n1;2;3")).isEqualTo(6);
    }

    @Test
    @DisplayName("여러 글자로 된 커스텀 구분자를 지원한다")
    void customDelimiterWithMultipleChars() {
        assertThat(calculator.add("//***\\n1***2***3")).isEqualTo(6);
    }

    @Test
    @DisplayName("정규식 특수문자를 커스텀 구분자를 지원한다")
    void customDelimiterWithRegexMetaChar() {
        assertThat(calculator.add("//.\\n1.2.3")).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자만 있고 숫자가 없는 경우 0을 반환한다")
    void customDelimiterWithoutNumbers() {
        assertThat(calculator.add("//;\\n")).isZero();
    }

    @Test
    @DisplayName("문자열에 음수가 포함되어 있으면 예외를 발생시킨다")
    void negativeNumberException() {
        assertThatThrownBy(() -> {
            calculator.add("-1,2,3");
        })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,,2", "1,2,", ",1,2"})
    @DisplayName("구분자만 있거나 구분자로 끝나는 경우 예외를 발생시킨다")
    void invalidDelimiterPlacementException(String input) {
        assertThatThrownBy(() -> calculator.add(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값 오류: 구분자 사이에 숫자가 없습니다");
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 포함된 경우 예외를 발생시킨다")
    void nonNumericInputException() {
        assertThatThrownBy(() -> calculator.add("a,1,2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값 오류: 숫자로 변환할 수 없는 문자가 포함되어 있습니다");
    }

    @Test
    @DisplayName("커스텀 구분자 선언 형식에 '\\n'이 없으면 예외를 발생시킨다")
    void invalidCustomDelimiterFormatException() {
        assertThatThrownBy(() -> calculator.add("//;1;2"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("커스텀 구분자가 숫자인 경우 예외를 발생시킨다")
    void numericCustomDelimiterException() {
        assertThatThrownBy(() -> calculator.add("//1\\n1,2"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}