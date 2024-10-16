package com.github.fracpete.processoutput4j.reader;

import com.github.fracpete.processoutput4j.core.ExpectInput;
import com.github.fracpete.processoutput4j.core.ExpectInputProcessOwner;
import com.github.fracpete.processoutput4j.core.StreamingProcessOutputType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Read output of process and expect matches by regular expression and then send input to the process.
 *
 * @author yangbo
 */
public class ExpectInputProcessReader extends AbstractProcessReader {

    /**
     * the owner.
     */
    protected ExpectInputProcessOwner m_Owner;

    /**
     * whether to forward the output to the owner.
     */
    protected boolean m_Forward;


    /**
     * Initializes the reader.
     *
     * @param stdout whether to read stdout or stderr
     */
    public ExpectInputProcessReader(ExpectInputProcessOwner owner, boolean stdout) {
        super(stdout);
        m_Owner = owner;
        m_Forward = (stdout && (m_Owner.getOutputType() == StreamingProcessOutputType.STDOUT))
                || (!stdout && (m_Owner.getOutputType() == StreamingProcessOutputType.STDERR))
                || (m_Owner.getOutputType() == StreamingProcessOutputType.BOTH);
    }

    @Override
    protected void process(String line) {
        List<ExpectInput> expectList = m_Owner.getExpectInputList();
        for (ExpectInput expectInput : expectList) {
            if (line.matches(expectInput.getExpect())) {
                input(expectInput.getInput());
            }
        }
        if (m_Forward) {
            m_Owner.processOutput(line, isStdout());
        }
    }

    private void input(String content) {
        OutputStream inStream = m_Process.getOutputStream();
        if (inStream == null) {
            logError("Can not send input to process, because the OutputStream of process is null!");
            return;
        }
        OutputStreamWriter writer = new OutputStreamWriter(inStream);
        try {
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logError("Send input to process failed!", e);
        }
    }
}
