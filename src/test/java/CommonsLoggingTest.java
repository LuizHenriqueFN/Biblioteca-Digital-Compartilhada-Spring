import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingTest {
    private static final Log log = LogFactory.getLog(CommonsLoggingTest.class);

    public static void main(String[] args) {
        log.info("Log gerado com sucesso usando o Apache Commons Logging.");
    }
}
