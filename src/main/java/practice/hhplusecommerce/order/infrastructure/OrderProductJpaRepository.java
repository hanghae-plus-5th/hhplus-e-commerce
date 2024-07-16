package practice.hhplusecommerce.order.infrastructure;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.order.business.entity.OrderProduct;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {

  List<OrderProduct> findAllByOrderIdIn(List<Long> orderIdList);

  @Query("select p.id as productId, p.name as productName, p.price as productPrice, p.stock as productStock, sum(op.quantity) as sumQuantity "
      + "from OrderProduct op "
      + "inner join Product p on op.product.id = p.id "
      + "inner join Order o on  op.order.id = o.id "
      + "where o.createdAt <= :now and  o.createdAt >= :minusDays3 "
      + "group by p.id, p.name, p.price, p.stock "
      + "order by sumQuantity desc, productId desc "
      + "limit 5")
  List<Tuple> findTop5ProductsLast3Days(LocalDateTime now, LocalDateTime minusDays3);

  List<OrderProduct> findALlByOrderId(Long orderId);
}
