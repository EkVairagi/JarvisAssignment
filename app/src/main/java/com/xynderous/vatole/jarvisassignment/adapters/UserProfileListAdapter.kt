package com.xynderous.vatole.jarvisassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xynderous.vatole.jarvisassignment.R
import com.xynderous.vatole.jarvisassignment.database.UserProfile
import com.xynderous.vatole.jarvisassignment.databinding.ItemUserBinding


class UserProfileListAdapter(val context: Context,val listener: View.OnClickListener?, private val onItemClicked:(UserProfile)-> Unit):
    ListAdapter<UserProfile, UserProfileListAdapter.UserProfileViewHolder>(UserProfileDiffCallback)
{
    inner class UserProfileViewHolder(private var binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(userProfile: UserProfile, context: Context) {

            if (userProfile.imageUrl.isNotEmpty()) {
                Glide.with(context).load(userProfile.imageUrl).into(binding.ivUserProfile)
            }

            binding.tvName.text = userProfile.fname.plus(" ").plus(userProfile.lname)
            binding.tvMobileNo.text = userProfile.mobile
            binding.tvMobileNo.text = userProfile.mobile

           binding.tvAddress.text = userProfile.houseno.plus(" ").
           plus(userProfile.area).plus(" ").plus(userProfile.pincode).plus(" ")
               .plus(userProfile.city).plus(" ").plus(userProfile.state)


            binding.tvEducationName.text = userProfile.level
            binding.tvEducationYear.text = userProfile.start_year.plus(" ").plus(userProfile.end_year)
            binding.tvCollegeName.text = userProfile.college_name

            binding.ivDeleteUser.setOnClickListener {
                it.setTag(R.id.model, userProfile)
                onClick(it)
            }


        }

        override fun onClick(v: View?) {
            v?.let {
                listener?.onClick(v)
            }
        }

    }

    companion object {

        private val UserProfileDiffCallback = object : DiffUtil.ItemCallback<UserProfile>() {
            override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {

        val viewHolder = UserProfileViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        viewHolder.itemView.setOnClickListener {
            val position =viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        holder.bind(getItem(position), context)
    }

}