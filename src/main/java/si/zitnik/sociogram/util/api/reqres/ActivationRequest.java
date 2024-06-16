package si.zitnik.sociogram.util.api.reqres;

public class ActivationRequest {

    private String serial_code;
    private Integer idproduct;
    private String procrevision;
    private String procidentifier;
    private String computername;
    private String username;

    public ActivationRequest() {
    }

    public ActivationRequest(String serial_code, Integer idproduct, String procrevision, String procidentifier, String computername, String username) {
        this.serial_code = serial_code;
        this.idproduct = idproduct;
        this.procrevision = procrevision;
        this.procidentifier = procidentifier;
        this.computername = computername;
        this.username = username;
    }

    public String getSerial_code() {
        return serial_code;
    }

    public void setSerial_code(String serial_code) {
        this.serial_code = serial_code;
    }

    public Integer getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(Integer idproduct) {
        this.idproduct = idproduct;
    }

    public String getProcrevision() {
        return procrevision;
    }

    public void setProcrevision(String procrevision) {
        this.procrevision = procrevision;
    }

    public String getProcidentifier() {
        return procidentifier;
    }

    public void setProcidentifier(String procidentifier) {
        this.procidentifier = procidentifier;
    }

    public String getComputername() {
        return computername;
    }

    public void setComputername(String computername) {
        this.computername = computername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ActivationRequest{" +
                "serial_code='" + serial_code + '\'' +
                ", idproduct=" + idproduct +
                ", procrevision='" + procrevision + '\'' +
                ", procidentifier='" + procidentifier + '\'' +
                ", computername='" + computername + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
