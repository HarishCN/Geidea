import com.geidea.data.local.AppDatabase
import com.geidea.data.local.DatabaseHelper
import com.geidea.data.local.entity.User

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun getUsersDetails(userId: Int?): User =appDatabase.userDao().getDetails(userId)

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

}