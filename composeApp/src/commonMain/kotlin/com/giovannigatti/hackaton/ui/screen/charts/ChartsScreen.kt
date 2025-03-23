package com.giovannigatti.hackaton.ui.screen.charts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.extractDayFromDate
import com.giovannigatti.hackaton.extractShortMonthFromDate
import com.giovannigatti.hackaton.extractYearFromDate
import com.giovannigatti.hackaton.groupByCategory
import com.giovannigatti.hackaton.groupByDate
import com.giovannigatti.hackaton.groupByMonth
import com.giovannigatti.hackaton.groupByRecipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.ui.composable.ClickableOutlinedTextField
import com.giovannigatti.hackaton.ui.composable.TransactionRow
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsGroupType
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsViewModel
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.columnSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.common.Legend
import com.patrykandpatrick.vico.multiplatform.common.LegendItem
import com.patrykandpatrick.vico.multiplatform.common.component.Component
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.TextComponent
import com.patrykandpatrick.vico.multiplatform.common.rememberHorizontalLegend
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChartsScreen(
    viewModel: ChartsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    var groupType by remember { mutableStateOf(TransactionsGroupType.DAY) }
    Box() {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

//            val listByCategory by remember(uiState.transactions) { mutableStateOf(uiState.transactions.groupByCategory()) }
//            val listByRecipient by remember(uiState.transactions) { mutableStateOf(uiState.transactions.groupByRecipient()) }
//            val listByDate by remember(uiState.transactions) { mutableStateOf(uiState.transactions.groupByDate()) }

            if (uiState.transactions.isNotEmpty()) {
                Column {
                    Text(
                        text = "Expenses by month:",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    val grouped = uiState.transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .groupByMonth()
                        .entries
                        .sortedWith(compareBy({ it.key.year }, { it.key.month }))
                    val monthLabels = grouped.map { entry ->
                        val month = entry.key.month
                        val year = entry.key.year
                        val monthName = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                            .getOrNull(month - 1) ?: "?"
                        "$monthName $year"
                    }
                    val bottomAxis = HorizontalAxis.rememberBottom(
                        valueFormatter = { _, value, _ ->
                            monthLabels.getOrNull(value.toInt()-1).takeIf { value.toInt()-1 < monthLabels.size } ?: "?"
                        }
                    )

                    val modelProducer = remember { CartesianChartModelProducer() }
                    LaunchedEffect(grouped) {
                        modelProducer.runTransaction {
                            columnSeries {
                                grouped.forEachIndexed { index, entry ->
                                    val y = entry.value.sumOf { it.amount }
                                    series(index, y)
                                }
                            }
                        }
                    }

                    CartesianChartHost(
                        rememberCartesianChart(
                            rememberColumnCartesianLayer(),
                            startAxis = VerticalAxis.rememberStart(),
                            bottomAxis = bottomAxis,
                        ),
                        modelProducer = modelProducer,
                        modifier = Modifier.height(500.dp),
                        )

                }
                Spacer(Modifier.width(10.dp))
                Column {
                    val modelProducer = remember { CartesianChartModelProducer() }

                    Text(
                        text = "Expenses by day:",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    val grouped = uiState.transactions
                        .filter { it.type == TransactionType.EXPENSE }
                        .groupBy { it.date }
                        .entries
                        .sortedBy { it.key }

                    val dayLabels = grouped.map { entry ->
                        val date = entry.key
                        "${date.dayOfMonth.toString().padStart(2, '0')} ${date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }}"
                    }

                    val bottomAxis = HorizontalAxis.rememberBottom(
                        valueFormatter = { _, value, _ ->
                            val index = value.toInt()
                            dayLabels.getOrNull(index)
                                ?.takeIf { it.isNotBlank() }
                                ?: "?"
                        }
                    )
                    LaunchedEffect(grouped) {
                        val values = grouped.map { it.value.sumOf { tx -> tx.amount } }

                        modelProducer.runTransaction {
                            lineSeries {
                                series(values)
                            }
                        }
                    }

                    CartesianChartHost(
                        chart = rememberCartesianChart(
                            rememberLineCartesianLayer(),
                            startAxis = VerticalAxis.rememberStart(),
                            bottomAxis = bottomAxis,
                        ),
                        modelProducer = modelProducer,
                        modifier = Modifier.height(500.dp),
                    )
                }
            }
        }
    }
}

