package com.esporter.client.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({ TestConnection.class })
public class AllTests {
}