package com.garosero.android.hobbyroadmap.main.til

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.*

@RequiresApi(Build.VERSION_CODES.O)
class TilItemFragment(
    val mode : TilWriteMode,
    val tilItem : TilItem
    ) : Fragment() {

    var binding : FragmentTilItemBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilItemBinding.inflate(layoutInflater)
        val view = binding?.root
        initView()

        return view
    }

    private fun initView(){
        // init view
        with(binding!!){
            tvDate.text = tilItem.date
            tvTitle.text = tilItem.moduleName
            tvSubtitle.text = tilItem.moduleDesc
            etContent.setText(tilItem.content)
        }

        // onclick
        with(binding!!){
            btnCancel.setOnClickListener {
                when (mode){
                    TilWriteMode.CREATE -> requireActivity().finish()
                    TilWriteMode.UPDATE -> moveToList()
                }
            }

            btnSave.setOnClickListener {
                when (mode){
                    TilWriteMode.CREATE -> onCreateTil()
                    TilWriteMode.UPDATE -> onUpdateTil()
                }
            }
        }
    }

    //---------------------------CURD TIL----------------------------
    private fun onCreateTil(){
        NetworkFactory.request(CreateTilRequest(tilResponse = tilItem), object : RequestListener() {
            override fun onRequestSuccess() {

                val xp_sub = AppApplication.tilData.value?.values?.size ?: 0
                val xp = AppApplication.userData.value?.xp ?: (xp_sub * TilParentFragment.model.tilXp)

                NetworkFactory.request(UpdateUserXpRequest(xp + TilParentFragment.model.tilXp), object : RequestListener(){

                    override fun onRequestSuccess() {
                        makeToast("til이 반영 되었습니다.")
                        moveToList()
                    }
                })
            }
        })
    }

    private fun onDeleteTil(){
        NetworkFactory.request(DeleteTilRequest(tilItem.tilId), object : RequestListener() {
            override fun onRequestSuccess() {
                makeToast("til이 삭제 되었습니다.")
                moveToList()
            }
        })
    }

    private fun onUpdateTil(){
        // get content
        val content = binding?.etContent?.text?.toString() ?: ""

        // request
        NetworkFactory.request(
            UpdateTilRequest(content = content, tilId = tilItem.tilId),
            object : RequestListener() {
            override fun onRequestSuccess() {
                makeToast("til이 수정 되었습니다.")
                moveToList()
            }
        })
    }

    private fun makeToast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToList(){
        val parent : TilParentFragment  = parentFragment as TilParentFragment
        parent.changeFragment(TilListFragment())
    }

    enum class TilWriteMode{
        CREATE, UPDATE
    }
}