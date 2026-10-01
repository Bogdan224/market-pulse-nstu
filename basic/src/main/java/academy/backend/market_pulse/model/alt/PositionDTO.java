package academy.backend.market_pulse.model.alt;

import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Portfolio;

/**
 * @author Ilya Traize
 */
public record PositionDTO(Instrument instrument, int quantity) implements Portfolio.Position {
    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
