package com.example.android.recyclerviewinteractions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements TodoAdapter.OnTodoCheckedChangeListener {

    private RecyclerView mTodoListRecyclerView;
    private EditText mTodoEntryEditText;

    private TodoAdapter mAdapter;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToast = null;

        mTodoListRecyclerView = (RecyclerView)findViewById(R.id.rv_todo_list);

        mTodoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTodoListRecyclerView.setHasFixedSize(true);

        mAdapter = new TodoAdapter(this);
        mTodoListRecyclerView.setAdapter(mAdapter);

        mTodoListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTodoEntryEditText = (EditText)findViewById(R.id.et_todo_entry);

        Button addTodoButton = (Button)findViewById(R.id.btn_add_todo);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = mTodoEntryEditText.getText().toString();
                if (!TextUtils.isEmpty(todoText)) {
                    mTodoListRecyclerView.scrollToPosition(0);
                    mAdapter.addTodo(todoText);
                    mTodoEntryEditText.setText("");
                }
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((TodoAdapter.TodoViewHolder)viewHolder).removeFromList();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mTodoListRecyclerView);

    }

    @Override
    public void onTodoCheckedChanged(String todo, boolean isChecked) {
        if (mToast != null) {
            mToast.cancel();
        }
        String completionState = isChecked ? "COMPLETED" : "MARKED INCOMPLETE";
        String toastText = completionState + ": " + todo;
        mToast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        mToast.show();
    }
}
