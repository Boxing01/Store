package boxing.com.store;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import boxing.com.store.adapters.GoodsAdapter;
import boxing.com.store.base.BaseActivity;
import boxing.com.store.base.BaseApplication;
import boxing.com.store.base.BaseRecyclerAdapter;
import boxing.com.store.divider.RecyclerItemDivider;
import boxing.com.store.interfaces.ItemClickListener;
import boxing.com.store.sql.Body;
import boxing.com.store.sql.BodyDao;
import boxing.com.store.sql.Goods;
import boxing.com.store.sql.GoodsDao;
import boxing.com.store.utils.DaoUtils;
import boxing.com.store.utils.L;
import boxing.com.store.utils.TimeUtil;
import boxing.com.store.utils.UIUtils;
import boxing.com.store.views.SearchHint;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        ItemClickListener<Goods>, View.OnFocusChangeListener, View.OnClickListener, TimeUtil
                .DialogInterface {
    public static final int START = 0;
    private SearchView searchView;
    private RecyclerView mRecycler;
    private SearchHint searchHint;
    private GoodsAdapter goodsAdapter;
    private Handler handler;
    private List<Goods> totalList = new ArrayList<>();
    private TextView start_time;
    private TextView end_time;

    // 搜索条件
    private long startTime;
    private long endTime = System.currentTimeMillis();
    private String queryText = "";

    @Override
    protected void initData() {
        HandlerThread sqlThread = new HandlerThread("sql");
        sqlThread.start();

        handler = new Handler(sqlThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case START:
                        // 搜索数据库
                        final List<Goods> goodsList = goodsDao.queryBuilder().where(goodsDao
                                .queryBuilder()
                                .and(GoodsDao.Properties.Time.between(startTime, endTime),
                                        GoodsDao.Properties.Name.like(DaoUtils.like(queryText))))
                                .orderDesc(GoodsDao.Properties.Time, GoodsDao.Properties.Id).list();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefresh.setRefreshing(false);
                                goodsAdapter.refreshData(goodsList);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView);
        start_time = (TextView) findViewById(R.id.start_time);
        start_time.setText(TimeUtil.getChinaDateTime(System.currentTimeMillis()));
        start_time.setOnClickListener(this);
        end_time = (TextView) findViewById(R.id.end_time);
        end_time.setText(TimeUtil.getChinaDateTime(System.currentTimeMillis()));
        end_time.setOnClickListener(this);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        setOnRefreshListener();
        View include = findViewById(R.id.include);
        include.setBackgroundResource(R.color.bg_f0);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.addItemDecoration(new RecyclerItemDivider(R.color.line, 1, LinearLayoutManager
                .VERTICAL));
        //        List<Goods> goodsList = BaseApplication.getDaoInstant().getGoodsDao().loadAll();
        goodsAdapter = new GoodsAdapter(totalList);
        goodsAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(goodsAdapter);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextFocusChangeListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                UIUtils.showToast("asdadasdsa");
                L.i("清除操作");
                return true;
            }
        });

        //        ArrayList<Goods> goodsList = new ArrayList<>();
        //        ArrayList<Body> bodies = new ArrayList<>();
        //        for (int i = 0; i < 100; i++) {
        //            Goods goods = new Goods();
        //            goods.setName("name" + i);
        //            goods.setPrice(i + 1d);
        //            goods.setInto_num(i);
        //            String date = "2017-08-" + ((i % 31) + 1);
        //            goods.setTime(TimeUtil.getStringToDate(date));
        //            goodsList.add(goods);
        //
        //            Body body = new Body();
        //            body.setName("name" + i);
        //            body.setPrice(i + 1d);
        //            bodies.add(body);
        //        }
        //        goodsDao.insertOrReplaceInTx(goodsList);
        //        bodyDao.insertOrReplaceInTx(bodies);

        // 初始化popupwindow
        searchView.clearFocus();
        mRecycler.requestFocus();
    }

    @Override
    protected void loadNetData() {
        mSwipeRefresh.setRefreshing(false);
        handler.sendEmptyMessage(START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_time:
                TimeUtil.showDataPicker(mActivity, start_time, this);
                break;
            case R.id.end_time:
                TimeUtil.showDataPicker(mActivity, end_time, TimeUtil.getStringToDate(start_time
                        .getText().toString()
                ), this);
                break;
            default:
                break;
        }
    }

    /**
     * 选择日期
     */
    @Override
    public void onClickYes(String str) {
        startTime = TimeUtil.getStringToDate(start_time.getText().toString());
        endTime = TimeUtil.getStringToDate(end_time.getText().toString());
        if (startTime > endTime) {
            endTime = startTime;
            end_time.setText(start_time.getText().toString());
        }
        loadNetData();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            if (searchHint == null) {
                searchHint = new SearchHint(mActivity, searchView);
            }
            searchHint.show((View) searchView.getParent());
            // 刷新提示框里的数据
            List<Body> bodies = BaseApplication.getDaoInstant().getBodyDao().loadAll();
            searchHint.refresh(bodies);
        } else {
            searchHint.dismiss();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.queryText = query;
        searchView.clearFocus();
        // 隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 2);
        // 刷新结果
        loadNetData();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 刷新提示框里的数据
        List<Body> bodies = BaseApplication.getDaoInstant().getBodyDao().queryBuilder().where
                (BodyDao.Properties.Name.like(DaoUtils.like(newText))).list();
        if (searchHint != null) {
            searchHint.refresh(bodies);
        }
        if (TextUtils.equals("", newText.trim())) {
            this.queryText = "";
            loadNetData();
            Handler mainHandler = new Handler();
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    searchView.clearFocus();
                }
            }, 50);
        }
        return true;
    }

    @Override
    public void onItemClickListener(BaseRecyclerAdapter<Goods> adapter, int position) {
        String name = adapter.getData().get(position).getName();
        String One_num = adapter.getData().get(position).getOne_num();
        UIUtils.showToast(name + "==============" + One_num);
    }

    @Override
    public void onItemLongClickListener(BaseRecyclerAdapter<Goods> adapter, int position) {
    }

    @Override
    public boolean onKeyBack() {
        if (searchHint != null && searchHint.isShowing()) {
            searchHint.dismiss();
            return false;
        }
        return super.onKeyBack();
    }

    /**
     * 进货
     *
     * @param view
     */
    public void onClickAdd(View view) {
        Intent intent = new Intent(mActivity, AddActivity.class);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    // 进货成功, 刷新列表
                    loadNetData();
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}