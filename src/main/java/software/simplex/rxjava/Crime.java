package software.simplex.rxjava;

public class Crime {
    private final Integer crimeCode;
    private final String crimeDescription;
    private final Integer areaCode;
    private final String areaName;
    private final String statusCode;
    private final String statusDescription;
    private final String address;
    private final String crossStreet;
    private final Coordinates coordinates;

    public Crime(Integer crimeCode, String crimeDescription, Integer areaCode, String areaName, String statusCode, String statusDescription, String address, String crossStreet, Coordinates coordinates) {
        this.crimeCode = crimeCode;
        this.crimeDescription = crimeDescription;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.address = address;
        this.crossStreet = crossStreet;
        this.coordinates = coordinates;
    }

    public Integer getCrimeCode() {
        return crimeCode;
    }

    public String getCrimeDescription() {
        return crimeDescription;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getAddress() {
        return address;
    }

    public String getCrossStreet() {
        return crossStreet;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public static class Coordinates{
        private final float lat;
        private final float lng;

        public Coordinates(float lat, float lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public float getLat() {
            return lat;
        }

        public float getLng() {
            return lng;
        }
    }
}
