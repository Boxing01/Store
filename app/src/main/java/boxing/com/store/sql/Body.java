package boxing.com.store.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 商品表
 */
@Entity
public class Body {
    //不能用int  自增
    @Id(autoincrement = true)
    private Long id;
    //商品名称唯一值
    @Unique
    private String name;

    //商品进价
    private double price;

    // 零售价
    private String one_price;

    @Generated(hash = 2120714404)
    public Body(Long id, String name, double price, String one_price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.one_price = one_price;
    }

    @Generated(hash = 139974340)
    public Body() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOne_price() {
        return this.one_price;
    }

    public void setOne_price(String one_price) {
        this.one_price = one_price;
    }
}
