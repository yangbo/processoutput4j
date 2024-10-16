package com.github.fracpete.processoutput4j.output;

import com.github.fracpete.processoutput4j.core.ExpectInputProcessOwner;
import com.github.fracpete.processoutput4j.reader.AbstractProcessReader;
import com.github.fracpete.processoutput4j.reader.ExpectInputProcessReader;

/**
 * @author yangbo
 */
public class ExpectInputProcessOutput extends StreamingProcessOutput {
    protected ExpectInputProcessOwner m_Owner;

    /**
     * Initializes the process output with the specified owning object.
     *
     * @param owner the owning object
     */
    public ExpectInputProcessOutput(ExpectInputProcessOwner owner) {
        super(owner);
        m_Owner = owner;
    }

    /**
     * Configures the thread for stderr.
     *
     * @return the configured thread, not yet started
     */
    @Override
    protected AbstractProcessReader configureStdErr() {
        return new ExpectInputProcessReader(m_Owner, false);
    }

    /**
     * Configures the thread for stdout.
     *
     * @return the configured thread, not yet started
     */
    @Override
    protected AbstractProcessReader configureStdOut() {
        return new ExpectInputProcessReader(m_Owner, true);
    }
}
