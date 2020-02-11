package progtips.vn.fbstyle


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_comment.*
import progtips.vn.fbstyle.ui.adapter.CommentAdapter
import progtips.vn.fbstyle.ui.helper.CommentList

/**
 * A simple [Fragment] subclass.
 */
class CommentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewManager = LinearLayoutManager(context)
        val commentList = CommentList.initCommentList()
        val viewAdapter = CommentAdapter(commentList)

        rcv_comments

        view.findViewById<RecyclerView>(R.id.rcv_comments).apply {
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
