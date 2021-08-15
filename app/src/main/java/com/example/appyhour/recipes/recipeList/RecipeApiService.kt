package com.example.appyhour.recipes.recipeList

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://gregamerrill.github.io/AppyHourRecipes/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface RecipeApiService {
    @GET("Recipes.json")
    suspend fun getRecipes(): List<Recipe>
}

object RecipeApi {
    val retrofitService : RecipeApiService by lazy { retrofit.create(RecipeApiService::class.java) }
}