package dk.chen.garbagev1.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query(value = "SELECT * FROM items ORDER BY `where`, what")
    fun getGarbageList(): Flow<List<ItemEntity>>

    @Query(value = "SELECT * FROM items WHERE id = :id")
    fun getItem(id: String): Flow<ItemEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Update
    suspend fun update(item: ItemEntity)

    @Query(value = "DELETE FROM items WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM items WHERE what = :what LIMIT 1")
    fun getItemWhere(what: String): Flow<ItemEntity?>
}