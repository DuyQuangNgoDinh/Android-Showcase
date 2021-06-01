package progtips.vn.androidshowcase.main.file.internal

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tradingview.lightweightcharts.api.interfaces.SeriesApi
import com.tradingview.lightweightcharts.api.options.models.CandlestickSeriesOptions
import com.tradingview.lightweightcharts.api.options.models.HistogramSeriesOptions
import com.tradingview.lightweightcharts.api.options.models.layoutOptions
import com.tradingview.lightweightcharts.api.options.models.localizationOptions
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.tradingview.lightweightcharts.api.series.models.HistogramData
import com.tradingview.lightweightcharts.api.series.models.Time
import com.tradingview.lightweightcharts.runtime.plugins.DateTimeFormat
import com.tradingview.lightweightcharts.runtime.plugins.PriceFormatter
import com.tradingview.lightweightcharts.runtime.plugins.TimeFormatter
import com.tradingview.lightweightcharts.view.ChartsView
import progtips.vn.androidshowcase.R
import progtips.vn.asia.filereader.explorer.InternalFileExplorer
import progtips.vn.asia.filereader.reader.CSVFileReader
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData

class InternalReaderFragment: Fragment(R.layout.fm_tmp) {
    private val fileReader = InternalFileExplorer()
    private lateinit var chartsView: ChartsView
    lateinit var volumeSeries: SeriesApi
    lateinit var priceSeries: SeriesApi

    private val viewModel: InternalReaderViewModel by viewModels()

    private val stockDataList = mutableListOf<StockData>()
    private var count = 0
    private val onReadLine : (Array<String>) -> Boolean = {
        if (count > 0) {
            stockDataList.add(
                StockData(
                    symbol = it[0],
                    date = it[1],
                    open = it[2].toFloat(),
                    high = it[3].toFloat(),
                    low =  it[4].toFloat(),
                    close =  it[5].toFloat(),
                    volume = it[6].toFloat()
                )
            )
        }

        val stop = count++ > 10

        if (stop) {
            priceSeries.setData(stockDataList.map { data ->
                val year = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 1)
                val month = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 2)
                val day = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 3)
                BarData(Time.BusinessDay(year!!.toInt(), month!!.toInt(), day!!.toInt()), data.open, data.high, data.low, data.close)
            })

            volumeSeries.setData(stockDataList.map { data ->
                val year = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 1)
                val month = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 2)
                val day = parseData(data.date, Regex("(\\d{4})(\\d{2})(\\d{2})"), 3)
                HistogramData(Time.BusinessDay(year!!.toInt(), month!!.toInt(), day!!.toInt()), data.volume)
            })
        }

        stop
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartsView = view.findViewById(R.id.charts_view)

        initView()
    }

    private fun initView() {
        chartsView.api.applyOptions {
            layout = layoutOptions {
                backgroundColor = Color.LTGRAY
                textColor = Color.BLACK
            }
            localization = localizationOptions {
                locale = "ru-RU"
                priceFormatter = PriceFormatter(template = "{price:#2:#3}$")
                timeFormatter = TimeFormatter(
                    locale = "ru-RU",
                    dateTimeFormat = DateTimeFormat.DATE_TIME
                )
            }
        }

        chartsView.api.addHistogramSeries(
            options = HistogramSeriesOptions(title = "Gia"),
            onSeriesCreated = { series ->
                volumeSeries = series
                viewModel.addVolumeChannel.offer(true)
            }
        )

        chartsView.api.addCandlestickSeries(
            options = CandlestickSeriesOptions(title = "Khoi luong"),
            onSeriesCreated = { series ->
                priceSeries = series
                viewModel.addPriceChannel.offer(true)
            }
        )

        viewModel.readFile.observe(viewLifecycleOwner) {
            if (it) {
                fileReader.readFile(requireContext(), "hsx_internal.csv", CSVFileReader(onReadLine))
            }
        }
    }
}

data class StockData(
    val symbol: String,
    val date: String,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Float
)