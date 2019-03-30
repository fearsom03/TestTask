package com.example.mtravel;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private JSONArray allnews;
    LayoutInflater inflater;
    RequestQueue queue;
    @Override
    public int getCount() {
        return allnews.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if ((convertView==null)||(viewHolder==null)){
            convertView = inflater.from(context).inflate(R.layout.lastnews_adapter, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            System.out.println(allnews+" HELLOOOOOO");
            JSONObject news =allnews.getJSONObject(position);
            String just_name = news.getString("name");
            just_name =MainActivity.decoderUnicode(just_name);
            String just_description = news.getString("short_text");
            just_description = MainActivity.decoderUnicode(just_description);
            //description maximum 30 letters
            String sample="";
            if (just_name.equals("null")){
                just_name="";
            }else if (just_description.equals("null")){
                just_description="";}
            String imageUrl = news.getString("image");

            viewHolder.description.setText(Html.fromHtml(just_description));
            viewHolder.tittle.setText(Html.fromHtml(just_name));
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder{
        TextView tittle,description;
        ImageView imageView;

        public ViewHolder(View convertView) {
            tittle=convertView.findViewById(R.id.titleLastNews);
            description=convertView.findViewById(R.id.desc_news);
            imageView=convertView.findViewById(R.id.imageLastNews);

        }
    }
    NewsAdapter(Context context, JSONArray news){
        this.context=context;
        this.allnews =news;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        queue = Volley.newRequestQueue(context);

    }
}
