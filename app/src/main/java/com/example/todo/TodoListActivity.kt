package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.todo.dummy.DummyContent
import com.example.todo.model.Todo
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.todo_list_content.view.*
import kotlinx.android.synthetic.main.todo_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [TodoDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class TodoListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (todo_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(todo_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val todoList = mutableListOf<Todo>()

        var itemClickListener: TodoItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_todo, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val todo = todoList[position]

            holder.todo = todo

            holder.tvTitle.text = todo.title
            holder.tvDueDate.text = todo.dueDate

            val resource = when (todo.priority) {
                Todo.Priority.LOW -> R.drawable.ic_low
                Todo.Priority.MEDIUM -> R.drawable.ic_medium
                Todo.Priority.HIGH -> R.drawable.ic_high
            }
            holder.ivPriority.setImageResource(resource)
        }

        fun addItem(todo: Todo) {
            val size = todoList.size
            todoList.add(todo)
            notifyItemInserted(size)
        }

        fun addAll(todos: List<Todo>) {
            val size = todoList.size
            todoList += todos
            notifyItemRangeInserted(size, todos.size)
        }

        fun deleteRow(position: Int) {
            todoList.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun getItemCount() = todoList.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvDueDate: TextView = itemView.tvDueDate
            val tvTitle: TextView = itemView.tvTitle
            val ivPriority: ImageView = itemView.ivPriority

            var todo: Todo? = null

            init {
                itemView.setOnClickListener {
                    todo?.let { todo -> itemClickListener?.onItemClick(todo) }
                }

                itemView.setOnLongClickListener { view ->
                    itemClickListener?.onItemLongClick(adapterPosition, view)
                    true
                }
            }
        }

        interface TodoItemClickListener {
            fun onItemClick(todo: Todo)
            fun onItemLongClick(position: Int, view: View): Boolean
        }

    }


}
