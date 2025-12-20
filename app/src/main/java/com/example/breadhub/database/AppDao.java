package com.example.breadhub.database;

import androidx.room.*;
import java.util.List;

@Dao
public interface AppDao {

    @Insert
    void insertType(SandwichType type);

    @Query("SELECT * FROM sandwich_types")
    List<SandwichType> getAllTypes();

    @Insert
    void insertSandwich(Sandwich sandwich);

    @Query("SELECT * FROM sandwiches WHERE typeId = :typeId")
    List<Sandwich> getSandwichesForType(int typeId);

    @Query("SELECT * FROM sandwiches")
    List<Sandwich> getAllSandwiches();

    @Delete
    void deleteSandwich(Sandwich sandwich);
}
