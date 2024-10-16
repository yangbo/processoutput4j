package com.github.fracpete.processoutput4j.core.impl;

import com.github.fracpete.processoutput4j.core.ExpectInput;
import com.github.fracpete.processoutput4j.core.ExpectInputProcessOwner;
import com.github.fracpete.processoutput4j.core.StreamingProcessOutputType;

import java.util.List;

/**
 * @author yangbo
 */
public class SimpleExpectInputProcessOwner implements ExpectInputProcessOwner {

    @Override
    public List<ExpectInput> getExpectInputList() {
        return List.of(new ExpectInput("Password:", "user123"));
    }

    @Override
    public StreamingProcessOutputType getOutputType() {
        return StreamingProcessOutputType.BOTH;
    }

    @Override
    public void processOutput(String line, boolean stdout) {
        System.out.println((stdout ? "[OUT] " : "[ERR] ") + line);
    }
}
