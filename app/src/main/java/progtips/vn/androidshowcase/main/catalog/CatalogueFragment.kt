package progtips.vn.androidshowcase.main.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.main.catalog.ui.CatalogueUI
import progtips.vn.androidshowcase.main.theme.AndroidShowcaseTheme

@ExperimentalFoundationApi
class CatalogueFragment : Fragment() {
    private val catalogue = listOf(
        Catalogue(
            R.id.navigateProfileFragment,
            "Authenticate",
            0xff9bf0e1
        ),
        Catalogue(
            R.id.navigateScanFragment,
            "OCR Library",
            0xff9bf0e1
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidShowcaseTheme {
                    CatalogueUI(catalogue = catalogue, onClick = {
                        findNavController().navigate(it.id)
                    })
                }
            }
        }
    }
}
