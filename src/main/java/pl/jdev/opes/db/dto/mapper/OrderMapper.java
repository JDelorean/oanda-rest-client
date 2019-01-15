package pl.jdev.opes.db.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes_commons.domain.order.Order;

public class OrderMapper {
    @Autowired
    private ModelMapper modelMapper;

    public OrderDto convertToDTO(Order order) {
        modelMapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(mapper -> {
                    mapper.map(Order::getOrderId, OrderDto::setExtId);
//                    mapper.map();
                });
        OrderDto dto = modelMapper.map(order, OrderDto.class);
        return null;
    }

    public Order convertToEntity(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        return order;
    }
}
