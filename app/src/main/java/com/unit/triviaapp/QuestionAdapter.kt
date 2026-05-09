package com.unit.triviaapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(
    private val questions: List<Question>
): RecyclerView.Adapter<QuestionAdapter.QuestionViewholder>(){

    class QuestionViewholder(view: View): RecyclerView.ViewHolder(view){
        val textQuestion: TextView
        val optionsContainer: LinearLayout

        init {
            textQuestion = view.findViewById<TextView>(R.id.tvQuestion)
            optionsContainer = view.findViewById<LinearLayout>(R.id.llOptionsContainer)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question,parent, false)

        return QuestionViewholder(view)
    }

    override fun onBindViewHolder(
        holder: QuestionViewholder,
        position: Int
    ) {
        holder.textQuestion.text = questions[position].question

        holder.optionsContainer.removeAllViews()

        for( option in questions[position].options){
            val textView = TextView(holder.itemView.context)
            textView.text = option
            holder.optionsContainer.addView(textView)
        }
    }

    override fun getItemCount() = questions.size

}