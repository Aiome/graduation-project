package top.aiome.service.order.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import top.aiome.dao.order.OrderMapper;
import top.aiome.dao.order.entity.Order;
import top.aiome.dao.order.entity.OrderExample;
import top.aiome.service.order.service.interfaces.IOrderSV;

@Service
public class OrderSVImpl implements IOrderSV {
	@Resource
	private OrderMapper orderMapper;

	@Override
	public Order getOrderById(Integer id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Order> getOrders(OrderExample orderExample) {
		return orderMapper.selectByExample(orderExample);
	}

	@Override
	public int updateOrderById(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}
	
	@Override
	public int updateOrder(Order order, OrderExample orderExample) {
		return orderMapper.updateByExampleSelective(order, orderExample);
	}

	@Override
	public int insertOrder(Order order) {
		return orderMapper.insertSelective(order);
	}

	@Override
	public int deleteOrderById(Integer id) {
		return orderMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteOrder(OrderExample orderExample) {
		return orderMapper.deleteByExample(orderExample);
	}
}
