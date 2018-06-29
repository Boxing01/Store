package boxing.com.store.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 进货记录表
 */
@Entity
public class Goods {

    // 不能用int  自增
    @Id(autoincrement = true)
    private Long id;
    // @Unique 唯一值
    //商品名称
    @NotNull
    private String name;
    //商品进价
    private double price;
    // 零售价
    private String one_price;
    // 进货件数
    private String into_num;
    // 进货时间
    private long time;
    // 一件含有的数量
    private String one_num;

    @Generated(hash = 760509971)
    public Goods(Long id, @NotNull String name, double price, String one_price,
            String into_num, long time, String one_num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.one_price = one_price;
        this.into_num = into_num;
        this.time = time;
        this.one_num = one_num;
    }
    @Generated(hash = 1770709345)
    public Goods() {
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
    public String getInto_num() {
        return this.into_num;
    }
    public void setInto_num(String into_num) {
        this.into_num = into_num;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getOne_num() {
        return this.one_num;
    }
    public void setOne_num(String one_num) {
        this.one_num = one_num;
    }

}
