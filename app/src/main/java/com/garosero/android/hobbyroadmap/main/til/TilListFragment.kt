package com.garosero.android.hobbyroadmap.main.til

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilListBinding
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilListFragment : Fragment() {
    private val model = TilParentFragment.model
    private var binding : FragmentTilListBinding? = null
    private val adapter = TilListAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTilListBinding.inflate(layoutInflater)

        initRecyclerview()
        registerObserver()

        return binding!!.root
    }

    private fun initRecyclerview(){
        binding?.recyclerList?.adapter = adapter
        adapter.setOnItemClickListener(object : TilListAdapter.OnItemClickListener{
            override fun onItemClick(item: TilItem) {
                val parent : TilParentFragment  = parentFragment as TilParentFragment
                parent.changeFragment(TilItemFragment(TilItemFragment.TilWriteMode.UPDATE, item))
            }
        })
    }

    private fun registerObserver(){
        val observer = Observer<LocalDate>{
            if (adapter == null){
                return@Observer
            } // end if

            adapter.dataSet = model.getDailyData()
            adapter.notifyDataSetChanged()

            initEmptyView()
        }
        model.focusDate.observe(viewLifecycleOwner, observer)

        val tilObserver = Observer<MutableMap<String, MutableList<TilItem>>> {
            adapter.dataSet = model.getDailyData()
            adapter.notifyDataSetChanged()

            initEmptyView()
        }
        model.tilMap.observe(viewLifecycleOwner, tilObserver)
    }

    private fun initEmptyView(){
        if (adapter.itemCount == 0){
            binding!!.ivTilEmptySet.visibility = View.VISIBLE
            binding!!.recyclerList.visibility = View.GONE
        } else {
            binding!!.ivTilEmptySet.visibility = View.GONE
            binding!!.recyclerList.visibility = View.VISIBLE
        } // end if
    }
}