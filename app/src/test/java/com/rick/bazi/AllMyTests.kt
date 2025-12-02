package com.rick.bazi
import com.rick.bazi.util.BaziPaiPanUtilTest
import com.rick.bazi.util.DiZhiUtilTest
import com.rick.bazi.util.GeJuUtilTest
import com.rick.bazi.util.TianGanUtilTest
import com.rick.bazi.util.WuXingUtilTest
import com.rick.bazi.util.YongShenUtilTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    BaziPaiPanUtilTest::class,
    WuXingUtilTest::class,
    GeJuUtilTest::class,
    DiZhiUtilTest::class,
    YongShenUtilTest::class,
    TianGanUtilTest::class
)
class AllMyTests