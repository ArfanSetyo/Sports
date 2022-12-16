

package com.example.android.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.android.sports.databinding.FragmentSportsListBinding

/**
 * Subkelas [Fragmen] sederhana sebagai tujuan default dalam navigasi.
 */

class SportsListFragment : Fragment() {

    private val sportsViewModel: SportsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSportsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSportsListBinding.bind(view)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        // Hubungkan SlidingPaneLayout ke tombol kembali sistem.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            SportsListOnBackPressedCallback(slidingPaneLayout)
        )

        // Inisialisasi adaptor dan setel ke RecyclerView.
        val adapter = SportsAdapter {
            // Perbarui olahraga yang dipilih pengguna sebagai olahraga saat ini dalam model tampilan bersama
            // Ini akan secara otomatis memperbarui konten panel ganda
            sportsViewModel.updateCurrentSport(it)
            // Geser panel detail ke tampilan. Jika kedua panel terlihat,
            // ini tidak memiliki efek yang terlihat.
            binding.slidingPaneLayout.openPane()
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }
}

/**
 * Callback menyediakan navigasi kembali kustom.
 */

class SportsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    // Tetapkan status 'diaktifkan' default ke true hanya jika dapat digeser (mis., panel
    // tumpang tindih) dan terbuka (mis., panel detail terlihat).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        // Kembali ke panel daftar saat tombol kembali sistem ditekan.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        // Mencegat tombol kembali sistem saat panel detail terlihat.
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        // Nonaktifkan penyadapan tombol kembali sistem saat pengguna kembali ke
        // panel daftar.
        isEnabled = false
    }
}
