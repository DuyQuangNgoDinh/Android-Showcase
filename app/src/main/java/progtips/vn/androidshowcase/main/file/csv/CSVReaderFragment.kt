package progtips.vn.androidshowcase.main.file.csv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.main.theme.AndroidShowcaseTheme
import javax.inject.Inject

@AndroidEntryPoint
class CSVReaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidShowcaseTheme {

                }
            }
        }
    }
}