@file:Suppress("UNREACHABLE_CODE")

package com.example.moneyflow

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyflow.DATA.Database
import com.example.moneyflow.DATA.ENTITY.DataAdapter
import com.example.moneyflow.DATA.ENTITY.User
import com.google.android.material.floatingactionbutton.FloatingActionButton


class BlankFragment : Fragment() {
    lateinit var fab:FloatingActionButton
    lateinit var recyclerView: RecyclerView
    private lateinit var database: Database
    private var list = mutableListOf<User>()
    lateinit var adapter : DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_moneyflow, container, false)
        recyclerView = v.findViewById(R.id.recycler_view)
        database = Database.getInstance(requireContext())
        fab= v.findViewById(R.id.fab_btn)!!
        adapter =DataAdapter(list)

        var intent = requireActivity().intent.extras
        var delete = intent?.getBoolean("delete",false)
        Log.d("delet", "value $delete")
        if (delete == true){
            getData()
        }


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))


        fab.setOnClickListener{
            Intent(requireContext(),EditActivity::class.java).apply {
                startActivity(this)
            }
        }

        return v

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.UserDao().getAll())
        adapter.notifyDataSetChanged()
    }


}