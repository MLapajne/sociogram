package si.zitnik.sociogram.util.api.reqres;

public class TrialActiveRequest {
    private String serial_code;

    public TrialActiveRequest() {
    }

    public TrialActiveRequest(String serial_code) {
        this.serial_code = serial_code;
    }

    public String getSerial_code() {
        return serial_code;
    }

    public void setSerial_code(String serial_code) {
        this.serial_code = serial_code;
    }

    @Override
    public String toString() {
        return "TrialActiveRequest{" +
                "serial_code='" + serial_code + '\'' +
                '}';
    }
}
