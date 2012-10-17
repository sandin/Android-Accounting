package com.lds.accounting;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.j256.ormlite.dao.Dao;
import com.lds.accounting.dao.DaoFactory;
import com.lds.accounting.inputParser.InputParser;
import com.lds.accounting.inputParser.Row;
import com.libs4and.widget.ArrayAdapter;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    
    private ListView listView;
    private ListAdapter adapter;
    private EditText editText;
    
    Dao<Row, Long> rowDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rowDao = DaoFactory.createDao(getApplicationContext(), Row.class);
        
        initViews();
        onRefresh();
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this);
        listView.setAdapter(adapter);
        
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
        Log.i(TAG, "row: " + row);
        try {
            rowDao.create(row);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        onRefresh();
    }
    
    private void onRefresh() {
        List<Row> list = null;
        try {
            list = rowDao.queryForAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    
    private class ListAdapter extends ArrayAdapter<Row> {

        public ListAdapter(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
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
                holder.date.setText(date.toLocaleString());
            }
            holder.summary.setText(bean.getContent());
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
