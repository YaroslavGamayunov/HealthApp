package com.yaroslavgamayunov.healthapp.presentation.activity.weight

import android.graphics.Typeface
import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.chart.zoom.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.lineSeries
import com.patrykandpatrick.vico.core.scroll.Scroll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rememberMarker
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Composable
fun WeightChart(
    modifier: Modifier = Modifier,
    goal: Float,
    data: Map<Instant, Float>,
) {
    val xToDateMapKey = ExtraStore.Key<Map<Float, Instant>>()

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(data) {
        withContext(Dispatchers.Default) {
            val xToDates = data.keys.associateBy { it.epochSecond.toFloat() }
            modelProducer.tryRunTransaction {
                lineSeries { series(data.values) }
                updateExtras { it[xToDateMapKey] = xToDates }
            }
        }
    }

    CartesianChartHost(
        zoomState = rememberVicoZoomState(),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                listOf(
                    rememberLineSpec(
                        DynamicShaders.color(
                            Color(
                                0xffa485e0
                            )
                        )
                    )
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(
                guideline = null,
                itemPlacer = remember {
                    AxisItemPlacer.Horizontal.default(
                        spacing = 1,
                        addExtremeLabelPadding = true
                    )
                },
                valueFormatter = { x, chartValues, _ ->
                    val date = data.keys.toList()[x.toInt()]
                    DateFormat.format("d MMM", Date.from(date))
                }
            ),
            decorations = listOf(rememberGoalLine(goal))
        ),
        marker = rememberMarker(),
        modelProducer = modelProducer,
        modifier = modifier,
        scrollState = rememberVicoScrollState(
            initialScroll = Scroll.Absolute.End,
        )
    )
}


@Preview
@Composable
fun PreviewWeightChart() {
    val weights =
        mapOf(
            Instant.now() to 2f,
            Instant.now().minus(5, ChronoUnit.DAYS) to 6f,
            Instant.now().minus(20, ChronoUnit.DAYS) to 4f,
            Instant.now() to 2f
        )

    WeightChart(data = weights, goal = 5f)
}

@Composable
private fun rememberGoalLine(goal: Float): HorizontalLine {
    val color = Color.Yellow
    return rememberHorizontalLine(
        y = { goal },
        line = rememberLineComponent(color, 2.dp),
        labelComponent =
        rememberTextComponent(
            background = rememberShapeComponent(Shapes.pillShape, color),
            typeface = Typeface.MONOSPACE,
        ),
    )
}

