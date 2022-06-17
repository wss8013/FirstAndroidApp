package edu.neu.madcourse.NUMAD22Su_ShashaWang;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* Implementation of link ViewHolder*/
public class LinkViewHolder  extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView linkTV;
    private Context context;
    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        this.nameTV = itemView.findViewById(R.id.name);
        this.linkTV = itemView.findViewById(R.id.link);
    }

    public void bindLink(LinkInfo linkInfo) {
        itemView.setClickable(true);
        nameTV.setText(linkInfo.getName());
        nameTV.setClickable(true);
        nameTV.setOnClickListener(new LinkListener());
        linkTV.setText(linkInfo.getLink());
        linkTV.setClickable(true);
        linkTV.setOnClickListener(new LinkListener());
    }

    public class LinkListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String link  = linkTV.getText().toString();
            if (!link.startsWith("http://") && !link.startsWith("https://") ) {
                link = "http:://" +link;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            Bundle bundle = new Bundle();
            startActivity(context,browserIntent,bundle);
        }
    }
}
