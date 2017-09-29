package recycleview.larry.com.recyclerviewadapter;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import recycleview.larry.com.library.BaseRecyclerViewAdapter;
import recycleview.larry.com.library.MyLinerDivider;
import recycleview.larry.com.library.RecyclerSupportViewEmpty;

public class MainActivity extends AppCompatActivity {

    private View emptyView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View pView) {
                                ArrayList<String> list=new ArrayList<String>();
                                list.add("qeqww");
                                list.add("rfewfw");
                                myAdapter.addDatas(list);
                            }
                        }).show();
            }
        });
        RecyclerSupportViewEmpty recyclerView = (RecyclerSupportViewEmpty) findViewById(R.id.recyclerView);
        emptyView=(View)findViewById(R.id.empty_View);
        recyclerView.setEmptyView(emptyView);
        setRecyclerView(recyclerView);
        setOther(recyclerView);
    }

    private void setOther(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new MyLinerDivider(Color.GREEN));
    }

    private void setRecyclerView(RecyclerSupportViewEmpty recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        myAdapter.addDatas(initList());
        addHeadView(myAdapter);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击的是" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeadView(MyAdapter myAdapter) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_head, null, false);
        myAdapter.addheadView(view);
    }

    private ArrayList<String> initList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("数据项" + i);
        }
        list.add("1223\nfdaf");
        return list;
    }


    private void test() {
        ListView listView = null;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
