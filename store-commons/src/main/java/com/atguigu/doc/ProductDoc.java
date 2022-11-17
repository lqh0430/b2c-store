package com.atguigu.doc;

import com.atguigu.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author LQH02
 * @Description
 *
 * 商品服务传给搜索服务的是product实体对象, 但是es库里的数据结构是doc对象类型,
 * 所以需要再搜索服务里将 product对象转成productDoc对象,然后搜索服务再将该productDoc对象序列化成json数据格式存入到es库中
 * @CreateDate 2022/11/16 9:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDoc extends Product {

    /**
     * 用于模糊查询字段,由商品名,标题和描述组成
     */
    private String all;

    public ProductDoc(Product product) {
        super(product.getProductId(),
                product.getProductName(),
                product.getCategoryId(),
                product.getProductTitle(),
                product.getProductIntro(),
                product.getProductPicture(),
                product.getProductPrice(),
                product.getProductSellingPrice(),
                product.getProductNum(),
                product.getProductSales());
        this.all = product.getProductName() + product.getProductTitle() + product.getProductIntro();
    }
}
