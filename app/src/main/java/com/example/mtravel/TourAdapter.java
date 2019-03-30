package com.example.mtravel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private int count;
    private JSONArray result;

    public TourAdapter(int countObjects, JSONArray Data) {
        this.count = countObjects;
        this.result = Data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tourse_adapter,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        //viewHolder.subData.setText(result.getString("s").toString());
        try {
            JSONObject tour = result.getJSONObject(i);
            String name = tour.getString("name");
            name =MainActivity.decoderUnicode(name);
            String description = tour.getString("short_text");
            description = MainActivity.decoderUnicode(description);
            //description maximum 30 letters

            String sample="";
            if (name.equals("null")){
                name="";
            }else if (description.equals("null")){
                description="";}
            String imageUrl = tour.getString("image");

            viewHolder.name.setText(Html.fromHtml(name));
            int number =330;
            if(description.length()>number){
                for (int j=0;j<number;j++){
                    sample+=description.charAt(j);
                }
                description=sample;
                description+="...";
            }
            viewHolder.descripting.setText(Html.fromHtml(description));
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            //implements View.OnClickListener
    {
        TextView name;
        TextView descripting;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Data);
            descripting = itemView.findViewById(R.id.subData);
            image = itemView.findViewById(R.id.ImageView_row);
        }
//        @Override
//        public void onClick(View v) {
//
//        }
    }
}
