package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.pricing.Price;
import pl.jdev.opes_commons.rest.message.response.JsonPricingListWrapper;

@RestController
@RequestMapping("/pricing")
@Log4j2
public class PricingController extends AbstractEntityController<Price> {
    @Deprecated
    @GetMapping
    @ResponseBody
    public JsonPricingListWrapper getPrices(@RequestParam(value = "instruments") final String instruments,
                                            @RequestParam(value = "since", required = false) final String since,
                                            @RequestParam(value = "includeUnitsAvailable", required = false) final boolean includeUnitsAvailable,
                                            @RequestParam(value = "includeHomeConversions", required = false) final boolean includeHomeConversions) {
        return null;
    }
}
