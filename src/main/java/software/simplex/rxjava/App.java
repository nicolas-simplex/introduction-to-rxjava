package software.simplex.rxjava;

import rx.Observable;

import java.io.IOException;
import java.util.Arrays;

import static spark.Spark.*;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        CrimesService crimesService = new CrimesService();
        Observable<Crime> crimes = crimesService.getCrimes(args[0]);
        crimes = crimesService.filterByCrimeCode(crimes, Arrays.asList(310,330));
        crimes.forEach(crime -> {
            System.out.println(crime.getCrimeDescription());
            try {
                WebsocketHandler.broadcast(crime);
            } catch (IOException e) {
            }
        });
        Observable.error(new RuntimeException());

        webSocket("/crimes", WebsocketHandler.class);
        staticFiles.location("/public");
        redirect.get("/", "/index.html");
    }
}
