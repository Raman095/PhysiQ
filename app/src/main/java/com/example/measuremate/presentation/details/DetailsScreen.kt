package com.example.measuremate.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.measuremate.domain.model.BodyPart
import com.example.measuremate.domain.model.BodyPartValues
import com.example.measuremate.domain.model.MeasuringUnit
import com.example.measuremate.domain.model.TimeRange
import com.example.measuremate.presentation.component.LineGraph
import com.example.measuremate.presentation.component.MeasuringUnitBottomSheet
import com.example.measuremate.presentation.component.PhysiQDialog
import com.example.measuremate.presentation.theme.MeasureMateTheme
import com.example.measuremate.presentation.util.changeLocalDateToDateString
import com.example.measuremate.presentation.util.changeLocalDateToGraphDate
import com.example.measuremate.presentation.util.roundToDecimal
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.collections.List

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen() {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)  // opens the bottom sheet fully, not partially
    val scope = rememberCoroutineScope()
    var isMeasuringUnitBottomSheetOpen by remember { mutableStateOf(false) }

    MeasuringUnitBottomSheet(
        isOpen = isMeasuringUnitBottomSheetOpen,
        sheetState = sheetState,
        onBottomSheetDismiss = { isMeasuringUnitBottomSheetOpen = false },
        onItemClicked = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if(!sheetState.isVisible) {
                    isMeasuringUnitBottomSheetOpen = false
                }
            }
        }
    )

    var isDeleteItemDialogOpen by rememberSaveable { mutableStateOf(false) }

    PhysiQDialog (
        isOpen = isDeleteItemDialogOpen,
        title = "Delete Body Part?",
        confirmButtonText = "Delete",
        onDialogDismiss = { isDeleteItemDialogOpen = false },
        onConfirmButtonClick = { isDeleteItemDialogOpen = false },
        body = {
            Text( text = "Are you sure, you want to delete this Body Part? All related" +
            " data will be permanently removed.")
        }
    )

    var selectedTimeRange by remember { mutableStateOf(TimeRange.LAST7DAYS)}

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailsTopBar(
            bodyPart = BodyPart(name = "Shoulder", isActive = true, measuringUnit = MeasuringUnit.CM.code),
            onDeleteIconClick = { isDeleteItemDialogOpen = true },
            onBackIconClick = {},
            onUnitIconClick = { isMeasuringUnitBottomSheetOpen = true }
        )
        ChartTimeRangeButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            selectedTimeRange = selectedTimeRange,
            onClick = { selectedTimeRange = it}
        )

        LineGraph(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio( ratio = 2 / 1f)
                .padding(16.dp),
            bodyPartValues = dummyBodyPartValues
        )

        HistorySection(
            bodyPartValues = dummyBodyPartValues,
            measuringUnitCode = "cm",
            onDeleteIconClick = {}
        )
    }
}

val dummyBodyPartValues = listOf(
    BodyPartValues(value = 72.0f, date = LocalDate.of(2023, 5, 10)),
    BodyPartValues(value = 76.854515466f, date = LocalDate.of(2023, 5, 1)),
    BodyPartValues(value = 74.0f, date = LocalDate.of(2023, 4, 20)),
    BodyPartValues(value = 75.1f, date = LocalDate.of(2023, 4, 5)),
    BodyPartValues(value = 66.3f, date = LocalDate.of(2023, 3, 15)),
    BodyPartValues(value = 67.2f, date = LocalDate.of(2023, 3, 10)),
    BodyPartValues(value = 73.5f, date = LocalDate.of(2023, 3, 1)),
    BodyPartValues(value = 69.8545615f, date = LocalDate.of(2023, 2, 18)),
    BodyPartValues(value = 68.4f, date = LocalDate.of(2023, 2, 1)),
    BodyPartValues(value = 72.0f, date = LocalDate.of(2023, 1, 22)),
    BodyPartValues(value = 70.5f, date = LocalDate.of(2023, 1, 14))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsTopBar(
    modifier: Modifier = Modifier,
    bodyPart: BodyPart?,
    onDeleteIconClick: () -> Unit,
    onBackIconClick: () -> Unit,
    onUnitIconClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = bodyPart?.name ?: "",
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton( onClick = { onBackIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },

        actions = {        // Used to place the element on the right side of Top Bar

            // When user taps the profile icon -> calls profilePictureOnClick()
            // That lambda changes (look up in the code) isProfileBottomSheetOpen = true -> opens bottom sheet
            IconButton( onClick = { onDeleteIconClick() }) {
                Icon( imageVector = Icons.Rounded.Delete, contentDescription = "Delete item")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = bodyPart?.measuringUnit ?: "")
            IconButton( onClick = { onUnitIconClick() }) {
                Icon( imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "Select Unit")
            }
        }
    )
}

@Composable
private fun ChartTimeRangeButtons(
    modifier: Modifier = Modifier,
    selectedTimeRange: TimeRange,
    onClick: (TimeRange) -> Unit
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        TimeRange.entries.forEach { timeRange ->
            TimeRangeSelectionButton(
                modifier = Modifier.weight(1f),
                label = timeRange.label,
                labelTextStyle = if(timeRange == selectedTimeRange) {
                    MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                } else {
                    MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                },
                backgroundColor = if(timeRange == selectedTimeRange) {
                    MaterialTheme.colorScheme.surface
                } else {
                    Color.Transparent
                },
                onClick = { onClick(timeRange) }
            )
        }
    }
}

@Composable
private fun TimeRangeSelectionButton(
    modifier: Modifier = Modifier,
    label: String,
    labelTextStyle: TextStyle,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable{ onClick() }
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, style = labelTextStyle, maxLines = 1)
    }
}

@Composable
private fun HistorySection(
    modifier: Modifier = Modifier,
    bodyPartValues: List<BodyPartValues>,
    measuringUnitCode: String?,
    onDeleteIconClick: (BodyPartValues) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        val grouped = bodyPartValues.groupBy { it.date.month }

        item {
            Text(
                text = "History",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        grouped.forEach { (month, bodyPartValues) ->
            stickyHeader {
                Text(
                    text = month.name,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            items(bodyPartValues) { bodyPartValue ->
                HistoryCard(
                    modifier = Modifier.padding(bottom = 10.dp),
                    bodyPartValue = bodyPartValue,
                    measuringUnitCode = measuringUnitCode,
                    onDeleteIconClick = {onDeleteIconClick(bodyPartValue)}
                )
            }
        }
    }
}

@Composable
private fun HistoryCard(
    modifier: Modifier = Modifier,
    bodyPartValue: BodyPartValues,
    measuringUnitCode: String?,
    onDeleteIconClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = null
            )
            Text(
                text = bodyPartValue.date.changeLocalDateToDateString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${bodyPartValue.value.roundToDecimal(3)} ${measuringUnitCode ?: ""}",
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = { onDeleteIconClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    MeasureMateTheme {
        DetailsScreen()
    }
}