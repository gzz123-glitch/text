package com.example.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<Users> UserInAdapter;
    public UsersAdapter(List<Users> userInAdapter) {
        UserInAdapter = userInAdapter;
    }


    private int selectIndex;

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }


    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        Users user=UserInAdapter.get(position);

        holder.userNameTextView.setText(user.getName());
        holder.timeTextView.setText(user.getTime());
        holder.contentTextView.setText(user.getContent());
        holder.likeCountTextView.setText(user.getLikeCount());
        holder.commentCountTextView.setText(user.getCommentCount());
        holder.avatarImageView.setImageResource(user.getUserimgID());
        holder.moreOptionsImageView.setImageResource(user.getMoreOptionsIcon());
        holder.contentImageView.setImageResource(user.getContentImage());
        holder.likeImageView.setImageResource(user.getLikeIcon());
        holder.commentImageView.setImageResource(user.getCommentIcon());
        holder.shareImageView.setImageResource(user.getShareIcon());
    }

    @Override
    public int getItemCount() {
        return UserInAdapter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userNameTextView;
        TextView timeTextView;
        TextView commentCountTextView;
        TextView contentTextView;
        TextView likeCountTextView;
        ImageView avatarImageView;
        ImageView contentImageView;
        ImageView moreOptionsImageView;
        ImageView likeImageView;
        ImageView commentImageView;
        ImageView shareImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            commentCountTextView = itemView.findViewById(R.id.commentCountTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);

            avatarImageView= itemView.findViewById(R.id.avatarImageView);
            contentImageView= itemView.findViewById(R.id.contentImageView);
            moreOptionsImageView= itemView.findViewById(R.id.moreOptionsImageView);
            likeImageView= itemView.findViewById(R.id.likeImageView);
            commentImageView= itemView.findViewById(R.id.commentImageView);
            shareImageView= itemView.findViewById(R.id.shareImageView);

        }
    }
}
