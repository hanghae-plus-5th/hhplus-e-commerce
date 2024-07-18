package practice.hhplusecommerce.support;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import java.util.List;

public class CustomTuple implements Tuple {
  private Long productId;
  private String productName;
  private Integer productPrice;
  private Integer productStock;
  private Long sumQuantity;

  public CustomTuple(Long productId, String productName, Integer productPrice, Integer productStock, Long sumQuantity) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productStock = productStock;
    this.sumQuantity = sumQuantity;
  }

  @Override
  public <X> X get(TupleElement<X> tupleElement) {
    return (X) get(tupleElement.getAlias());
  }

  @Override
  public <X> X get(String alias, Class<X> type) {
    return type.cast(get(alias));
  }

  @Override
  public Object get(String alias) {
    switch (alias) {
      case "productId":
        return productId;
      case "productName":
        return productName;
      case "productPrice":
        return productPrice;
      case "productStock":
        return productStock;
      case "sumQuantity":
        return sumQuantity;
      default:
        throw new IllegalArgumentException("Unknown alias: " + alias);
    }
  }

  @Override
  public <X> X get(int index, Class<X> type) {
    return type.cast(get(index));
  }

  @Override
  public Object get(int index) {
    switch (index) {
      case 0:
        return productId;
      case 1:
        return productName;
      case 2:
        return productPrice;
      case 3:
        return productStock;
      case 4:
        return sumQuantity;
      default:
        throw new IllegalArgumentException("Unknown index: " + index);
    }
  }

  @Override
  public Object[] toArray() {
    return new Object[]{productId, productName, productPrice, productStock, sumQuantity};
  }

  @Override
  public List<TupleElement<?>> getElements() {
    return null; // 필요에 따라 구현
  }
}