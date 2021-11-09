package ru.yandex.market;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestsArguments {

    public static Stream<Arguments> searchNotebooksProvider(){
        return Stream.of(
                Arguments.of("Маркет", "Электроника", "Ноутбуки",
                        10000, 30000, Arrays.asList("HP", "Lenovo"), 12)
        );
    }
}
