package com.lds.accounting;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.lds.accounting.dao.CategoryDao;
import com.lds.accounting.dao.RowDao;
import com.lds.accounting.db.AccountDatabase;
import com.lds.accounting.inputParser.Category;
import com.lds.accounting.inputParser.InputParser;
import com.lds.accounting.inputParser.Row;
import com.libs4and.widget.ArrayAdapter;

public class MainActivity extends Activity implements OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int CONTEXT_DELETE_ID = 1;
    
    private ListView listView;
    private ListAdapter adapter;
    private EditText editText;
    
    private RowDao rowDao;
    private CategoryDao categoryDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (! UserManger.getInstance(this).isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        
        setContentView(R.layout.activity_main);
        
        AccountDatabase database = AccountDatabase.getInstance(this);
        rowDao = new RowDao(database);
        categoryDao = new CategoryDao(database);
        
        initViews();
        onRefresh();
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        
        editText = (EditText) findViewById(R.id.input);
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL  
                        && event.getAction() == KeyEvent.ACTION_DOWN) { 
                    onInputDone(v.getText().toString());
                    v.setText("");
                    return true;
                }
                return false;
            }
        });
    }
    
    private void onInputDone(String input) {
        Log.i(TAG, "input: " + input);
        Row row = InputParser.parse(input);
        
        // handle Category
        Category category = row.getCategory();
        long categoryId = -1;
        if (category != null) {
            // find category id
            Category c = categoryDao.findByName(category.getName());
            if (c != null) { // use the exist id
                categoryId = c.getId();
            } else { // or create a new one
                categoryId = categoryDao.insert(category);
            }
        }
        row.setCategoryId(categoryId);
        Log.i(TAG, "row: " + row);
        rowDao.insert(row);
        
        onRefresh();
    }
    
    private void onRefresh() {
        List<Row> list = null;
        list = new RowDao(AccountDatabase.getInstance(this)).queryForAll();
        System.out.println("list:" + list);
        if (list != null) {
            adapter.refresh(list);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
       
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        
        //AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        //item = adapter.getItem(info.position);
        menu.add(0, CONTEXT_DELETE_ID, 0, R.string.delete);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
        
        Row bean = (Row) adapter.getItem(info.position);
        long id = bean.getId();
        if (id > 0) {
            if (rowDao.delete(id)) {
                adapter.remove(bean);
            }
        } else {
            Log.e(TAG, "Item id 不正确: " + id);
        }
        
        return super.onContextItemSelected(item);
    }
    
    private class ListAdapter extends ArrayAdapter<Row> {

        public ListAdapter(Context context) {
            super(context);
        }
        
        @Override
        public long getItemId(int position) {
            Row item = list.get(position);
            if (item != null) {
                return item.getId();
            }
            return -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.main_list_item, null);
                ViewHolder holder = new ViewHolder();
                holder.date = (TextView) view.findViewById(R.id.row_date);
                holder.summary = (TextView) view.findViewById(R.id.row_summary);
                holder.price = (TextView) view.findViewById(R.id.row_price);
                view.setTag(holder);
            } else {
                view = convertView;
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            
            Row bean = list.get(position); 
            
            Date date = bean.getDate();
            if (date != null) {
                holder.date.setText(AccountDatabase.datetimeFormat.format(date).substring(0, 10));
            }
            String content = null;
            if (bean.getCategory() != null) {
                content = "[" + bean.getCategory().getName() + "] " + bean.getSummary();
            } else {
                content = bean.getSummary();
            }
            holder.summary.setText(content);
            holder.price.setText(bean.getPrice()+"");
            
            return view;
        }
        
        class ViewHolder {
            TextView date;
            TextView summary;
            TextView price;
        }
        
    }

    
    
    
}
