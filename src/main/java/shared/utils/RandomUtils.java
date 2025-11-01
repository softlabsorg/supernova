package shared.utils;

import com.github.curiousoddman.rgxgen.RgxGen;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String generateRandomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    public static String generateRandomString(String regex) {
        return RgxGen.parse(regex).generate();
    }

}
