package pack.util;

import java.lang.management.ManagementFactory;
import java.util.regex.Pattern;

public class RuntimeEnvironment {

    // https://stackoverflow.com/a/7397648
    private final static Pattern debugPattern = Pattern.compile("-Xdebug|jdwp");
    public static boolean isDebugging() {
        for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (debugPattern.matcher(arg).find()) {
                return true;
            }
        }
        return false;
    }
}
