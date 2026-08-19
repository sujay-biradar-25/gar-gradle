package ai.endor.mixed.core;

/** Validation applied before any renderer touches a template. */
public final class TemplateRules {

    private static final int MAX_LENGTH = 4096;

    private TemplateRules() {
    }

    /** Returns null when the request may be rendered, otherwise the reason it may not. */
    public static String rejectionReason(TemplateRequest request) {
        if (request.template().isBlank()) {
            return "template is blank";
        }
        if (request.template().length() > MAX_LENGTH) {
            return "template exceeds " + MAX_LENGTH + " characters";
        }
        if (request.strict() && !request.hasPlaceholder()) {
            return "strict request carries no placeholder";
        }
        if (unbalanced(request.template())) {
            return "template has an unbalanced placeholder";
        }
        return null;
    }

    private static boolean unbalanced(String template) {
        int depth = 0;
        for (int i = 0; i < template.length() - 1; i++) {
            if (template.charAt(i) == '$' && template.charAt(i + 1) == '{') {
                depth++;
            } else if (template.charAt(i) == '}') {
                depth--;
            }
        }
        if (!template.isEmpty() && template.charAt(template.length() - 1) == '}') {
            depth--;
        }
        return depth != 0;
    }
}
