package io.github.gndps.eatsleephustlerepeat

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import java.util.ArrayList

/**
 * Created by Gundeep on 02/11/17.
 */

class ThingsRecyclerViewAdapter(private val mContext: Context, var allThings: ArrayList<ThingsModel>) : RecyclerView.Adapter<ThingsRecyclerViewAdapter.ThingsRecyclerViewHolder>() {

    val TAG : String = "ThingsRVA"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingsRecyclerViewHolder? =
            ThingsRecyclerViewHolder(parent.inflate(R.layout.item_rv_thing))

    override fun onBindViewHolder(holder: ThingsRecyclerViewHolder, position: Int) {
        Log.d(TAG, "binding view holder at position " + position)
        holder.updateView(allThings[position])
    }

    override fun getItemCount(): Int = allThings.size

    inner class ThingsRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView : ImageView = itemView.findViewById(R.id.iv_rv_item)
        fun updateView(thing:ThingsModel) {
            Log.d(TAG, "updating view for " + thing.thingType)
            if(thing.selected) {
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, thing.selectedImage))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, thing.unselectedImage))
            }
            imageView.setOnClickListener {
                for (thingy in allThings) {
                    thingy.selected = false
                }
                thing.selected = true
                RealmUtil.justSelected(thing.thingType)
                notifyDataSetChanged()
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}