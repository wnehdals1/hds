package com.hiconsy.app.hds.kit

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.hiconsy.app.hds.R
import com.hiconsy.app.hds.databinding.ToastCommonBinding
import com.hiconsy.app.hds.databinding.ToastCommonExpandedBinding
import com.hiconsy.app.hds.databinding.ToastCommonTwoBinding
import com.hiconsy.app.hds.databinding.ToastCommonTwoExpandedBinding

object CommonToast {
    fun createToast(
        context: Context,
        message: String,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes background: Int = R.drawable.bg_r100_f_green400,
        yOffset: Int = 0
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonBinding = DataBindingUtil.inflate(inflater, R.layout.toast_common, null, false)

        binding.tvToastMsg.text = message
        binding.ivToastMsg.visibility = View.GONE
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, yOffset)
            duration = length
            view = binding.root
        }
    }
    fun createExpandedToast(
        context: Context,
        message: String,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes background: Int = R.drawable.bg_r8_f_green400,
        yOffset: Int = 0
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonExpandedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.toast_common_expanded,
            null,
            false
        )

        binding.tvToastMsg.text = message
        binding.ivToastMsg.visibility = View.GONE
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, yOffset)
            duration = length
            view = binding.root
        }
    }
    fun createIconToast(
        context: Context,
        message: String,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes icon: Int = R.drawable.ic_check_white,
        @DrawableRes background: Int = R.drawable.bg_r100_f_green400,
        yOffset: Int = 0
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonBinding = DataBindingUtil.inflate(inflater, R.layout.toast_common, null, false)

        binding.tvToastMsg.text = message
        binding.ivToastMsg.visibility = View.VISIBLE
        binding.ivToastMsg.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, yOffset)
            duration = length
            view = binding.root
        }
    }
    fun createExpandedIconToast(
        context: Context,
        message: String,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes icon: Int = R.drawable.ic_check_white,
        @DrawableRes background: Int = R.drawable.bg_r8_f_green400,
        yOffset: Int = 0
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonExpandedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.toast_common_expanded,
            null,
            false
        )

        binding.tvToastMsg.text = message
        binding.ivToastMsg.visibility = View.VISIBLE
        binding.ivToastMsg.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, yOffset)
            duration = length
            view = binding.root
        }
    }

    fun createTwoToast(
        context: Context,
        message: List<String>,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes background: Int = R.drawable.bg_r100_f_green400
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonTwoBinding = DataBindingUtil.inflate(inflater, R.layout.toast_common_two, null, false)

        if (message.size != 2) {
            return createToast(context, "")
        }

        binding.tvToastMsg.text = message[0]
        binding.tvToastMsg2.text = message[1]
        binding.ivToastMsg.visibility = View.GONE
        binding.ivToastMsg2.visibility = View.GONE
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)
        binding.llToastMsg2.background = ContextCompat.getDrawable(context, background)
        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = length
            view = binding.root
        }
    }
    fun createTwoIconToast(
        context: Context,
        message: List<String>,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes icon: Int = R.drawable.ic_check_white,
        @DrawableRes background: Int = R.drawable.bg_r100_f_green400
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonTwoBinding = DataBindingUtil.inflate(inflater, R.layout.toast_common_two, null, false)

        if (message.size != 2) {
            return createIconToast(context, "")
        }

        binding.tvToastMsg.text = message[0]
        binding.tvToastMsg2.text = message[1]
        binding.ivToastMsg.visibility = View.VISIBLE
        binding.ivToastMsg2.visibility = View.VISIBLE
        binding.ivToastMsg.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.ivToastMsg2.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)
        binding.llToastMsg2.background = ContextCompat.getDrawable(context, background)
        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = length
            view = binding.root
        }
    }
    fun createExpandedTwoToast(
        context: Context,
        message: List<String>,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes background: Int = R.drawable.bg_r8_f_green400
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonTwoExpandedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.toast_common_two_expanded,
            null,
            false
        )

        if (message.size != 2) {
            return createExpandedToast(context, "")
        }

        binding.tvToastMsg.text = message[0]
        binding.tvToastMsg2.text = message[1]
        binding.ivToastMsg.visibility = View.GONE
        binding.ivToastMsg2.visibility = View.GONE
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)
        binding.llToastMsg2.background = ContextCompat.getDrawable(context, background)
        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = length
            view = binding.root
        }
    }
    fun createExpandedTwoIconToast(
        context: Context,
        message: List<String>,
        length: Int = Toast.LENGTH_SHORT,
        @DrawableRes icon: Int = R.drawable.ic_check_white,
        @DrawableRes background: Int = R.drawable.bg_r8_f_green400
    ): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCommonTwoExpandedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.toast_common_two_expanded,
            null,
            false
        )

        if (message.size != 2) {
            return createIconToast(context, "")
        }

        binding.tvToastMsg.text = message[0]
        binding.tvToastMsg2.text = message[1]
        binding.ivToastMsg.visibility = View.VISIBLE
        binding.ivToastMsg2.visibility = View.VISIBLE
        binding.ivToastMsg.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.ivToastMsg2.setImageDrawable(ContextCompat.getDrawable(context, icon))
        binding.llToastMsg.background = ContextCompat.getDrawable(context, background)
        binding.llToastMsg2.background = ContextCompat.getDrawable(context, background)
        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = length
            view = binding.root
        }
    }
}
