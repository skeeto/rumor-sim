package rumor;

/**
 * The modes of rumor spreading.
 */
public enum Mode {
    /** Knows nothing about the rumor. */
    IGNORANT,
    /** Knows about the rumor and is actively spreading it. */
    SPREADER,
    /** Considers the rumor old news and no longer spreads it. */
    STIFLER;
}
