package rumor;

import java.util.Random;
import java.util.concurrent.Callable;
import lombok.Data;

/**
 * Write a program that simulates the spreading of a rumor among a
 * group of people. At any given time, each person in the group is in
 * one of three categories:
 *
 * <ul>
 *   <li>IGNORANT - the person has not yet heard the rumor</li>
 *   <li>SPREADER - the person has heard the rumor and is eager to
 *                  spread it</li>
 *   <li>STIFLER - the person has heard the rumor but considers it old
 *                 news and will not spread it</li>
 * </ul>
 *
 * At the very beginning, there is one spreader; everyone else is
 * ignorant. Then people begin to encounter each other.
 *
 * So the encounters go like this:
 *
 * <ul>
 *   <li>If a SPREADER and an IGNORANT meet, IGNORANT becomes a SPREADER.</li>
 *   <li>If a SPREADER and a STIFLER meet, the SPREADER becomes a STIFLER.</li>
 *   <li>If a SPREADER and a SPREADER meet, they both become STIFLERS.</li>
 *   <li>In all other encounters nothing changes.</li>
 * </ul>
 *
 * Your program should simulate this by repeatedly selecting two
 * people randomly and having them "meet."
 *
 * There are three questions we want to answer:
 *
 * <ul>
 *   <li>Will everyone eventually hear the rumor, or will it die out
 *       before everyone hears it?<li>
 *   <li>If it does die out, what percentage of the population hears it?</li>
 *   <li>How long does it take?  i.e. How many encounters occur before
 *       the rumor dies out?</li>
 * </ul>
 */
@Data
public class Rumor implements Callable<Rumor> {

    /** The number of people. */
    private final int n;

    /** The final number of meetups. */
    private int meetups = 0;

    /** The percentage of people in-the-know (0 - 1). */
    private double knowing;

    /**
     * Simulate the spread of the rumor. This method should only be
     * run once per Rumor.
     * @return this rumor
     */
    @Override
    public Rumor call() {
        Random rng = new Random();
        Person[] people = new Person[n];
        people[0] = new Person(Mode.SPREADER);
        for (int i = 1; i < n; i++) {
            people[i] = new Person(Mode.IGNORANT);
        }

        /* Meet pairs of people. */
        while (spreading(people)) {
            Person a = people[rng.nextInt(n)];
            Person b = people[rng.nextInt(n)];
            if (a != b) {
                a.meet(b);
                meetups++;
            }
        }

        /* Tally statistics. */
        int know = 0;
        for (Person p : people) {
            if (p.getMode() != Mode.IGNORANT) {
                know++;
            }
        }
        knowing = know / (1d * n);
        return this;
    }

    /**
     * Determine if the rumor is still spreading.
     * @param people  the array of people
     * @return true if there is at least one SPREADER
     */
    private static boolean spreading(Person[] people) {
        for (Person p : people) {
            if (p.getMode() == Mode.SPREADER) {
                return true;
            }
        }
        return false;
    }
}
