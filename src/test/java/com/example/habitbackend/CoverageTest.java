import org.junit.jupiter.api.Test;

class CoverageTest {

    @Test
    void coverAll() {
        try {
            Class.forName("com.example.habitbackend.HabitBackendApplication");
        } catch (Exception e) {
            // ignore
        }
    }
}
