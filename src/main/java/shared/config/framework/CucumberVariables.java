package shared.config.framework;

import io.cucumber.java.ParameterType;
import io.cucumber.java.Status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CucumberVariables {

    @ParameterType(value = "([0-9]+(?:,[0-9]+)*)")
    public List<Integer> intlist(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @ParameterType(value = "([+-]?[0-9]*\\.[0-9]+)")
    public double doubleparam(String input) {
        return Double.parseDouble(input);
    }

    @ParameterType(value = "(?i:true|false)")
    public boolean bool(String input) {
        return Boolean.parseBoolean(input.toLowerCase());
    }

    @ParameterType(value = "\\d{4}-\\d{2}-\\d{2}")
    public LocalDate date(String input) {
        return LocalDate.parse(input);
    }

    @ParameterType(value = "([^,]+(?:,[^,]+)*)")
    public List<String> stringList(String input) {
        return Arrays.asList(input.split(","));
    }

    @ParameterType(value = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")
    public UUID uuid(String input) {
        return UUID.fromString(input);
    }

    @ParameterType(value = "(?i:ACTIVE|INACTIVE|PENDING)")
    public Status status(String input) {
        return Status.valueOf(input.toUpperCase());
    }

    @ParameterType(value = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    public String email(String input) {
        return input;
    }

    @ParameterType(value = "[a-zA-Z0-9]+")
    public String token(String input) {
        return input;
    }

}