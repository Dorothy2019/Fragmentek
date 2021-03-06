package com.example.todo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.example.todo.adapter.SimpleItemRecyclerViewAdapter
import com.example.todo.model.Todo
import kotlinx.android.synthetic.main.activity_todo_detail.*
import kotlinx.android.synthetic.main.todo_list.*

/**
 * An activity representing a single Todo detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [TodoListActivity].
 */
class TodoDetailActivity : AppCompatActivity(){
    companion object {
        const val KEY_DESC = "KEY_DESC"
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            val fragment = TodoDetailFragment.newInstance(intent.getStringExtra(KEY_DESC))
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            supportFragmentManager.beginTransaction()
                .add(R.id.todo_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, TodoListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
