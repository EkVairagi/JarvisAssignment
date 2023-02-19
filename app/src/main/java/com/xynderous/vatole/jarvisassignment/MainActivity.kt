package com.xynderous.vatole.jarvisassignment

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xynderous.vatole.jarvisassignment.Utils.ApplicationConstants
import com.xynderous.vatole.jarvisassignment.Utils.CommonUtils
import com.xynderous.vatole.jarvisassignment.adapters.UserProfileListAdapter
import com.xynderous.vatole.jarvisassignment.database.UserProfile
import com.xynderous.vatole.jarvisassignment.databinding.ActivityMainBinding
import com.xynderous.vatole.jarvisassignment.ui.UserProfileActivity
import com.xynderous.vatole.jarvisassignment.viemodel.UserProfileViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() ,View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickEvent()
        viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        binding.btnNew.setOnClickListener { userProfile(null) }

        val rc = binding.recyclerView
        rc.layoutManager = LinearLayoutManager(this)
        val userProfileAdapter = UserProfileListAdapter(this,this@MainActivity){ userProfile(it) }
        rc.adapter = userProfileAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getAllUser().collect {
                userProfileAdapter.submitList(it)
            }
        }
    }

    private fun setOnClickEvent() {

    }

    private fun userProfile(userProfile: UserProfile?) {
        val userProfileActivity = Intent(this, UserProfileActivity::class.java)
        userProfileActivity.putExtra(ApplicationConstants.USER,userProfile)
        startActivity(userProfileActivity)
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id){
                R.id.ivDeleteUser -> {
                    CommonUtils.genericCastOrNull<UserProfile>(v.getTag(R.id.model) ?: "")?.let { userProfile ->
                        viewModel.deleteUser(userProfile)
                    }
                }
                else -> {}
            }
        }
    }


}