package tests.unit;

import org.junit.jupiter.api.Test;
import testing_project.State;

import static org.junit.jupiter.api.Assertions.*;

public class StateUnitTests {

    @Test
    void getFullNameReturnsStateName() {
        assertEquals("Illinois", State.IL.getFullName());
        assertEquals("California", State.CA.getFullName());
        assertEquals("New York", State.NY.getFullName());
        assertEquals("Texas", State.TX.getFullName());
    }
}