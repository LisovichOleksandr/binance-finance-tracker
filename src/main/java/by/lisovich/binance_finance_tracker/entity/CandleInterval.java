package by.lisovich.binance_finance_tracker.entity;

public enum CandleInterval {
    SEC_1("1s"),
    MIN_1("1m"),
    MIN_3("3m"),
    MIN_5("5m"),
    MIN_15("15m"),
    MIN_30("30m"),
    HOUR_1("1h"),
    HOUR_2("2h"),
    HOUR_4("4h"),
    HOUR_6("6h"),
    HOUR_8("8h"),
    HOUR_12("12h"),
    DAY_1("1d"),
    DAY_3("3d"),
    WEEK_1("1w"),
    MONTH_1("1M");

    private final String code;

    CandleInterval(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CandleInterval fromCode(String code) {
        for (CandleInterval interval : values()) {
            if (interval.code.equalsIgnoreCase(code)) {
                return interval;
            }
        }
        throw new IllegalArgumentException("Unknown candle interval: " + code);
    }

}
