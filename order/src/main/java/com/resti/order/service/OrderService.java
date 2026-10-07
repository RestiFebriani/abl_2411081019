package com.resti.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.resti.order.entity.Orders;
import com.resti.order.repository.OrderRepository;
import com.resti.order.vo.orderVO;
import com.resti.order.vo.pelangganVO;
import com.resti.order.vo.productVO;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private final RestClient restClient;

    public OrderService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public orderVO saveOrder(Orders order) {

    // Cek Pelanggan
    pelangganVO pelanggan = getPelanggan(order.getPelanggan_id());

    if (pelanggan == null) {
        throw new RuntimeException("Pelanggan tidak ditemukan");
    }

    // Cek Produk
    productVO produk = getProduk(order.getProduk_id());

    if (produk == null) {
        throw new RuntimeException("Produk tidak ditemukan");
    }

    // Ambil harga Produk dan Menghitung total
    double total = produk.getHarga() * order.getJumlah();

    order.setTotal(total);

    Orders savedOrder = orderRepository.save(order);

    // Simpan Order
    return getOrderVO(savedOrder);
}

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Orders updateOrder(Long id, Orders orders) {
        Orders existing = orderRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setProduk_id(orders.getProduk_id());
            existing.setPelanggan_id(orders.getPelanggan_id());
            existing.setTgl_trans(orders.getTgl_trans());
            existing.setJumlah(orders.getJumlah());
            existing.setTotal(orders.getTotal());
            return orderRepository.save(existing);
        }
        return null;
    }

    // Mengambil Data Pelanggan

    public pelangganVO getPelanggan(Long id) {

        return restClient.get()
                .uri("http://localhost:8081/api/pelanggan/" + id)
                .retrieve()
                .body(pelangganVO.class);
    }

    // Mengambil Data Produk

    public productVO getProduk(Long id) {

        return restClient.get()
                .uri("http://localhost:8083/api/produk/" + id)
                .retrieve()
                .body(productVO.class);
    }

    public orderVO getOrderVO(Orders order) {

        pelangganVO pelanggan = getPelanggan(order.getPelanggan_id());
        productVO produk = getProduk(order.getProduk_id());

        orderVO orderVO = new orderVO();

        orderVO.setId(order.getId());
        orderVO.setProdukId(order.getProduk_id());
        orderVO.setPelangganId(order.getPelanggan_id());
        orderVO.setJumlah(order.getJumlah());
        orderVO.setTglTrans(order.getTgl_trans());
        orderVO.setTotal(order.getTotal());

        orderVO.setPelanggan(pelanggan);
        orderVO.setProduk(produk);

        return orderVO;
    }

}