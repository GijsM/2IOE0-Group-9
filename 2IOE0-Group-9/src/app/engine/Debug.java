package engine;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    private static List<String> log = new ArrayList<String>();

    public static void Log(String message) {
        AddMessage(message);
    }

    private static void AddMessage(String message) {
        log.add(message);
        if (log.size() > 20) {
            log.remove(0);
        }
    }

    public static String Log() {
        String ret = " ";
        for (int i = 0; i < log.size(); i++) {
            ret += (log.get(i) + "\n");

        }
        return ret;
    }

    public static String lastEntry() {
        if(log.size() == 0) {
            return "";
        }
        return log.get(log.size() - 1);
    }
}
