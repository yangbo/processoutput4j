package com.github.fracpete.processoutput4j.core;

import java.util.List;

/**
 * @author yangbo
 */
public interface ExpectInputProcessOwner extends StreamingProcessOwner {

    /**
     * Provide ExpectInput pair list to use.
     *
     * @return Expect and Input pair List.
     */
    List<ExpectInput> getExpectInputList();
}
