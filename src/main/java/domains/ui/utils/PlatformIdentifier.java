package domains.ui.utils;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PlatformIdentifier {

    private static final List<String> VALID_PLATFORMS = List.of("web", "android", "ios", "desktop");

    public String identify(Collection<String> tags) {
        List<String> matchedPlatforms = extractMatchingPlatformTags(tags);
        return validateTags(matchedPlatforms);
    }

    private List<String> extractMatchingPlatformTags(Collection<String> tags) {
        return VALID_PLATFORMS.stream()
                .filter(p -> tags.contains("@" + p))
                .toList();
    }

    private String validateTags(List<String> matchedPlatforms) {
        if (matchedPlatforms.isEmpty()) {
            return null;
        }

        if (matchedPlatforms.size() > 1) {
            throw new IllegalArgumentException();
        }

        return matchedPlatforms.getFirst();
    }
}