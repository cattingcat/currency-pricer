import org.currency_pricer.sources.KurscomruDataSource
import org.junit.Assert
import org.junit.Test

class KurscomruIntegrationTest {

    @Test
    fun simpleTest() {
        val src = KurscomruDataSource()
        val d = src.loadData()

        Assert.assertNotEquals(d.size, 0)
    }
}