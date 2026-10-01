package academy.backend.market_pulse.model.alt;

import academy.backend.market_pulse.model.Instrument;
import academy.backend.market_pulse.model.Portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Traize
 */
public class User implements Portfolio {
    private final String name;

    private final Map<Instrument, Integer> storage = new HashMap<>();

    public User(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position[] getPositions() {
        Position[] positions = new Position[storage.size()];
        int i = 0;

        for (Map.Entry<Instrument, Integer> entry : storage.entrySet()){
            positions[i] = new PositionDTO(entry.getKey(), entry.getValue());
            i++;
        }

        return positions;
    }

    @Override
    public void addPosition(Instrument instrument, int quantity) {
        if (instrument == null){
            throw new IllegalArgumentException("Инструмент не может быть пустым");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Количество должно быть больше нуля");
        }

        storage.merge(instrument, quantity, Integer::sum);
    }
}
