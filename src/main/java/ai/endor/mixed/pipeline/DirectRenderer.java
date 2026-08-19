package ai.endor.mixed.pipeline;

import ai.endor.gartest.bridge.BridgeProcessor;
import ai.endor.mixed.core.RenderOutcome;
import ai.endor.mixed.core.TemplateRequest;
import ai.endor.mixed.core.TemplateRules;

/**
 * The root module's own renderer, so the root project produces a real jar rather than being
 * an empty aggregator.
 *
 * DirectRenderer.render
 *   -> TemplateRules.rejectionReason        (internal edge into :core)
 *   -> BridgeProcessor.process              (edge into the GAR-hosted artifact)
 *        -> StringSubstitutor.replace       (CVE-2022-42889 sink in commons-text 1.9)
 */
public final class DirectRenderer {

    public static final String NAME = "direct";

    private DirectRenderer() {
    }

    public static RenderOutcome render(TemplateRequest request) {
        String reason = TemplateRules.rejectionReason(request);
        if (reason != null) {
            return RenderOutcome.rejected(request.id(), NAME, reason);
        }
        if (!request.hasPlaceholder()) {
            return RenderOutcome.skipped(request.id(), NAME, "nothing to interpolate");
        }
        return RenderOutcome.rendered(request.id(), NAME, BridgeProcessor.process(request.template()));
    }
}
