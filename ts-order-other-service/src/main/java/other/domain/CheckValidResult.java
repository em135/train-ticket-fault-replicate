package other.domain;

public class CheckValidResult {

    private boolean valid;

    private String stationId;

    public CheckValidResult() {
        //Default Constructor
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
