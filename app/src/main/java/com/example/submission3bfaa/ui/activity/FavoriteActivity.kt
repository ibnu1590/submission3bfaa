package com.example.submission3bfaa.ui.activity

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3bfaa.R
import com.example.submission3bfaa.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.submission3bfaa.db.MappingHelper
import com.example.submission3bfaa.ui.adapter.UserAdapter
import com.example.submission3bfaa.utils.User
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        title = getString(R.string.favorite_users)

        initRecyclerView()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUsersAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadUsersAsync()
        }

    }

    private fun initRecyclerView() {
        adapter = UserAdapter(onClick = { user: User ->
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra(InfoActivity.EXTRA_USER, user)
            startActivity(intent)
        }, onLongClick = { _, _ ->
        })
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.hasFixedSize()
        rv_favorite.adapter = adapter
    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_bar_fav.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progress_bar_fav.visibility = View.INVISIBLE

            val notes = deferredNotes.await()
            addUsersToAdapter(notes)
        }
    }

    private fun addUsersToAdapter(notes: ArrayList<User>) {
        when {
            notes.isNotEmpty() -> {
                adapter.addAll(notes)
            }
            else -> {
                adapter.addAll(notes)
                Toast.makeText(
                    this@FavoriteActivity,
                    "Tidak ada data saat ini",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadUsersAsync()
    }
}