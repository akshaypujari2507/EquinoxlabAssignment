package com.equinoxlab.ui.view

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.equinoxlab.R
import com.equinoxlab.data.local.entities.Employees
import com.equinoxlab.data.remote.model.ApiResponse
import com.equinoxlab.ui.adapters.EmployeesAdapter
import com.equinoxlab.ui.interfaces.OnEmployeeClicked
import com.equinoxlab.ui.viewmodel.MainActivityViewModel
import com.greenlight.di.Injection
import com.greenlight.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnEmployeeClicked {

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var progressBar: ProgressBar
    private lateinit var recycleView: RecyclerView
    private lateinit var searchApp: EditText
    private lateinit var tv_noRecord: TextView

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    private lateinit var mGridLayoutManager: GridLayoutManager

    lateinit var mAdapter: EmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, Injection.provideMainActivityViewModelFactory(this))
            .get(MainActivityViewModel::class.java)

        initView()
        initRecyclerView()

        if (Util.isNetworkAvailable(this)) {
            progressBar.visibility = View.VISIBLE
            fetchRemoteRecords()
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                fetchEmployees()
            }
        }

/*
        val api = ApiClient.api
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getResponse().execute().body()
                println("Akshay: $response")
            } catch (e: Exception) {
                println("Akshay Exception: $e")
            }
        }
*/

    }


    fun initView() {
        progressBar = findViewById(R.id.progressBar)
        recycleView = findViewById(R.id.recyclerView)
        searchApp = findViewById(R.id.searchApp)
        tv_noRecord = findViewById(R.id.tv_noRecord)

        searchApp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mAdapter.filter.filter(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {}
        })

    }

    private fun fetchRemoteRecords() {

        viewModel.getRemoteEmployees()?.observe(this, Observer<ApiResponse> {
            progressBar?.visibility = View.GONE
            setUpAdapter(it.employees)
        })
    }

    private suspend fun fetchEmployees() {

        viewModel.getAllEmployees()?.observe(this, Observer<List<Employees>> {
            setUpAdapter(it)
        })
    }

    private fun setUpAdapter(employees: List<Employees>) {
        if (employees.size > 0) {
            mAdapter.employees = employees
            recycleView.visibility = View.VISIBLE
            searchApp.visibility = View.VISIBLE
            tv_noRecord.visibility = View.GONE
        } else {
            recycleView.visibility = View.GONE
            searchApp.visibility = View.GONE
            tv_noRecord.visibility = View.VISIBLE
            tv_noRecord.setText(resources.getString(R.string.noRecords))
        }
    }


    private fun initRecyclerView() {
        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = EmployeesAdapter(this@MainActivity)
        recycleView.adapter = mAdapter
    }

    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(
            this,
            if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE
        )
        recycleView.setLayoutManager(mGridLayoutManager)
    }

    override fun onItemClicked(employees: Employees) {
        toast("Name: ${employees.name}\nDept: ${employees.dept_name}")
    }


    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}