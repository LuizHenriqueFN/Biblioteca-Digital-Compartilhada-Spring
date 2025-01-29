import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JTest {
    private static final Logger logger = LoggerFactory.getLogger(SLF4JTest.class);

    public static void main(String[] args) {
        logger.info("Este é um log de INFO");
        logger.debug("Este é um log de DEBUG");
        logger.error("Este é um log de ERROR");
    }
}
