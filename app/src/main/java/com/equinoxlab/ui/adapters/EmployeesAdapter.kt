package com.equinoxlab.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.equinoxlab.R
import com.equinoxlab.data.local.entities.Employees
import com.equinoxlab.ui.interfaces.OnEmployeeClicked
import com.equinoxlab.ui.view.viewholder.EmployeeViewHolder
import java.util.*


class EmployeesAdapter(private val listener: OnEmployeeClicked) :
    RecyclerView.Adapter<EmployeeViewHolder>(),
    Filterable {

    public var filteredList: MutableList<Employees> = arrayListOf()

    var employees: List<Employees>? = null
        set(value) {
            field = value
            filteredList.addAll(value!!)
            notifyDataSetChanged()
        }

    override fun getItemCount() = filteredList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employees, parent, false)
        return EmployeeViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee: Employees? = filteredList?.get(position)!!
        if (filteredList != null) {
            val viewHolder = holder as EmployeeViewHolder
            viewHolder.bindNowShowingData(employee)
        } else {
            notifyItemRemoved(position)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList.clear()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(employees!!)
                } else {
                    for (row in employees!!) {
                        if (row.name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                            row.dept_name!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }

}
