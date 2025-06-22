import com.intuit.karate.junit5.Karate;

class RunKarateTest {
    @Karate.Test
    Karate runAllFeatures() {
        return Karate.run("classpath:features").relativeTo(getClass());
    }
}