package boxing.com.store.utils;

import java.util.List;

import boxing.com.store.base.BaseApplication;
import boxing.com.store.sql.DaoSession;
import boxing.com.store.sql.Goods;
import boxing.com.store.sql.GoodsDao;

/**
 * Created by Administrator on 2017/9/13.
 */

public class DaoUtils {

    private static final DaoSession daoInstant = BaseApplication.getDaoInstant();
    private static GoodsDao dao = daoInstant.getGoodsDao();


    /**
     * 模糊搜索
     *
     * @param query
     */
    public static String like(String query) {
        StringBuilder stringBuilder = new StringBuilder("%");
        for (int i = 0; i < query.length(); i++) {
            stringBuilder.append(query.charAt(i) + "");
            stringBuilder.append("%");
        }
        return stringBuilder.toString();
    }

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param goods
     */
    public static void insertLove(Goods goods) {
        dao.insertOrReplace(goods);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void delete(long id) {
        dao.deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param goods
     */
    public static void update(Goods goods) {
        dao.update(goods);
    }

    /**
     * 查询条件为  value1 < Time < value2的数据
     *
     * @return 货物列表
     */
    public static List<Goods> queryTime(long value1, long value2) {
        return BaseApplication.getDaoInstant().getGoodsDao().queryBuilder().where(GoodsDao
                .Properties.Time.between(value1, value2)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<Goods> queryAll() {
        return dao.loadAll();
    }
}
