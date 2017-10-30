package boxing.com.store;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import boxing.com.store.base.BaseActivity;
import boxing.com.store.sql.Body;
import boxing.com.store.sql.Goods;
import boxing.com.store.utils.TimeUtil;
import boxing.com.store.utils.UIUtils;

/**
 * 进货
 */
public class AddActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private Toolbar toolbar;
    private EditText add_name;
    private EditText add_price;
    private EditText add_num;
    private EditText add_one_num;
    private TextView add_time;
    private TextView total;
    // 零售价
    private EditText one_price;

    @Override
    protected void initData() {
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_name = (EditText) findViewById(R.id.add_name);
        add_price = (EditText) findViewById(R.id.add_price);
        add_price.addTextChangedListener(this);
        add_num = (EditText) findViewById(R.id.add_num);
        add_num.addTextChangedListener(this);
        one_price = (EditText) findViewById(R.id.one_price);
        add_one_num = (EditText) findViewById(R.id.add_one_num);
        add_time = (TextView) findViewById(R.id.add_time);
        add_time.setText(TimeUtil.getChinaDateTime(System.currentTimeMillis()));
        add_time.setOnClickListener(this);
        total = (TextView) findViewById(R.id.total);
    }

    @Override
    protected void loadNetData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_time:
                TimeUtil.showDataPicker(mActivity, add_time, null);
                break;
        }
    }

    /**
     * 提交
     */
    public void onSubmit(View view) {
        if (TextUtils.isEmpty(add_name.getText())) {
            UIUtils.showToast("请选择货物名称");
            return;
        }
        if (TextUtils.isEmpty(add_price.getText())) {
            UIUtils.showToast("请输入进价");
            return;
        }
        if (TextUtils.isEmpty(add_num.getText())) {
            UIUtils.showToast("请输入一共几件");
            return;
        }
        Goods goods = new Goods();
        goods.setName(add_name.getText().toString());
        goods.setPrice(Double.valueOf(add_price.getText().toString()));
        goods.setOne_price(one_price.getText().toString());
        goods.setInto_num(add_num.getText().toString());
        goods.setTime(TimeUtil.getStringToDate(add_time.getText().toString()));
        goods.setOne_num(add_one_num.getText().toString());
        long insert = goodsDao.insert(goods);

        Body body = new Body();
        body.setName(add_name.getText().toString());
        body.setPrice(Double.valueOf(add_price.getText().toString()));
        body.setOne_price(one_price.getText().toString());
        long insert1 = bodyDao.insert(body);

        if (insert >= 0 && insert1 >= 0) {
            setResult(RESULT_OK);
            finish();
        } else {
            // 插入失败
            UIUtils.showToast("保存失败, 请重新保存");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 自动合计
        if (!TextUtils.isEmpty(add_price.getText()) && !TextUtils.isEmpty(add_num.getText())) {
            BigDecimal decimalPrice = new BigDecimal(add_price.getText().toString());
            BigDecimal decimalNum = new BigDecimal(add_num.getText().toString());
            total.setText((decimalPrice.multiply(decimalNum).toString() + "元"));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        UIUtils.closeSoftInput(add_name);

    }

}
