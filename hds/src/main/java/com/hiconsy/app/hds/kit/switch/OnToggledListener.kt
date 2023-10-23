package com.hiconsy.app.hds.kit.switch

interface OnToggledListener {
    fun onSwitched(toggleableView: LabeledSwitch?, isOn: Boolean)
}
