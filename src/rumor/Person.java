package rumor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A single person in the population, who may or may not know the
 * rumor and may or may not be spreading it.
 */
@AllArgsConstructor
public class Person {

    /** This person's rumor-spreading mode. */
    @Getter @Setter
    private Mode mode;

    /**
     * Meet with another person and possibly spread the rumor.
     * @param person  the other person
     */
    public void meet(final Person person) {
        if (this.mode == Mode.IGNORANT) {
            if (person.mode == Mode.SPREADER) {
                /* Learn the rumor. */
                setMode(Mode.SPREADER);
            }
        } else if (this.mode == Mode.SPREADER) {
            if (person.mode == Mode.STIFLER || person.mode == Mode.SPREADER) {
                /* Stifle the rumor. */
                setMode(Mode.STIFLER);
                person.setMode(Mode.STIFLER);
            } else if (person.mode == Mode.IGNORANT) {
                /* Spread the rumor. */
                person.setMode(Mode.SPREADER);
            }
        } else if (this.mode == Mode.STIFLER) {
            if (person.mode == Mode.SPREADER) {
                /* Stifle the rumor. */
                person.setMode(Mode.STIFLER);
            }
        }
    }
}
