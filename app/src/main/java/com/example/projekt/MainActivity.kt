import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.example.projekt.R
import com.example.projekt.CPUFragment
import com.example.projekt.RAMFragment
import com.example.projekt.DiskSpaceFragment
import com.example.projekt.SystemInfoFragment
import res.layout.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = ViewPagerAdapter(this)

        // Assuming you have defined tabLayout and viewPager in your layout XML
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "System Info"
                1 -> tab.text = "CPU Usage"
                2 -> tab.text = "RAM Usage"
                3 -> tab.text = "Disk Space"
            }
        }.attach()
    }

    private inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SystemInfoFragment()
                1 -> CPUFragment()
                2 -> RAMFragment()
                3 -> DiskSpaceFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }

    }
}
