package org.igye.svg

import org.igye.graph2d.Boundaries2D

data class SvgElems(val boundaries: Boundaries2D, val elems: List<SvgElem>) {
    fun merge(vararg other:SvgElems): SvgElems {
        val bs: MutableList<Boundaries2D> = ArrayList()
        bs.add(this.boundaries)
        val es: MutableList<SvgElem> = ArrayList()
        es.addAll(elems)
        for (o in other) {
            bs.add(o.boundaries)
            es.addAll(o.elems)
        }
        return SvgElems(
            boundaries = Boundaries2D.from(bs),
            elems = es
        )
    }
}