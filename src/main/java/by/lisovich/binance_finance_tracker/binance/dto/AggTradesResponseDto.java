package by.lisovich.binance_finance_tracker.binance.dto;

import com.binance.connector.client.spot.rest.model.AggTradesResponse;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import java.util.List;
import java.util.stream.Collectors;

public record AggTradesResponseDto(String sSymbol,
                                   String bSymbol,
                                   Long aLowerCase,
                                   String pLowerCase,
                                   String qLowerCase,
                                   Long fLowerCase,
                                   Long lLowerCase,
                                   Long T,
                                   boolean mLowerCase,
                                   boolean M) {

    public static List<AggTradesResponseDto> getListDto(String sSymbol, String bSymbol, AggTradesResponse aggTradesResponseInners) {
        List<AggTradesResponseDto> dtos = aggTradesResponseInners.stream().map(agg -> new AggTradesResponseDto(sSymbol, bSymbol,
                agg.getaLowerCase(),
                agg.getpLowerCase(),
                agg.getqLowerCase(),
                agg.getfLowerCase(),
                agg.getlLowerCase(),
                agg.getT(),
                agg.getmLowerCase(),
                agg.getM())).collect(Collectors.toList());
        return dtos;
    }
}
