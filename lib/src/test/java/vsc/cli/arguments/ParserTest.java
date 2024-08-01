package vsc.cli.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private static Stream<Arguments> parseArgumentsHandlesValidInput() {
        return Stream.of(
                Arguments.of(new String[] {"--key=value"}, List.of("key"), List.of("value")),
                Arguments.of(new String[] {"--key=value", "--key2=value2"}, List.of("key", "key2"), List.of("value", "value2")),
                Arguments.of(new String[] {"--key=value", "--key2=value2", "-f"}, List.of("key", "key2"), List.of("value", "value2"))
        );
    }

    @ParameterizedTest 
    @MethodSource
    void parseArgumentsHandlesValidInput(String[] args, List<String> keys, List<String> values) {
        var result = Parser.parseArguments(args);
        for (int i= 0; i < keys.size(); i++) {
            assertEquals(result.get(keys.get(i)), values.get(i));
        }
    }
    
    @Test
    void parseArgumentsHandlesInvalidInput() {
        var args = new String[] { "--key=" };
        var result = Parser.parseArguments(args);
        assertEquals(result.get("key"), null);
    }
    
    private static Stream<Arguments> parseFlagsHandlesValidInput() {
        return Stream.of(
                Arguments.of(new String[] {"-f"}, List.of("f")),
                Arguments.of(new String[] {"--key=value", "-f"}, List.of("f"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void parseFlagsHandlesValidInput(String[] args, List<String> flags) {
        var result = Parser.parseFlags(args);
        for (int i= 0; i < flags.size(); i++) {
            assertEquals(result.get(flags.get(i)), true);
        }
    }
    
    @Test
    void parseFlagsHandlesInvalidInput() {
        var args = new String[] { "f" };
        var result = Parser.parseFlags(args);
        assertEquals(result.get("f"), null);
    }
}
