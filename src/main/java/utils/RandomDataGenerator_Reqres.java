package utils;

import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataGenerator_Reqres {

    public static Faker faker = new Faker();

    public static String getRandomDataFor(RandomDataTypeNames_Reqres dataTypesNames) {
        switch (dataTypesNames) {
            case NAME:
                return faker.name().name();
            case JOB:
                return faker.job().title();
            case EMAIL:
                return faker.internet().emailAddress();
            case PASSWORD:
                return faker.internet().password();
            default:
                return "Data type name not available";
        }
    }

    public static String getRandomNumber(int count) {
        return faker.number().digits(count);
    }

    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String getRandomAlphabets(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public static String getRandomWebsiteName() {
        return "https://" + RandomDataGenerator_Reqres.getRandomAlphabets(10) + ".com";
    }
}
