package boxing.com.store.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import boxing.com.store.R;
import boxing.com.store.sql.BodyDao;
import boxing.com.store.sql.GoodsDao;
import boxing.com.store.utils.L;
import boxing.com.store.utils.UIUtils;


/**
 * activity的基类,封装常用的方法,新建activity时继承它
 */
public abstract class BaseActivity extends AppCompatActivity {
    public GoodsDao goodsDao = BaseApplication.getDaoInstant().getGoodsDao();
    public BodyDao bodyDao = BaseApplication.getDaoInstant().getBodyDao();
    public Context mContext;
    public BaseActivity mActivity;
    protected int page = 1;
    protected int page_total;
    protected boolean isRefresh = true;
    protected SwipeRefreshLayout mSwipeRefresh;
    protected int page_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(mActivity + " onCreate================");

        mContext = BaseApplication.getApplication();
        mActivity = this;

        initData();
        initView();
        loadNetData();
    }


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化view 先调用 setContentView(int)
     */
    public abstract void initView();

    /**
     * 网络访问
     */
    protected abstract void loadNetData();

    /**
     * 设置下拉刷新
     */
    public void setOnRefreshListener() {
        this.mSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.bg_f0);
        this.mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        this.mSwipeRefresh.setProgressViewOffset(false, 0, UIUtils.dip2px(50));
        this.mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                loadNetData();
            }
        });
    }


    /**
     * 上拉加载更多
     *
     * @param baseAdapter
     */
    public void onLodeMore(BaseRecyclerAdapter baseAdapter) {
        isRefresh = false;
        if (page >= page_total) {
            baseAdapter.changeMoreStatus(BaseRecyclerAdapter.NO_MORE_DATA);
        } else {
            if (page < 1 + page_success) {
                page++;
            }
            loadNetData();
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        if (onKeyBack()) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onKeyBack())
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @return true 关闭页面; false 无响应; 默认true
     */
    public boolean onKeyBack() {
        return true;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (TextUtils.isEmpty(getUserName())) {
            return false;
        }
        return true;
    }

    /**
     * 打开页面
     *
     * @param cls 要打开的activity
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        startActivity(intent);
    }

    /**
     * 打开页面
     *
     * @param cls 要打开的activity
     */
    public void startActivity(Class<?> cls, String flag, String title, String data) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtra("flag", flag);
        intent.putExtra("title", title);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public String getIntentData(String key) {
        Intent intent = getIntent();
        String str = intent.getStringExtra(key);
        return str;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * @return 用户名
     */
    public String getUserName() {
        return null;
    }

    /**
     * @param userName 保存用户名
     */
    public void setUserName(String userName) {
    }

    /**
     * @return 用户密码
     */
    public String getUserPsd() {
        return null;
    }

    /**
     * @param userPsd 保存用户密码
     */
    public void setUserPsd(String userPsd) {
    }

    /**
     * @return 用户 uid
     */
    public String getUserId() {
        return null;
    }

    /**
     * @param userId 保存用户 uid
     */
    public void setUserId(String userId) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.i(mActivity + " onDestroy================");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.i(mActivity + " onStart================");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i(mActivity + " onResume================");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.i(mActivity + " onRestart================");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.i(mActivity + " onPause================");

    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i(mActivity + " onStop================");

    }

}
