package com.skjline.caddie.model

import android.util.SparseArray

/**
 * Created on 9/9/17.
 */
data class Hole (val holeNo: Int, var par: Int) {
    val strokes: SparseArray<Stroke> = SparseArray()

    constructor(builder: Builder):
            this(holeNo = builder.holeNo, par = builder.par)


    companion object {
        inline fun build(hole: Int, block: Builder.() -> Unit) = Builder(hole).apply(block).build()
    }

    class Builder(val holeNo: Int) {
        var par: Int = 0
        fun build() = Hole(this)
    }
}