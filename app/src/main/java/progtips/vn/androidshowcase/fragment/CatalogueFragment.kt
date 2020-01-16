package progtips.vn.androidshowcase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.ui.adapter.FunctionAdapter

/**
 * A simple [Fragment] subclass.
 */
class CatalogueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewManager = GridLayoutManager(context, 2)
        val viewAdapter = FunctionAdapter(resources.getStringArray(R.array.components))

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcv_func).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

}
