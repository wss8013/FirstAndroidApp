package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final List<LinkInfo> links;
    private final Context context;
    public LinkAdapter(List<LinkInfo> links, Context context) {
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.link_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        holder.bindLink(links.get(position));
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return links.size();
    }



}
