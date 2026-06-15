package _24_template_method;

/** Parses JSON data — overrides parseData AND validateData. */
public class JsonProcessor extends DataProcessor {

    @Override
    protected String parseData(String rawData) {
        System.out.println("[parse] JsonProcessor: converting to JSON format");
        // Simplified transformation for demo
        return "{\"data\": \"" + rawData.replace("\n", "\\n") + "\"}";
    }

    @Override
    protected boolean validateData(String data) {
        boolean ok = data != null && data.startsWith("{");
        System.out.println("[validate] JsonProcessor: must start with '{': " + ok);
        return ok;
    }
}
