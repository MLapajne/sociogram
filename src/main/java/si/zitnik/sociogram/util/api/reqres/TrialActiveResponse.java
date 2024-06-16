package si.zitnik.sociogram.util.api.reqres;

public class TrialActiveResponse {
    private Boolean result;

    public TrialActiveResponse() {
    }

    public TrialActiveResponse(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TrialActiveResponse{" +
                "result=" + result +
                '}';
    }
}
