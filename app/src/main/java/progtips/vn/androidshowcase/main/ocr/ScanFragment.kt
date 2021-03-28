package progtips.vn.androidshowcase.main.ocr

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.BaseFragment
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.databinding.FmScanBinding
import progtips.vn.androidshowcase.main.ocr.adapter.CardListAdapter
import progtips.vn.androidshowcase.main.ocr.adapter.HomeCarouselAdapter
import progtips.vn.androidshowcase.main.ocr.adapter.HomeCarouselItemVH
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CardTypeEntity
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.parser.ParserManager
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ScanData
import progtips.vn.sharedresource.helper.imagepicker.ImagePickerActivity
import progtips.vn.sharedresource.helper.imagepicker.PhotoSelectionDelegate
import java.io.IOException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ScanFragment: BaseFragment<FmScanBinding>(R.layout.fm_scan), AdapterView.OnItemSelectedListener {

    companion object {
        const val REQUEST_IMAGE = 100

        const val CARD_ASPECT_RATIO_X = 337
        const val CARD_ASPECT_RATIO_Y = 212

        const val CERT_ASPECT_RATIO_X = 703
        const val CERT_ASPECT_RATIO_Y = 248
    }

    private lateinit var listener: CardParserListener<CitizenData>
    private var currentParser: Parser<CitizenData>? = null
    private var currentPosition = 0
    private val dobDateFormat = SimpleDateFormat("dd-MM-yyyy")
    private var photoSelectionDelegate: PhotoSelectionDelegate? = null

    private val cardList by lazy { listOf(
        CardTypeEntity(getString(R.string.title_singapore_nric), CardType.IDENTITY_CARD),
        CardTypeEntity(getString(R.string.title_work_permit), CardType.WORK_PERMIT),
        CardTypeEntity(getString(R.string.title_passport), CardType.PASSPORT),
        CardTypeEntity(getString(R.string.title_driving_license), CardType.DRIVER_LICENSE),
        CardTypeEntity(getString(R.string.title_student_concession_card), CardType.STUDENT_PASS),
        CardTypeEntity(getString(R.string.title_long_term_pass), CardType.LONG_TERM_PASS),
        CardTypeEntity(getString(R.string.title_saf_11b), CardType.ARMED_FORCES),
        CardTypeEntity(getString(R.string.title_birth_certificate), CardType.BIRTH_CERTIFICATE),
        CardTypeEntity(getString(R.string.title_malaysia_ic), CardType.MALAYSIA_IC)
    )}

    private val onCarouselClickListener = object: HomeCarouselItemVH.EventListener {
        override fun onClickCarouselItem(position: Int) {
            currentPosition = position

            if ((binding.spCardType.selectedItem as? CardTypeEntity)?.type == CardType.BIRTH_CERTIFICATE) {
                photoSelectionDelegate!!.onSelectPhotoClick(CERT_ASPECT_RATIO_X, CERT_ASPECT_RATIO_Y)
            } else
                photoSelectionDelegate!!.onSelectPhotoClick(CARD_ASPECT_RATIO_X, CARD_ASPECT_RATIO_Y)
        }
    }

    private val carouselAdapter = HomeCarouselAdapter(onCarouselClickListener)

    private val onPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.includeImage.includeIndicator.indicator.animatePageSelected(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImagePickerActivity.clearCache(requireContext())

        photoSelectionDelegate = PhotoSelectionDelegate(requireActivity(), this, requestCode = REQUEST_IMAGE)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FmScanBinding {
        return FmScanBinding.inflate(inflater, container, false)
    }

    override fun initView(view: View) {
        binding.run {
            btnTakePicture.setOnClickListener {
                recognizeTextInImage(carouselAdapter.carouselDataList)
            }

            includeImage.carouselPager.adapter = carouselAdapter
            includeImage.carouselPager.registerOnPageChangeCallback(onPageChanged)
            includeImage.includeIndicator.indicator.createIndicators(carouselAdapter.itemCount, includeImage.carouselPager.currentItem)

            listener = object: CardParserListener<CitizenData> {
                override fun onSuccess(entity: CitizenData) {
                    tvName.setText(entity.name)
                    val dob = entity.dob?.let {
                        dobDateFormat.format(it)
                    }
                    tvDob.setText(dob)
                    tvNumber.setText(entity.idNo)
                    tvRace.setText(entity.nationality)
                    tvBirthCountry.setText(entity.birthCountry)
                    rgSex.clearCheck()
                    radMale.isChecked = entity.gender == "M"
                    radMale.isChecked = entity.gender == "F"
                }

                override fun onError(errorMessage: String?) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        updateCarouselImageList()

        populateDropdown()
    }

    private fun populateDropdown() {
        binding.run {
            spCardType.adapter = CardListAdapter(requireContext(), android.R.layout.simple_spinner_item, cardList).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spCardType.onItemSelectedListener = this@ScanFragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>("path")
            try {
                uri?.let {
                    carouselAdapter.updateImageOnCarousel(it, currentPosition)
                    if (carouselAdapter.itemCount == 1) {
                        Log.d("MainActivity", "CurrentPosition: $currentPosition")
                        recognizeTextInImage(carouselAdapter.carouselDataList)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateLayoutBaseOnCardType(type: CardType) {
        clearAllFields()

        updateCarouselImageList()

        binding.run {
            inputNumber.hint = when (type) {
                CardType.IDENTITY_CARD, CardType.WORK_PERMIT -> getString(R.string.hint_id_number)
                CardType.PASSPORT -> getString(R.string.hint_passport_number)
                CardType.BIRTH_CERTIFICATE -> getString(R.string.hint_birth_cert_number)
                else -> getString(R.string.hint_license_number)
            }

            inputRace.hint = when (type) {
                CardType.IDENTITY_CARD, CardType.ARMED_FORCES -> getString(R.string.hint_race)
                else -> getString(R.string.hint_nationality)
            }

            inputRace.visibility = when (type) {
                CardType.IDENTITY_CARD, CardType.WORK_PERMIT, CardType.LONG_TERM_PASS, CardType.ARMED_FORCES -> View.VISIBLE
                else -> View.GONE
            }

            if (type == CardType.DRIVER_LICENSE) {
                tvSex.visibility = View.GONE
                rgSex.visibility = View.GONE
            } else {
                tvSex.visibility = View.VISIBLE
                rgSex.visibility = View.VISIBLE
            }

            inputBirthCountry.visibility = when (type) {
                CardType.IDENTITY_CARD, CardType.ARMED_FORCES -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun updateCarouselImageList() {
        val requiredImageList = currentParser?.getRequiredImageList() ?: listOf()
        val scanData = mutableListOf<ScanData>()
        requiredImageList.forEach {
            scanData.add(ScanData(null, it))
        }
        carouselAdapter.updateCarouselDataList(scanData)

        binding.run {
            if (scanData.size > 1) {
                includeImage.includeIndicator.indicator.visibility = View.VISIBLE
                includeImage.includeIndicator.indicator.createIndicators(
                    carouselAdapter.itemCount,
                    includeImage.carouselPager.currentItem
                )
            } else
                includeImage.includeIndicator.indicator.visibility = View.INVISIBLE
        }
    }

    private fun clearAllFields() {
        binding.run {
            tvName.setText("")
            tvDob.setText("")
            tvNumber.setText("")
            tvRace.setText("")
            tvBirthCountry.setText("")
            rgSex.clearCheck()
        }
    }

    private fun recognizeTextInImage(data: List<ScanData>) = currentParser?.parse(data)

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (parent?.getItemAtPosition(position) as? CardTypeEntity)?.let {
            currentParser = ParserManager.getParser(CardType.IDENTITY_CARD, requireContext(), listener)
            updateLayoutBaseOnCardType(it.type)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}