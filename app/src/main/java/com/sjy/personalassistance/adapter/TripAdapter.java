package com.sjy.personalassistance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjy.personalassistance.R;
import com.sjy.personalassistance.activity.TripActivity;
import com.sjy.personalassistance.database.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private Context mContext;

    private List<Trip> mTripList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title, location, startTime, endTime;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            title = (TextView) view.findViewById(R.id.t_title);
            location = (TextView) view.findViewById(R.id.t_location);
            startTime = (TextView) view.findViewById(R.id.t_starttime);
            endTime = (TextView) view.findViewById(R.id.t_endtime);
        }
    }

    public TripAdapter(List<Trip> tripList){
        mTripList = tripList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.trip_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Trip trip = mTripList.get(position);
                Intent intent = new Intent(mContext, TripActivity.class);
                intent.putExtra("id", trip.getId());
                intent.putExtra("title", trip.getTitle());
                intent.putExtra("location", trip.getLocation());
                intent.putExtra("starttime", trip.getStartTime());
                intent.putExtra("endtime", trip.getEndTime());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trip trip = mTripList.get(position);
        holder.title.setText(trip.getTitle());
        holder.location.setText(trip.getLocation());
        holder.startTime.setText(trip.getStartTime());
        holder.endTime.setText(trip.getEndTime());
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }
}
