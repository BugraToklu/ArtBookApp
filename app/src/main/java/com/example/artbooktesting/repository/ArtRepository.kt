package com.example.artbooktesting.repository

import androidx.lifecycle.LiveData
import com.example.artbooktesting.api.RetrofitAPI
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.roomdb.Art
import com.example.artbooktesting.roomdb.ArtDao
import com.example.artbooktesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArt()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Response body is null", null)
            }else{
                Resource.error("Response not successful: ${response.code()} - ${response.message()}",null)
            }
        }catch (e: Exception){
            Resource.error("Exception ${e.localizedMessage}",null)
        }
    }
}