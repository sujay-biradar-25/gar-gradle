package ai.endor.mixed.javalib;

import ai.endor.mixed.core.RenderOutcome;

import java.util.EnumMap;
import java.util.Map;

/** Counts outcomes by status so the renderer has a real internal collaborator. */
final class RenderStats {

    private final Map<RenderOutcome.Status, Integer> counts = new EnumMap<>(RenderOutcome.Status.class);

    void record(RenderOutcome.Status status) {
        counts.merge(status, 1, Integer::sum);
    }

    String summary() {
        StringBuilder builder = new StringBuilder();
        for (RenderOutcome.Status status : RenderOutcome.Status.values()) {
            builder.append(status).append('=').append(counts.getOrDefault(status, 0)).append(' ');
        }
        return builder.toString().trim();
    }
}
