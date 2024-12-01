package dev.httpmarco.netline;

import dev.httpmarco.netline.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        NetServerTest.class, NetClientTest.class
})
@SuiteDisplayName("NetLine Test Suite")
public class NetLineTestSuite {
}
