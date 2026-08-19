package ai.endor.mixed.core;

/** Result of a render attempt, carrying which renderer produced it. */
public record RenderOutcome(String id, String renderer, Status status, String text) {

    public enum Status {
        RENDERED,
        SKIPPED,
        REJECTED
    }

    public static RenderOutcome rendered(String id, String renderer, String text) {
        return new RenderOutcome(id, renderer, Status.RENDERED, text);
    }

    public static RenderOutcome skipped(String id, String renderer, String reason) {
        return new RenderOutcome(id, renderer, Status.SKIPPED, reason);
    }

    public static RenderOutcome rejected(String id, String renderer, String reason) {
        return new RenderOutcome(id, renderer, Status.REJECTED, reason);
    }

    public boolean succeeded() {
        return status == Status.RENDERED;
    }
}
