package binar.academy.kelompok6.tripie_buyer.view.onboarding.fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentFirstOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.databinding.FragmentOnboardingBinding
import binar.academy.kelompok6.tripie_buyer.view.onboarding.adapter.OnboardingAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pt.tornelas.segmentedprogressbar.SegmentedProgressBar
import pt.tornelas.segmentedprogressbar.SegmentedProgressBarListener

private const val ARG_PARAM1 = "param1"

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private var position: Int? = null

    //used to create argument for Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = OnboardingAdapter(this)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.btnNextFirst.setOnClickListener {
                    binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1)
                }

                if (binding.viewPager.currentItem == 2) {
                    binding.btnNextFirst.text = "Get Started"
                    binding.btnNextFirst.setOnClickListener{
                        Navigation.findNavController(view).navigate(R.id.action_onboardingFragment2_to_homeFragment2)
                    }
                }
                else binding.btnNextFirst.text = "Next"
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    /* override fun onCreate(savedInstanceState: Bundle?) {
               super.onCreate(savedInstanceState)

               val items = List(3){ index -> index }

                binding.viewPager.adapter = PagerAdapter(supportFragmentManager, items)
                spb.viewPager = viewPager

                spb.segmentCount = items.size
                spb.listener = object : SegmentedProgressBarListener {
                    override fun onPage(oldPageIndex: Int, newPageIndex: Int) {
                        // New page started animating
                    }

                    override fun onFinished() {
                        finish()
                    }
                }

                val spb = findViewById<SegmentedProgressBar>(R.id.spb)
                spb.start()

                btnNext.setOnClickListener { spb.next() }
                btnPrevious.setOnClickListener { spb.previous() }
            }

             */

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}