package rumor;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonTest {

    Person ignorantA = new Person(Mode.IGNORANT);
    Person ignorantB = new Person(Mode.IGNORANT);
    Person spreaderA = new Person(Mode.SPREADER);
    Person spreaderB = new Person(Mode.SPREADER);
    Person stiflerA  = new Person(Mode.STIFLER);
    Person stiflerB  = new Person(Mode.STIFLER);

    @Test
    public void spread1Test() {
        ignorantA.meet(spreaderB);
        assertEquals(ignorantA.getMode(), Mode.SPREADER);
        assertEquals(spreaderB.getMode(), Mode.SPREADER);
    }

    @Test
    public void spread2Test() {
        spreaderA.meet(ignorantB);
        assertEquals(spreaderA.getMode(), Mode.SPREADER);
        assertEquals(ignorantB.getMode(), Mode.SPREADER);
    }

    @Test
    public void stifle1Test() {
        spreaderA.meet(spreaderB);
        assertEquals(spreaderA.getMode(), Mode.STIFLER);
        assertEquals(spreaderB.getMode(), Mode.STIFLER);
    }

    @Test
    public void stifle2Test() {
        stiflerA.meet(spreaderB);
        assertEquals(stiflerA.getMode(), Mode.STIFLER);
        assertEquals(spreaderB.getMode(), Mode.STIFLER);
    }

    @Test
    public void stifle3Test() {
        spreaderA.meet(stiflerB);
        assertEquals(spreaderA.getMode(), Mode.STIFLER);
        assertEquals(stiflerB.getMode(), Mode.STIFLER);
    }

    @Test
    public void nothingTest() {
        ignorantA.meet(ignorantB);
        assertEquals(ignorantA.getMode(), Mode.IGNORANT);
        assertEquals(ignorantB.getMode(), Mode.IGNORANT);
        ignorantA.meet(stiflerB);
        assertEquals(ignorantA.getMode(), Mode.IGNORANT);
        assertEquals(stiflerB.getMode(), Mode.STIFLER);
        stiflerA.meet(ignorantB);
        assertEquals(stiflerA.getMode(), Mode.STIFLER);
        assertEquals(ignorantB.getMode(), Mode.IGNORANT);
        stiflerA.meet(stiflerB);
        assertEquals(stiflerA.getMode(), Mode.STIFLER);
        assertEquals(stiflerB.getMode(), Mode.STIFLER);
    }
}
