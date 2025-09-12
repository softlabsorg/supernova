package shared.exception;

public class ConfigurationParsingException extends RuntimeException {

    public ConfigurationParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationParsingException(String message) {
        super(message);
    }
}