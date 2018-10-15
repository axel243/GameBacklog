package com.example.marmm.demolevel3

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class GameAdapter(private var mGames: List<Game>, private val mGameClickListener: GameClickListener, val mResources: Resources) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    interface GameClickListener {
        fun gameOnClick(i: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.game_card, parent, false)
        // Return a new holder instance
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        val game = mGames[position]
        holder.textView.text = game.getTitleText()
        holder.platformTextView.text = game.getPlatform()
        holder.dateTextView.text = game.getDateModified()
        holder.statusTextView.text = mResources.getStringArray(R.array.status_array)[game.getStatus()!!];
    }

    override fun getItemCount(): Int {
        return mGames.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun swapList(newList: List<Game>) {
        mGames = newList

        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged()

        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textView: TextView = itemView.findViewById(R.id.titleTextView)
        var platformTextView: TextView = itemView.findViewById(R.id.platformTextView)
        var statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        var dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val clickedPosition = adapterPosition
            mGameClickListener.gameOnClick(clickedPosition)
        }

    }


}
