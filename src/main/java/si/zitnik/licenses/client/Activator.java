package si.zitnik.licenses.client;

import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.util.RunningUtil;
import si.zitnik.sociogram.util.api.SociogramAPIManager;
import si.zitnik.sociogram.util.api.reqres.ActivationRequest;
import si.zitnik.sociogram.util.api.reqres.ActivationResponse;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Activator
{
    private RunningUtil runningUtil;
    private String enteredSerial;
    private boolean activated;
    private String officialName;

    public Activator(String enteredSerial, RunningUtil runningUtil) {
        this.enteredSerial = enteredSerial;
        this.activated = false;
        this.runningUtil = runningUtil;
    }

    public boolean activate() throws Exception {

        var requestData = new ActivationRequest(
                this.enteredSerial,
                SociogramConstants.PROGRAM_DB_ID,
                System.getenv("PROCESSOR_REVISION"),
                System.getenv("PROCESSOR_IDENTIFIER"),
                System.getenv("COMPUTERNAME"),
                System.getenv("USERNAME"));

        var responseData = SociogramAPIManager.request(
                SociogramConstants.SOCIOGRAM_API_ACTIVATE,
                requestData,
                ActivationResponse.class
        );

        if (responseData.getTrial()) {
            // Program is trial version!
            this.runningUtil.setTrial(true);
            this.runningUtil.setSerial(this.enteredSerial);
            this.officialName = responseData.getOfficialName();
            this.activated = true;
            return true;
        } else if (responseData.getActivated()) {
            // Successful activation!
            this.officialName = responseData.getOfficialName();
            this.activated = true;
            return true;
        } else {
            return false;
        }
    }

    public String getOwnerOfficialName() throws Exception {
        if (this.activated) {
            return this.officialName;
        }
        throw new Exception("ERROR in use! Must activate first!");
    }
}