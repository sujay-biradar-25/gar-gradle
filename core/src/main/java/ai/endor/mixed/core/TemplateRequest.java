package ai.endor.mixed.core;

import java.util.Objects;

/** One unit of work: a template to interpolate, plus how strictly to treat it. */
public record TemplateRequest(String id, String template, boolean strict) {

    public TemplateRequest {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(template, "template");
    }

    public static TemplateRequest lenient(String id, String template) {
        return new TemplateRequest(id, template, false);
    }

    public static TemplateRequest strict(String id, String template) {
        return new TemplateRequest(id, template, true);
    }

    public boolean hasPlaceholder() {
        return template.contains("${");
    }
}
