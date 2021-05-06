import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;
import java.util.function.Function;

class Attach {
    Function<String, CheckResult> func;

    Attach(Function<String, CheckResult> func) {
        this.func = func;
    }
}

public class MusicAdvisorTest extends StageTest<Attach> {

    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
            new TestCase<Attach>()
                .setInput("auth\nexit")
                .setAttach(new Attach(reply -> {
                    if (!reply.contains("---SUCCESS---")) {
                        return new CheckResult(false,
                            "There is no \"---SUCCESS---\" after \"auth\" but should be");
                    }
                    if (!reply.contains("https://accounts.spotify.com/authorize?")) {
                        return new CheckResult(false,
                            "There is no link after \"auth\" but should be");
                    }
                    if (reply.contains("a19ee7dbfda443b2a8150c9101bfd645")) {
                        return new CheckResult(false,
                                "You shouldn't use the client_id from the example!! " +
                                        "You should create your own id on the spotify site.");
                    }
                    return CheckResult.correct();
                })),

            new TestCase<Attach>()
                .setInput("new\nexit")
                .setAttach(new Attach(reply -> {
                    if (!reply.strip().startsWith("Please, provide access for application.")) {
                        return new CheckResult(false,
                            "When no access provided you should output " +
                                "\"Please, provide access for application.\"");
                    }
                    return CheckResult.correct();
                })),

            new TestCase<Attach>()
                .setInput("featured\nexit")
                .setAttach(new Attach(reply -> {
                    if (!reply.strip().startsWith("Please, provide access for application.")) {
                        return new CheckResult(false,
                            "When no access provided you should output " +
                                "\"Please, provide access for application.\"");
                    }
                    return CheckResult.correct();
                })),

            new TestCase<Attach>()
                .setInput("auth\nnew\nfeatured\nexit")
                .setAttach(new Attach(reply -> {
                    if (!reply.contains("---NEW RELEASES---")) {
                        return new CheckResult(false,
                            "When \"new\" was inputted there should be \"---NEW RELEASES---\" line");
                    }
                    if (!reply.contains("---FEATURED---")) {
                        return new CheckResult(false,
                            "When \"featured\" was inputted there should be \"---FEATURED---\" line");
                    }
                    return CheckResult.correct();
                }))

        );
    }

    @Override
    public CheckResult check(String reply, Attach clue) {
        return clue.func.apply(reply);
    }
}
