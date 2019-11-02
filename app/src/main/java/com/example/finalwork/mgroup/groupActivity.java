package com.example.finalwork.mgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalwork.QueryActivity;
import com.example.finalwork.R;

import java.util.List;

public class groupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        data_base.open(this);


        init_group_ListView();
        init_add_Button();

        group_ListView.setAdapter(new groupListViewAdapter(this, data_base.search_memorandum_group()));


    }
    public ListView group_ListView;

    public void init_group_ListView(){
        group_ListView = (ListView) findViewById(R.id.group);
        group_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(groupActivity.this, QueryActivity.class);

                memorandum_group memorandum_group = (com.example.finalwork.mgroup.memorandum_group) group_ListView.getAdapter().getItem(position);

                intent.putExtra("group", memorandum_group.name);
//                intent.setFlags();
                startActivity(intent);


            }
        });

        group_ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                memorandum_group group = (memorandum_group) group_ListView.getAdapter().getItem(position);

                data_base.del_memorandum_group(group);

                group_ListView.setAdapter(new groupListViewAdapter(groupActivity.this, data_base.search_memorandum_group()));

                Toast.makeText(groupActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                return true;
            }

        });
    }
    public Button add_Button;

    public void init_add_Button(){
        add_Button = (Button) findViewById(R.id.add);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText v1 = new EditText(groupActivity.this);
                new AlertDialog.Builder(groupActivity.this)
                        .setTitle("请输入名字")
                        .setView(v1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Log.e("0", "groupActivity onClick " + String.valueOf(
                                        v1.getText().toString()
                                ));


                                if (v1.getText().toString().equals("")) {
                                    return;

                                }


                                data_base.add_memorandum_group(new memorandum_group(                                        v1.getText().toString()));

                                group_ListView.setAdapter(new groupListViewAdapter(groupActivity.this, data_base.search_memorandum_group()));



                                Toast.makeText(groupActivity.this, "添加成功", Toast.LENGTH_SHORT).show();


                            }
                        }).create()
                        .show();


            }
        });
    }




    public class groupListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
        private List<memorandum_group> groups ;

        /*构造函数*/
        public groupListViewAdapter(Context context,List<memorandum_group> data) {
            this.mInflater = LayoutInflater.from(context);
            groups = data ;
        }


        public List<memorandum_group> getData() {
            return groups;
        }

        @Override
        public int getCount() {
            return getData().size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return groups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            groupListViewViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.group_item, null);
                holder = new groupListViewViewHolder();
                    /*得到各个控件的对象*/
                holder.name = convertView.findViewById(R.id.name);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (groupListViewViewHolder) convertView.getTag();//取出ViewHolder对象
            }


            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            holder.name.setText(getData().get(position).name);

            return convertView;

        }

    }


    /*存放控件*/
    public final class groupListViewViewHolder {
        public TextView name;
    }


}


