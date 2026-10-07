package com.resti.order.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.resti.order.entity.Orders;
import com.resti.order.service.OrderService;
import com.resti.order.vo.orderVO;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // --- ENDPOINT ORDER ---
    @GetMapping
    public List<Orders> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<orderVO> createOrder(@RequestBody Orders order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(
            @PathVariable("id") Long id,
            @RequestBody Orders order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Succes To Delete Data With ID : " + id);
    }
}