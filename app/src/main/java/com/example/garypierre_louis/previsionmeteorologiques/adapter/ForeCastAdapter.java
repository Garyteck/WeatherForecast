package com.example.garypierre_louis.previsionmeteorologiques.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.garypierre_louis.previsionmeteorologiques.helpers.DayFormatter;
import com.example.garypierre_louis.previsionmeteorologiques.helpers.TemperatureFormatter;
import com.example.garypierre_louis.previsionmeteorologiques.model.Forecast;
import com.example.garypierre_louis.previsionmeteorologiques.R;


/**
 *Adapter for the ListView in the ListFragment, it uses the ViewHolder pattern
 */
public class ForeCastAdapter extends ArrayAdapter implements ListAdapter{

    public ForeCastAdapter(final Forecast[] forecast, final Context context) {
        super(context, 0, forecast);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.dayTextView = (TextView) convertView.findViewById(R.id.date);
            viewHolder.temperature = (TextView) convertView.findViewById(R.id.temperature);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Forecast prevision= (Forecast) getItem(position);

        final DayFormatter dayFormatter = new DayFormatter();
        final String day = dayFormatter.format(Long.valueOf(prevision.getDt()));
        viewHolder.dayTextView.setText(day);
        viewHolder.temperature.setText(TemperatureFormatter.format(Float.valueOf(prevision.getTemp().getDay())));
        return convertView;
    }


    class ViewHolder {
        private TextView dayTextView;
        private TextView temperature;
    }


}
