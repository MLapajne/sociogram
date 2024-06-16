package si.zitnik.sociogram.config;

public class SociogramConstants {
    final public static String PROGRAM_YEAR = "2022";
    final public static String PROGRAM_VER = "5.5";
    final public static String PROGRAM_NAME = "Sociogram " + PROGRAM_VER;
    final public static Integer PROGRAM_DB_ID = 2;

    final private static String SOCIOGRAM_API_ROOT = "https://sociogram-services.zitnik.si/api";
    final public static String SOCIOGRAM_API_TRIAL = SOCIOGRAM_API_ROOT + "/trial-active";
    final public static String SOCIOGRAM_API_ACTIVATE = SOCIOGRAM_API_ROOT + "/activation";

    final public static Integer SOCIOGRAM_API_TIMEOUT_SECONDS = 60;
}
