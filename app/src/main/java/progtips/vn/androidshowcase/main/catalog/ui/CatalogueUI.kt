package progtips.vn.androidshowcase.main.catalog.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import progtips.vn.androidshowcase.main.catalog.Catalogue

@ExperimentalFoundationApi
@Composable
fun CatalogueUI(
    catalogue: List<Catalogue>,
    onClick: (Catalogue) -> Unit
) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(catalogue) { item ->
            CatalogueRow(catalogue = item, onClick = onClick)
        }
    }
}

@Composable
private fun CatalogueRow(
    catalogue: Catalogue, onClick: (Catalogue) -> Unit
) {
    Card(
        backgroundColor = Color(catalogue.color),
        modifier = Modifier
            .clickable(onClick = { onClick(catalogue) })
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center,
            text = catalogue.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun PreviewCatalogueRow() {
    CatalogueRow(catalogue = Catalogue(1, "Mock Catalogue", 0xFFE9A96F), onClick = {})
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewCatalogueUI() {
    CatalogueUI(
        catalogue = listOf(
            Catalogue(1, "Mock Catalogue", 0xFFE9A96F),
            Catalogue(1, "Mock Catalogue", 0xFFE9A9FF),
            Catalogue(1, "Mock Catalogue", 0xFFE9A91F),
            Catalogue(1, "Mock Catalogue", 0xFFE9A921)
        ), onClick = {})
}