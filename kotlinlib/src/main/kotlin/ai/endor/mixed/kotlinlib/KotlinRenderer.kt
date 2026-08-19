package ai.endor.mixed.kotlinlib

import ai.endor.gartest.bridge.BridgeProcessor
import ai.endor.mixed.core.RenderOutcome
import ai.endor.mixed.core.TemplateRequest
import ai.endor.mixed.core.TemplateRules

/**
 * KotlinRenderer.render
 *   -> TemplateRules.rejectionReason   (:core, java from kotlin)
 *   -> RenderAudit.note                (same module)
 *   -> BridgeProcessor.process         (GAR artifact) -> StringSubstitutor.replace
 *
 * Real .kt sources, so this module routes through the Kotlin call-graph path rather than the
 * Java one.
 */
class KotlinRenderer(private val audit: RenderAudit = RenderAudit()) {

    fun render(request: TemplateRequest): RenderOutcome {
        val reason = TemplateRules.rejectionReason(request)
        if (reason != null) {
            audit.note(request.id(), "rejected: $reason")
            return RenderOutcome.rejected(request.id(), NAME, reason)
        }
        if (!request.hasPlaceholder()) {
            audit.note(request.id(), "skipped")
            return RenderOutcome.skipped(request.id(), NAME, "nothing to interpolate")
        }
        val text = BridgeProcessor.process(request.template())
        audit.note(request.id(), "rendered")
        return RenderOutcome.rendered(request.id(), NAME, text)
    }

    fun trail(): List<String> = audit.entries()

    companion object {
        const val NAME: String = "kotlin"
    }
}

/** Ordered record of what the renderer did, so the module has an internal collaborator. */
class RenderAudit {

    private val lines = mutableListOf<String>()

    fun note(id: String, what: String) {
        lines += "$id:$what"
    }

    fun entries(): List<String> = lines.toList()
}

/** Extension so the Kotlin module carries at least one top-level function too. */
fun KotlinRenderer.renderAll(requests: List<TemplateRequest>): List<RenderOutcome> =
    requests.map { render(it) }
