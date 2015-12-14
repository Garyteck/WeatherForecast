package com.garytech.weatherfocast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.garytech.weatherfocast.helpers.DayFormatter;
import com.garytech.weatherfocast.helpers.TemperatureFormatter;
import com.garytech.weatherfocast.model.Forecast;
import com.garytech.weatherforecast.R;
import com.squareup.picasso.Picasso;


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
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Forecast prevision= (Forecast) getItem(position);

        final DayFormatter dayFormatter = new DayFormatter();
        final String day = dayFormatter.format(Long.valueOf(prevision.getDt()));
        viewHolder.dayTextView.setText(day);
        viewHolder.temperature.setText(TemperatureFormatter.format(Float.valueOf(prevision.getTemp().getDay())));

        Picasso.with(getContext())
                .load(getContext().getString(R.string.icon_url).concat(prevision.getWeather()[0].getIcon().concat(getContext().getString(R.string.icon_extension))))

                .into(viewHolder.icon);
        return convertView;
    }


    class ViewHolder {
        private TextView dayTextView;
        private TextView temperature;
        private ImageView icon;
    }


}
