package ai.endor.mixed.javalib;

import ai.endor.gartest.bridge.BridgeProcessor;
import ai.endor.mixed.core.RenderOutcome;
import ai.endor.mixed.core.TemplateRequest;
import ai.endor.mixed.core.TemplateRules;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaRenderer.render
 *   -> TemplateRules.rejectionReason   (:core)
 *   -> RenderStats.record              (same module)
 *   -> BridgeProcessor.process         (GAR artifact) -> StringSubstitutor.replace
 */
public final class JavaRenderer {

    public static final String NAME = "java";

    private final RenderStats stats = new RenderStats();

    public RenderOutcome render(TemplateRequest request) {
        String reason = TemplateRules.rejectionReason(request);
        if (reason != null) {
            stats.record(RenderOutcome.Status.REJECTED);
            return RenderOutcome.rejected(request.id(), NAME, reason);
        }
        if (!request.hasPlaceholder()) {
            stats.record(RenderOutcome.Status.SKIPPED);
            return RenderOutcome.skipped(request.id(), NAME, "nothing to interpolate");
        }
        String text = BridgeProcessor.process(request.template());
        stats.record(RenderOutcome.Status.RENDERED);
        return RenderOutcome.rendered(request.id(), NAME, text);
    }

    public List<RenderOutcome> renderAll(List<TemplateRequest> requests) {
        List<RenderOutcome> outcomes = new ArrayList<>(requests.size());
        for (TemplateRequest request : requests) {
            outcomes.add(render(request));
        }
        return outcomes;
    }

    public String summary() {
        return stats.summary();
    }
}
