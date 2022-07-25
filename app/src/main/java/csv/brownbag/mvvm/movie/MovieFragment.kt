package csv.brownbag.mvvm.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import csv.brownbag.mvvm.advanced.model.Movie
import csv.brownbag.mvvm.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private var selectedMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.let {
            MovieFragmentArgs.fromBundle(
                it
            )
        }
        if (args != null) {
            selectedMovie = args.selectedMovie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        selectedMovie?.let {
            with(binding) {
                tvTitle.text = selectedMovie!!.name
                tvDescription.text = selectedMovie!!.desc

                Glide.with(this@MovieFragment)
                    .load(selectedMovie!!.imageUrl)
                    .into(ivPoster)
            }
        }
    }
}