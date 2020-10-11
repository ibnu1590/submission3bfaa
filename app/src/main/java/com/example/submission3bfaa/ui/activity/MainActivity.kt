package com.example.submission3bfaa.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3bfaa.R
import com.example.submission3bfaa.ui.adapter.UserAdapter
import com.example.submission3bfaa.utils.User
import com.example.submission3bfaa.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    private lateinit var adapter: UserAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SearchViewModel::class.java
        )

        searchViewModel.searchResults.observe(this, Observer { list: List<User>? ->
            progress(false)
            adapter.addAll(list)
        })

        initRecyclerView()
        edit_search.setOnEditorActionListener(this)
    }

    private fun initRecyclerView() {
        adapter = UserAdapter(onClick = { user: User ->
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra(InfoActivity.EXTRA_USER, user)
            startActivity(intent)
        })
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
    }

    private fun progress(isVisible: Boolean) {
        iv_search.visibility = View.GONE
        progress_bar.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val query = textView?.text.toString()
            progress(true)
            searchViewModel.searchUser(query)
            return true
        }
        return false
    }
}