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
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.*
import com.garosero.android.hobbyroadmap.network.response.MyClassResponse

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
                finish()
            }

            btnSave.setOnClickListener {
                when (mode){
                    TilWriteMode.CREATE -> onCreateTil()
                    TilWriteMode.UPDATE -> onUpdateTil()
                }
            }

            btnDelete.setOnClickListener {
                onDeleteTil()
                finish()
            }
        }

        // set visible
        with(binding!!){
            when (mode){
                TilWriteMode.UPDATE -> {
                    btnCancel.visibility = View.VISIBLE
                    btnSave.visibility = View.VISIBLE
                    btnDelete.visibility = View.VISIBLE
                }

                TilWriteMode.CREATE -> {
                    btnCancel.visibility = View.VISIBLE
                    btnSave.visibility = View.VISIBLE
                    btnDelete.visibility = View.GONE
                }
            }
        }
    }

    //========================================== CURD TIL ==========================================
    private fun onCreateTil(){
        val tilResponse = CastHelper.tilitemToTilresponse(tilItem)
        tilResponse.content = binding?.etContent?.text.toString()

        // create til
        NetworkFactory.request(CreateTilRequest(tilResponse))

        // update xp
        val xp = AppApplication.userData.value?.xp
        if (xp != null) {
            NetworkFactory.request(UpdateUserXpRequest(
                xp + TilParentFragment.model.tilXp
            ))
        } // end if

        // create modules
        val myClassResponse = MyClassResponse()
        with(myClassResponse) {
            lastAccess = DateHelper.getTodayString()
            modules
        }

        NetworkFactory.request(CreateModulesRequest(
            moduleName = tilItem.moduleName,
            moduleNum = tilItem.moduleNum,
            classPath = tilItem.modulePath
        ))

        // update last access
        val lastAccess = AppApplication.userData.value?.myClass?.get(tilItem.modulePath)?.lastAccess
        val today = DateHelper.getTodayString()
        if (lastAccess != null && lastAccess != today) {
            NetworkFactory.request(
                UpdateLastAccessRequest(
                    classPath = tilItem.modulePath,
                    dateString = today
                )
            )
        } // end if

        // 현재는 데이터가 반영되면 화면을 종료하는 것이 아니라, 반영을 요청한 뒤 화면을 종료하고 있음.
        makeToast("til이 반영 되었습니다.")
        finish()

    }

    private fun onDeleteTil(){

        // update xp
        val xp = AppApplication.userData.value?.xp
        if (xp != null) {
            NetworkFactory.request(UpdateUserXpRequest(
                xp - TilParentFragment.model.tilXp
            ))
        } // end if

        NetworkFactory.request(DeleteModuleRequest(classPath = tilItem.modulePath, moduleNum = tilItem.moduleNum))
        NetworkFactory.request(DeleteTilRequest(tilItem.tilId))

        // 현재는 데이터가 반영되면 화면을 종료하는 것이 아니라, 반영을 요청한 뒤 화면을 종료하고 있음.
        makeToast("til이 삭제 되었습니다.")
        finish()
    }

    private fun onUpdateTil(){
        // get content
        val content = binding?.etContent?.text?.toString() ?: ""

        if (content == tilItem.content){
            return
        } // end if

        // request
        NetworkFactory.request(
            UpdateTilRequest(content = content, tilId = tilItem.tilId),
            object : RequestListener() {
            override fun onRequestSuccess() {
                makeToast("til이 수정 되었습니다.")
                finish()
            }
        })
    }

    // =============================================================================================
    private fun makeToast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    enum class TilWriteMode(s: String) {
        CREATE("CREATE"), UPDATE("UPDATE");
    }

    private fun finish(){
        (requireActivity() as? TilWriteActivity)?.finish()
        (parentFragment as? TilParentFragment)?.changeFragment(TilListFragment())
    }
}