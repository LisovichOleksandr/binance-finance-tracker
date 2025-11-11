package by.lisovich.binance_finance_tracker.entity.converter;

import by.lisovich.binance_finance_tracker.entity.CandleInterval;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CandleIntervalConverter implements AttributeConverter<CandleInterval, String> {


    @Override
    public String convertToDatabaseColumn(CandleInterval attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CandleInterval convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CandleInterval.fromCode(dbData);
    }
}
