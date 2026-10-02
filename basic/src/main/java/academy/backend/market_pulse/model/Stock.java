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

    /**
     * Дивидендная доходность в процентах — единственный числовой атрибут акции на этом этапе
     * проекта, используется в том числе как «аналог цены» в фильтрации ({@code PriceFilter}).
     */
    public BigDecimal getDividendYield() {
        return dividendYield;
    }

    @Override
    public String getDescription() {
        return "Акция, сектор: " + sector;
    }

    @Override
    public String getType() {
        return "STOCK";
    }

    public BigDecimal getDividends(BigDecimal currentPrice) {
        return currentPrice.multiply(dividendYield)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
