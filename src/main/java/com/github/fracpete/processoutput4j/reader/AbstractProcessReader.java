/*
 * AbstractProcessReader.java
 * Copyright (C) 2017-2024 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.processoutput4j.reader;

import com.github.fracpete.processoutput4j.core.AbstractProcessRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Ancestor for readers that read line from stdout/stderr of the provided
 * {@link Process} object.
 *
 * @author fracpete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractProcessReader
        extends AbstractProcessRunnable {

    /**
     * whether to use stdout or stderr.
     */
    protected boolean m_Stdout;

    /**
     * the reader use internally.
     */
    protected BufferedReader m_Reader;

    /**
     * Initializes the reader.
     *
     * @param stdout whether to read stdout or stderr
     */
    public AbstractProcessReader(boolean stdout) {
        super();
        m_Stdout = stdout;
    }

    /**
     * Returns whether the reader is for stdout or stderr.
     *
     * @return true if for stdout
     */
    public boolean isStdout() {
        return m_Stdout;
    }

    /**
     * For processing the line read from stdout/stderr.
     *
     * @param line the output line
     */
    protected abstract void process(String line);

    /**
     * Flushes the data.
     */
    public void flush() {
        String line;

        while (m_Reader != null) {
            try {
                line = m_Reader.readLine();
                if (line == null)
                    break;
                else
                    process(line);
            } catch (IOException e) {
                // probably "stream closed"
                break;
            } catch (Exception e) {
                logError("Failed to flush!", e);
                break;
            }
        }
    }

    /**
     * The actual processing loop.
     */
    protected void doRun() {
        String line;

        try {
            if (m_Stdout)
                m_Reader = new BufferedReader(new InputStreamReader(m_Process.getInputStream()));
            else
                m_Reader = new BufferedReader(new InputStreamReader(m_Process.getErrorStream()));

            while (m_Process.isAlive()) {
                try {
                    // line = m_Reader.readLine();
                    // To support rsync password input we can not use readLine(), because it will hang up the caller
                    line = readLineSafe(1000);
                } catch (IOException ioe) {
                    // has process stopped?
                    if (ioe.getMessage().toLowerCase().contains("stream closed")) {
                        return;
                    } else {
                        throw ioe;
                    }
                }
                if (line != null && !line.isEmpty()) {
                    process(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read from " + (m_Stdout ? "stdout" : "stderr") + " for process #" + m_Process.hashCode() + ":");
            e.printStackTrace();
        }

        try {
            // make sure all data has been read
            flush();
        } catch (Exception e) {
            // ignored
        }
    }

    /**
     * Read line from m_Reader without hang on the caller.
     *
     * @param timeoutMs timeout in milliseconds.
     *                  It is the time to wait and then return as a line even there is no Carriage Return characters.
     *                  It is used for 'rsync password' scenarios.
     * @return line read
     */
    private String readLineSafe(long timeoutMs) throws IOException {
        StringBuilder line = new StringBuilder();
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            while (m_Reader.ready()) {
                line.append((char) m_Reader.read());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }

        return line.toString();
    }
}
