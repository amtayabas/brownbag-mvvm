package csv.brownbag.mvvm.advanced.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val imageUrl: String,
    val category: String,
    val desc: String
): Parcelable
