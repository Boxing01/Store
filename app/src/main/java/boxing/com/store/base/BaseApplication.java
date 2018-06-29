package boxing.com.store.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import boxing.com.store.sql.BodyDao;
import boxing.com.store.sql.CeshiDao;
import boxing.com.store.sql.DaoMaster;
import boxing.com.store.sql.DaoSession;
import boxing.com.store.sql.GoodsDao;
import boxing.com.store.utils.MigrationHelper;

/**
 * Created by Administrator on 2017/9/13.
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //配置数据库
        setupDatabase();
    }

    public static BaseApplication getApplication(){
        return mApplication;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"       DevOpenHelper 是创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper(this, "store.db", null){
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                MigrationHelper.migrate(db, BodyDao.class,  GoodsDao.class);
            }
        };
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象  DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者  DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
