package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* Implementation of link ViewHolder*/
public class LinkViewHolder  extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView linkTV;
    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.name);
        this.linkTV = itemView.findViewById(R.id.link);
    }

    public void bindLink(LinkInfo linkInfo) {
        nameTV.setText(linkInfo.getName());
        linkTV.setText(linkInfo.getLink());
    }
}
