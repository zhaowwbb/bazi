package com.rick.bazi
import com.rick.bazi.util.BaziPaiPanUtilTest
import com.rick.bazi.util.GeJuUtilTest
import com.rick.bazi.util.WuXingUtilTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    BaziPaiPanUtilTest::class,
    WuXingUtilTest::class,
    GeJuUtilTest::class
)
class AllMyTests