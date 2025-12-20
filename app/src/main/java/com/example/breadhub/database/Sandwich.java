package com.example.breadhub.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "sandwiches",
        foreignKeys = @ForeignKey(
                entity = SandwichType.class,
                parentColumns = "id",
                childColumns = "typeId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Sandwich {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int typeId;
    public String name;
    public String proteins;
    public String veggies;
    public String cheeses;
    public String sauces;

    public Sandwich(int typeId, String name,
                    String proteins, String veggies,
                    String cheeses, String sauces) {
        this.typeId = typeId;
        this.name = name;
        this.proteins = proteins;
        this.veggies = veggies;
        this.cheeses = cheeses;
        this.sauces = sauces;
    }
}
