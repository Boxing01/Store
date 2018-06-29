package boxing.com.store.views;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.List;

import boxing.com.store.R;
import boxing.com.store.adapters.SearchHintAdapter;
import boxing.com.store.base.BaseActivity;
import boxing.com.store.base.BaseApplication;
import boxing.com.store.base.BaseRecyclerAdapter;
import boxing.com.store.divider.RecyclerItemDivider;
import boxing.com.store.interfaces.ItemClickListener;
import boxing.com.store.sql.Body;
import boxing.com.store.utils.UIUtils;

/**
 * Created by 搜索货物提示框 on 2017/9/14.
 */

public class SearchHint implements ItemClickListener<Body> {
    private SearchHintAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BaseActivity context;
    private PopupWindow popupWindow;
    private View view;

    public SearchHint(BaseActivity context, SearchView searchView) {
        this.context = context;
        this.searchView = searchView;
        recyclerView = new RecyclerView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerItemDivider(R.color.line, 1,
                LinearLayoutManager.VERTICAL));
        List<Body> bodies = BaseApplication.getDaoInstant().getBodyDao().loadAll();
        adapter = new SearchHintAdapter(bodies);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        popupWindow = new PopupWindow(recyclerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(UIUtils.getColor(R.color.bg_f0)));
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //        popupWindow.setOutsideTouchable(true);

    }

    public void show(View view) {
        this.view = view;
        popupWindow.showAsDropDown(view);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void refresh(List<Body> bodies) {
        if (bodies.size() == 0) {
            popupWindow.dismiss();
        } else if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(view);
        }
        adapter.refreshData(bodies);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onItemClickListener(BaseRecyclerAdapter<Body> adapter, int position) {
        String name = adapter.getData().get(position).getName();
        UIUtils.showToast(name);
        searchView.setQuery(name, true);
    }

    @Override
    public void onItemLongClickListener(BaseRecyclerAdapter<Body> adapter, int position) {

    }
}
