package jodatimeutils.spikes.com.demo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import org.joda.time.DateTime
import android.R.attr.name
import android.widget.TextView
import android.view.LayoutInflater
import org.joda.time.format.DateTimeFormat


/**
 * Created by Jameido on 08/10/2017.
 */
class DateFormatsAdapter(context: Context?, objects: Array<out String>?) :
        ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val reView : TextView = super.getView(position, convertView, parent) as TextView
        reView.text = DateTimeFormat.forPattern(getItem(position)).print(System.currentTimeMillis())
        return reView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val reView : TextView = super.getDropDownView(position, convertView, parent) as TextView
        reView.text = DateTimeFormat.forPattern(getItem(position)).print(System.currentTimeMillis())
        return reView
    }
}