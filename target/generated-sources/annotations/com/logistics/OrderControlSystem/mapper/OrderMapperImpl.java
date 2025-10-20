package com.logistics.OrderControlSystem.mapper;

import com.logistics.OrderControlSystem.dto.CreateOrderRequest;
import com.logistics.OrderControlSystem.dto.OrderDto;
import com.logistics.OrderControlSystem.dto.OrderItemDto;
import com.logistics.OrderControlSystem.model.Order;
import com.logistics.OrderControlSystem.model.OrderItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-20T07:44:51-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.createdAt( order.getCreatedAt() );
        orderDto.customerId( order.getCustomerId() );
        orderDto.id( order.getId() );
        orderDto.items( orderItemListToOrderItemDtoList( order.getItems() ) );
        orderDto.status( order.getStatus() );
        orderDto.updatedAt( order.getUpdatedAt() );

        return orderDto.build();
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDto( order ) );
        }

        return list;
    }

    @Override
    public Order toEntity(CreateOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.customerId( request.getCustomerId() );
        order.items( orderItemDtoListToOrderItemList( request.getItems() ) );

        return order.build();
    }

    @Override
    public OrderItem toOrderItem(OrderItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder orderItem = OrderItem.builder();

        orderItem.price( dto.getPrice() );
        orderItem.quantity( dto.getQuantity() );
        orderItem.sku( dto.getSku() );

        return orderItem.build();
    }

    @Override
    public OrderItemDto toOrderItemDto(OrderItem item) {
        if ( item == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setPrice( item.getPrice() );
        orderItemDto.setQuantity( item.getQuantity() );
        orderItemDto.setSku( item.getSku() );

        return orderItemDto;
    }

    protected List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDto> list1 = new ArrayList<OrderItemDto>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( toOrderItemDto( orderItem ) );
        }

        return list1;
    }

    protected List<OrderItem> orderItemDtoListToOrderItemList(List<OrderItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItem> list1 = new ArrayList<OrderItem>( list.size() );
        for ( OrderItemDto orderItemDto : list ) {
            list1.add( toOrderItem( orderItemDto ) );
        }

        return list1;
    }
}
