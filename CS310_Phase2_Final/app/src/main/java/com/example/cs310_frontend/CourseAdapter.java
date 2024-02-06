package com.example.cs310_frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context ctx;
    List<OnlyList> data;

    public CourseAdapter(Context ctx, List<OnlyList> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.activity_row, parent, false);
        return new CourseViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.idTxt.setText(data.get(position).getProgram().toString());
        holder.codeTxt.setText(data.get(position).getCode().toString());
        holder.typeTxt.setText(data.get(position).getType().toString());

        holder.row.setOnClickListener(v -> {
            Toast.makeText(ctx, data.get(position).getProgram(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView typeTxt;
        TextView codeTxt;
        TextView idTxt;

        ConstraintLayout row;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTxt = itemView.findViewById(R.id.typetxt);
            idTxt = itemView.findViewById(R.id.idtxt);
            codeTxt = itemView.findViewById(R.id.codetxt);

            row = (ConstraintLayout) itemView;
        }

    }

}
