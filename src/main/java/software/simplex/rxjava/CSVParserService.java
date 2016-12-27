package software.simplex.rxjava;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import rx.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CSVParserService {
    private Charset charset;
    private CSVFormat csvFormat;

    public CSVParserService(Charset charset, CSVFormat csvFormat) {
        this.charset = charset;
        this.csvFormat = csvFormat;
    }

    public CSVParserService() {
        this(StandardCharsets.UTF_8, CSVFormat.RFC4180.withFirstRecordAsHeader());
    }

    public Observable<CSVRecord> parse(String filePath) throws IOException {
        File csvFile = new File(filePath);
        CSVParser parser = CSVParser.parse(csvFile, charset, csvFormat);
        Observable<CSVRecord> records = Observable.from(parser);
        return records.doOnTerminate(   () -> {
            try {
                System.out.println("Closing file");
                parser.close();
            } catch (IOException e) {
            }
        });
    }
}
