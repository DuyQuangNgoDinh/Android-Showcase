package progtips.vn.androidshowcase

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import progtips.vn.sharedresource.helper.showProgressDialog

abstract class BaseFragment<T: ViewBinding>(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateView(inflater, container)
        return binding.root
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        registerObserver()
    }

    protected abstract fun initView(view: View)

    protected open fun registerObserver() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun displayLoading(isShown: Boolean) {
        if (isShown) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun showLoading() {
        if (progressDialog == null) {
            progressDialog = requireContext().showProgressDialog()
        }

        progressDialog?.show()
    }

    private fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}