package academy.backend.market_pulse.converter;

import academy.backend.market_pulse.cli.ListCommand;
import academy.backend.market_pulse.filter.*;
import academy.backend.market_pulse.model.Currency;

import java.math.BigDecimal;
import java.util.List;

public final class InstrumentFilterConverter {
    public static InstrumentFilter convert(ListCommand.ListCommandFilter filter, List<Object> params) {
        return switch (filter) {
            case BY_TYPE -> new TypeFilter((String) params.get(0));
            case BY_PRICE -> new PriceFilter((BigDecimal) params.get(0), (PriceFilter.EqualsOperator) params.get(1));
            case BY_TICKER -> new TickerFilter((String) params.get(0));
            case BY_CURRENCY -> new CurrencyFilter((Currency) params.get(0));
            case WITHOUT -> new WithoutFilter();
        };
    }
}
