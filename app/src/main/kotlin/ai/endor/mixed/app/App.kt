package ai.endor.mixed.app

import ai.endor.mixed.core.RenderOutcome
import ai.endor.mixed.core.TemplateRequest
import ai.endor.mixed.javalib.JavaRenderer
import ai.endor.mixed.kotlinlib.KotlinRenderer
import ai.endor.mixed.kotlinlib.renderAll

/**
 * Drives both renderers, so the sink is reachable through a Java module and a Kotlin module:
 *
 *   main -> JavaRenderer.render   -> BridgeProcessor.process -> StringSubstitutor.replace
 *   main -> KotlinRenderer.render -> BridgeProcessor.process -> StringSubstitutor.replace
 */
fun main(args: Array<String>) {
    val requests = buildRequests(args)

    val javaOutcomes = JavaRenderer().renderAll(requests)
    val kotlinRenderer = KotlinRenderer()
    val kotlinOutcomes = kotlinRenderer.renderAll(requests)

    report("java", javaOutcomes)
    report("kotlin", kotlinOutcomes)

    kotlinRenderer.trail().forEach { println("  audit $it") }
}

private fun buildRequests(args: Array<String>): List<TemplateRequest> {
    if (args.isNotEmpty()) {
        return args.mapIndexed { index, value -> TemplateRequest.lenient("arg-$index", value) }
    }
    return listOf(
        TemplateRequest.lenient("year", "\${date:yyyy}"),
        TemplateRequest.strict("host", "\${env:HOSTNAME}"),
        TemplateRequest.lenient("plain", "no placeholder here"),
        TemplateRequest.lenient("blank", "  "),
    )
}

private fun report(label: String, outcomes: List<RenderOutcome>) {
    val rendered = outcomes.count { it.succeeded() }
    println("$label: ${outcomes.size} requests, $rendered rendered")
    outcomes.forEach { println("  ${it.id()} ${it.status()} ${it.text()}") }
}
