package jp.co.axa.apidemo.Const;

public enum UpdateEmployeeResult {
    /**
     * @code 0 success
     */
    SUCCESS(0),
    /**
     * @code 1 not found
     */
    NOT_FOUND(1);
    private final int resultCode;
//    public static final int SUCCESS = 0;
//    public static final int NOT_FOUND = 1;

    UpdateEmployeeResult(int resultCode) {
        this.resultCode = resultCode;
    }


}
