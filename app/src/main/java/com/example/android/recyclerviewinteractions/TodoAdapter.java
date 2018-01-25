package com.example.android.recyclerviewinteractions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hessro on 1/18/18.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<String> mTodoList;

    private OnTodoCheckedChangeListener mCheckedChangeListener;

    public TodoAdapter(OnTodoCheckedChangeListener checkedChangeListener) {
        mTodoList = new ArrayList<String>();
        mCheckedChangeListener = checkedChangeListener;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_list_item, parent, false);
        TodoViewHolder viewHolder = new TodoViewHolder(view);
        return viewHolder;
    }

    private int adapterPositionToArrayIdx(int position) {
        return mTodoList.size() - position - 1;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        String todo = mTodoList.get(this.adapterPositionToArrayIdx(position));
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public void addTodo(String todo) {
        mTodoList.add(todo);
//        notifyDataSetChanged();
        notifyItemInserted(0);
    }

    public interface OnTodoCheckedChangeListener {
        void onTodoCheckedChanged(String todo, boolean isChecked);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {

        private TextView mTodoText;

        public TodoViewHolder(final View itemView) {
            super(itemView);
            mTodoText = (TextView)itemView.findViewById(R.id.tv_todo_text);

            CheckBox checkbox = (CheckBox)itemView.findViewById(R.id.todo_checkbox);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String todo = mTodoList.get(adapterPositionToArrayIdx(getAdapterPosition()));
                    mCheckedChangeListener.onTodoCheckedChanged(todo, isChecked);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bind(String todo) {
            mTodoText.setText(todo);
        }

        public void removeFromList() {
            int position = getAdapterPosition();
            mTodoList.remove(adapterPositionToArrayIdx(position));
            notifyItemRemoved(position);
        }
    }

}
