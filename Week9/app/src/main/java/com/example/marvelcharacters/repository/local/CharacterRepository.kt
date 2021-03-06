package com.example.marvelcharacters.repository.local

import androidx.lifecycle.LiveData
import com.example.marvelcharacters.model.entities.Character

interface CharacterRepository {
    suspend fun insert(characters: List<Character>)
    fun getAllCharacters(): LiveData<List<Character>>
}