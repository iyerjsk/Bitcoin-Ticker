package com.example.bitcointicker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bitcointicker.R
import com.example.bitcointicker.Region
import com.example.bitcointicker.databinding.BlockchainFragmentViewBinding
import com.example.bitcointicker.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BlockchainFragment : Fragment() {

    companion object {
        fun newInstance(region: Region): BlockchainFragment {
            return BlockchainFragment().also {
                it.arguments = Bundle().apply {
                    putString(REGION, region.name)
                }
            }
        }

        private const val REGION = "Region"
        @SuppressLint("SimpleDateFormat")
        private val formatter = SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
    }

    private var _binding: BlockchainFragmentViewBinding? = null
    private val binding get() = _binding!!
    private val viewModels by activityViewModels<SharedViewModel>()
    private var region: Region? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        region = arguments?.getString(REGION)?.let {
            Region.valueOf(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BlockchainFragmentViewBinding.inflate(inflater).apply {
            viewModels.data.observe(viewLifecycleOwner, { data ->
                region?.run {
                    priceView.text = when(this) {
                        Region.CANADA -> {
                            String.format(Locale.US,
                                resources.getString(R.string.price_text_canada),
                                data.second.canadaPriceModel.last)
                        }

                        Region.EUROPE -> {
                            String.format(Locale.US,
                                resources.getString(R.string.price_text_europe),
                                data.second.europePriceModel.last)
                        }

                        Region.US -> {
                            String.format(Locale.US,
                                resources.getString(R.string.price_text_us),
                                data.second.usPriceModel.last)

                        }
                    }

                    timeView.text = formatter.format(Date(data.first))
                }
            })
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}