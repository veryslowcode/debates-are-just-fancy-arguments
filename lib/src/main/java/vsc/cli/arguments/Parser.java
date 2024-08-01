package vsc.cli.arguments;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static Map<String, String> parseArguments(String[] args) {
        var pattern = Pattern.compile("--.*=.*");
        var arguments = new HashMap<String, String>();
        
        if (args.length == 0) { return arguments; }
        
        for (var input : args) {
            var matcher = pattern.matcher(input);
            if (matcher.find()) {
                var parts = input.split("=");
                if (parts.length < 2) { continue; }
                var key = parts[0].replace("--", "");
                var value = parts[1];
                arguments.put(key, value);
            }
        }

        return arguments;
    }
    
    public static Map<String, Boolean> parseFlags(String[] args) {
        var pattern = Pattern.compile("-.");
        var flags = new HashMap<String, Boolean>();
        
        if (args.length == 0) { return flags; }

        for (var input : args) {
            var matcher = pattern.matcher(input);
            if (matcher.find()) {
                var key = input.replace("-", "");
                var value = true;
                flags.put(key, value);
            }
        }
        return flags;
    }
}
