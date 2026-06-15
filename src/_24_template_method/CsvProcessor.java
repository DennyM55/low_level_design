package _24_template_method;

/** Parses CSV data — overrides only the parseData step. */
public class CsvProcessor extends DataProcessor {

    @Override
    protected String parseData(String rawData) {
        System.out.println("[parse] CsvProcessor: splitting on commas");
        // Simplified: replace commas with pipe symbols to show transformation
        return rawData.replace(",", " | ");
    }
}
