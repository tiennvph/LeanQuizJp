package com.app.learnquizjp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.R
import com.app.learnquizjp.adapter.DefaultAdapter
import com.app.learnquizjp.dao.KotobaDAO
import com.app.learnquizjp.model.Kotoba
import com.app.learnquizjp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_favorite.view.rvKotoba



class FavoriteFragment : Fragment(){

    private var data : MutableList<Kotoba> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_favorite, container, false)
        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val ref = FirebaseDatabase.getInstance().getReference("users").child(user!!.uid)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    for(i in 0 until user.favorite.size){
                        KotobaDAO(view.context).getSearchKotoba(user.favorite[i] + 1)?.let { data.add(it) }
                        val defaultAdapter = DefaultAdapter(data)
                        val linearLayoutManager = LinearLayoutManager(view.context)
                        view.rvKotoba.apply {
                            setHasFixedSize(true)
                            layoutManager = linearLayoutManager
                            adapter = defaultAdapter
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("result", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        ref.addValueEventListener(userListener)

        return view
    }
}