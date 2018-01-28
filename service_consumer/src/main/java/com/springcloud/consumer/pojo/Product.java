package com.springcloud.consumer.pojo;

/** @author cctv 2018/1/27 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品信息DTO对象
 *
 * @author CD826(CD826Dong@gamil.com)
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@Setter
public class Product {
  private static final long serialVersionUID = 1L;

  private String itemCode; // 产品货号
  private String name; // 产品名称
  private String bandName; // 产品品牌名称
  private int price; // 产品价格(分)
}
