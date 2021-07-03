package com.equinoxlab.ui.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equinoxlab.R
import com.equinoxlab.data.local.entities.Employees
import com.equinoxlab.ui.interfaces.OnEmployeeClicked


class EmployeeViewHolder(
    itemView: View?,
    val listener: OnEmployeeClicked
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var tvName: TextView
    var tvDeptName: TextView
    private var mEmployees: Employees? = null

    init {
        tvName = itemView!!.findViewById(R.id.tvName)
        tvDeptName = itemView!!.findViewById(R.id.tvDeptName)

        itemView?.setOnClickListener(this)

    }

    fun bindNowShowingData(mEmployees: Employees?) {
        if (mEmployees == null) {
            return
        } else {

            this.mEmployees = mEmployees

            tvName.setText("Name: ${mEmployees.name}")
            tvDeptName.setText("Dept: ${mEmployees.dept_name}")
//            tv_Item.setPaintFlags(tv_Item.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onItemClicked(mEmployees!!)
        }
    }

}