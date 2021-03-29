package br.com.ufop.studentaid.features.ui.modal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import br.com.ufop.studentaid.R
import kotlinx.android.synthetic.main.modal_dialog_new.*


class ModalFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_dialog_new, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWindow()
        setClickListener()

    }

    private fun setClickListener() {
        cancel_new_service_btn?.setOnClickListener {
            dismissDialog()
        }
        ok_new_service_btn?.setOnClickListener {
            val service = edit_text_new_service?.text.toString()
            callbackOk(service)
            dismissDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let {
            return object : Dialog(it, theme) {
                override fun onBackPressed() {
//                    mBody?.callbackNo?.invoke()
//                    dismissDialog()
                }
            }
        } ?: return super.onCreateDialog(savedInstanceState)
    }




    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun dismissDialog() {
        activity?.applicationContext?.let { hideKeyboard(it, view) }
        dismissAllowingStateLoss()
    }

    private fun setUpWindow() {
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    companion object {


        private lateinit var callbackOk: (service: String) -> Unit

        fun newInstance(
            callbackOk : (String) -> Unit
        ): ModalFragment {
            val frag =
                    ModalFragment()
            val args = Bundle()
            frag.arguments = args
            this.callbackOk = callbackOk
            return frag
        }
    }

    fun hideKeyboard(context: Context, view: View?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

//    fun setMetricsDialog(){
//        val metrics = activity?.let { getDisplayMetrics(it) }
//        if (metrics != null) {
//            dialog?.window?.setLayout(metrics[1] - 65,
//                ViewGroup.LayoutParams.WRAP_CONTENT)
//        }
//    }
}
