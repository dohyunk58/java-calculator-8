package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    @DisplayName("기본 구분자를 사용한 덧셈 출력 테스트")
    void defaultDelimiter() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("커스텀 구분자를 사용한 덧셈 출력 테스트")
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1;2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("빈 문자열 입력 시 0을 출력하는 테스트")
    void emptyInput_printsZero() {
        assertSimpleTest(() -> {
            run("\n"); // Console.readLine() 메서드 특성으로 ""을 입력하면 작동하지 않습니다
            assertThat(output()).contains("결과 : 0");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1,2,3", "1,2,", "//1\\n1,2,3", "1,,2", "//123"})
    @DisplayName("여러 예외 케이스에 대한 IllegalArgumentException 발생 테스트")
    void 예외_테스트(String input) {
        assertThatThrownBy(() -> runException(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    public void runMain() {
        try {
            Application.main(new String[]{});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
