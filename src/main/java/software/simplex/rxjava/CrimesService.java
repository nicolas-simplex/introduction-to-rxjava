package software.simplex.rxjava;


import org.apache.commons.csv.CSVRecord;
import rx.Observable;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class CrimesService {

    private static final int CRIME_CODE_POSITION = 7;
    private static final int CRIME_DESCRIPTION_POSITION = 8;
    private static final int AREA_CODE_POSITION = 4;
    private static final int AREA_NAME_POSITION = 5;
    private static final int STATUS_CODE_POSITION = 9;
    private static final int STATUS_DESCRIPTION_POSITION = 10;
    private static final int ADDRESS_POSITION = 11;
    private static final int CROSS_STREET_POSITION = 12;
    private static final int COORDINATES_POSITION = 13;

    private CSVParserService csvParserService;

    public CrimesService(CSVParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    public CrimesService() {
        this(new CSVParserService());
    }

    public Observable<Crime> getCrimes(String crimesCsvPath) throws IOException {
        Observable<Long> timer = Observable.interval(200, TimeUnit.MILLISECONDS);
        Observable<Crime> crimes = csvParserService.parse(crimesCsvPath).map(this::parseCrimeFromCSVRecord);
        return Observable.zip(crimes, timer, (crime, t) -> crime);
    }

    public Observable<Crime> filterByCrimeCode(Observable<Crime> crimes, Collection<Integer> codes){
        return crimes.filter(crime -> codes.contains(crime.getCrimeCode()));
    }

    private Crime parseCrimeFromCSVRecord(CSVRecord csvRecord){
        Crime.Coordinates coordinates = parseCoordinates(csvRecord.get(COORDINATES_POSITION));

        return new Crime(Integer.parseInt(csvRecord.get(CRIME_CODE_POSITION)),csvRecord.get(CRIME_DESCRIPTION_POSITION),
                Integer.parseInt(csvRecord.get(AREA_CODE_POSITION)), csvRecord.get(AREA_NAME_POSITION),
                csvRecord.get(STATUS_CODE_POSITION), csvRecord.get(STATUS_DESCRIPTION_POSITION),
                csvRecord.get(ADDRESS_POSITION), csvRecord.get(CROSS_STREET_POSITION),
                coordinates);
    }

    private Crime.Coordinates parseCoordinates(String coordinates){
        if(coordinates != null && coordinates.matches("\\(-?[0-9]+\\.?[0-9]*\\, ?-?[0-9]+\\.?[0-9]*\\)")){
            String lat = coordinates.split(",")[0].substring(1);
            String lng = coordinates.split(",")[1];
            lng = lng.substring(0,lng.length() - 2).trim();
            return new Crime.Coordinates(Float.parseFloat(lat), Float.parseFloat(lng));
        }else{
            return null;
        }
    }
}
