package enums;

public enum ErrorCode {
    DATABASE(0, "A database error has occurred."),
    DEVICE_ALREADY_EXIST(1, "Device already exist!!"),
    DEVICE_NOT_FOUND(2, "Device not found!!"),
    DEVICE_NOT_ACTIVE(3, "Device should be active!!");

    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
