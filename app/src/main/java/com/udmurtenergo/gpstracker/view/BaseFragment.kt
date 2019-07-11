package com.udmurtenergo.gpstracker.view

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    /**
     * Позволяет фрагменту обработать кнопку back
     * @return true - действие обработано и не требуется дальнейшая обработка
     * false - действие не обработано, обработка будет выполнена по умолчанию активностью
     */
    fun onBackPressed(): Boolean = false
}
