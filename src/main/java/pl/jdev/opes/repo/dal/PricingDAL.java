package pl.jdev.opes.repo.dal;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.jdev.opes.domain.pricing.Price;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2(topic = "DB - Pricing")
public class PricingDAL extends DAL<Price> {

    @Override
    public Price get(String extObjectId) {
        return dao.getById(extObjectId);
    }

    @Override
    public Collection<Price> getAll() {
        return dao.getAll();
    }

    @Override
    public Price upsert(String extObjectId, Price upsertObject) {
        if (!containsObjectId(extObjectId)) {
            log.info(String.format("No pricing entry with id: %s. Inserting into DB...", extObjectId));
            dao.insert(upsertObject);
        } else {
            log.info(String.format("Upserting pricing %s with %s...", extObjectId, upsertObject));
            dao.upsert(extObjectId, upsertObject);
        }
        Price price = dao.getById(extObjectId);
        log.info(String.format("Successfully created/updated pricing %s", price));
        return price;
    }

    public Collection<Price> upsertMulti(Collection<Price> upsertObjects) {
        Map<String, Price> priceMap = upsertObjects.stream()
                .collect(Collectors.toMap(Price::getTime, price -> price));
        priceMap.forEach(this::upsert);
        return getAll();
    }


    @Override
    public boolean remove(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectIds(Collection<String> extObjectIds) {
        return false;
    }
}
