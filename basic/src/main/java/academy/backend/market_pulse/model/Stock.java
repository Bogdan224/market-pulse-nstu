package academy.backend.market_pulse.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stock extends Instrument {

    private final String sector;
    // NOTICE: доходность и цена — всегда BigDecimal, не double (деньги).
    private final BigDecimal dividendYield;

    public Stock(String ticker, String name, Currency currency,
                 String sector, BigDecimal dividendYield) {
        super(ticker, name, currency);
        this.sector = sector;
        this.dividendYield = dividendYield;
    }

    public String getSector() {
        return sector;
    }

    @Override
    public String getDescription() {
        return "Акция, сектор: " + sector;
    }

    public BigDecimal getDividends(BigDecimal currentPrice) {
        // TODO: currentPrice * dividendYield / 100, с явным RoundingMode.
        throw new UnsupportedOperationException("getDividends для Stock");
    }
}
