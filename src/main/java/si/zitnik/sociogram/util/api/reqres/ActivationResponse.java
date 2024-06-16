package si.zitnik.sociogram.util.api.reqres;

public class ActivationResponse {

    private Boolean activated;
    private Boolean trial;
    private String officialName;

    public ActivationResponse() {
    }

    public ActivationResponse(Boolean activated, Boolean trial, String officialName) {
        this.activated = activated;
        this.trial = trial;
        this.officialName = officialName;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
        this.trial = trial;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    @Override
    public String toString() {
        return "ActivationResponse{" +
                "activated=" + activated +
                ", trial=" + trial +
                ", officialName='" + officialName + '\'' +
                '}';
    }
}
