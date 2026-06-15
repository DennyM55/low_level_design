package _24_template_method;

/**
 * Abstract class defining the template method for data processing.
 *
 * The overall algorithm is fixed: read → parse → validate → save.
 * Subclasses override parseData() and optionally validateData().
 */
public abstract class DataProcessor {

    /**
     * Template method — defines the fixed algorithm skeleton.
     * Declared final so subclasses cannot reorder the steps.
     */
    public final void process(String source) {
        System.out.println("\n--- Processing: " + source + " ---");
        String rawData = readData(source);
        String parsed  = parseData(rawData);
        boolean valid  = validateData(parsed);
        if (valid) {
            saveData(parsed);
        } else {
            System.out.println("Validation failed — data not saved.");
        }
    }

    /** Shared step: reads raw content from the source. */
    protected String readData(String source) {
        System.out.println("[read]  Reading from: " + source);
        // Simulate reading file content
        return "id,name,value\n1,Alice,100\n2,Bob,200";
    }

    /** Abstract step: each format parses differently. */
    protected abstract String parseData(String rawData);

    /**
     * Default validation — subclasses may override for stricter rules.
     */
    protected boolean validateData(String data) {
        boolean ok = data != null && !data.isBlank();
        System.out.println("[validate] Valid: " + ok);
        return ok;
    }

    /** Shared step: saves the processed data. */
    protected void saveData(String data) {
        System.out.println("[save]  Saved " + data.length() + " characters.");
    }
}
