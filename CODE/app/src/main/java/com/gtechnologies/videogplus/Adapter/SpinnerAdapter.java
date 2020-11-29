package com.gtechnologies.videogplus.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gtechnologies.videogplus.Library.Utility;
import com.gtechnologies.videogplus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 4/3/2018.
 */

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    List<String> values;
    Utility utility;

    public SpinnerAdapter(Context context){
        this.context = context;
        utility = new Utility(this.context);
        values = new ArrayList<>();
        String[] val = context.getResources().getStringArray(R.array.quality_array);
        for(int i=0; i<val.length; i++){
            values.add(val[i]);
        }
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_layout,null);
            }
            TextView text = (TextView)convertView.findViewById(R.id.text);
            //View view = (View) convertView.findViewById(R.id.label);
            text.setText(values.get(position));
/*            if(position>=(values.size()-1)){
                view.setVisibility(View.GONE);
            }*/
        }
        catch (Exception ex){
            utility.logger(ex.toString());
        }
        return convertView;
    }
}
