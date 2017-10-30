package boxing.com.store.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/27.
 */
@Entity
public class Ceshi {
    // 不能用int  自增
    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 269955089)
    public Ceshi(Long id) {
        this.id = id;
    }

    @Generated(hash = 1352712445)
    public Ceshi() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
