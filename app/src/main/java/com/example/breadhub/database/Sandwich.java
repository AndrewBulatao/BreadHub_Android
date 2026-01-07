package com.example.breadhub.database;

import androidx.annotation.NonNull;
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

    /*
     * typeId meanings:
     * 1 = Sandwiches
     * 2 = Subs / Hoagies
     * 3 = Bánh mì
     * 4 = Panini
     * 5 = Burgers
     */

    // TODO: Convert strings to List<String> and then make a converter for them
    // A converter for Array -> String and another for String -> Array
    @NonNull
    public String name;

    public int typeId;

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
