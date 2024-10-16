package com.github.fracpete.processoutput4j.core;

/**
 * The expect and input pair for the process.
 * <p>
 * The program will match `ExpectInput.expect` with the output of the process, if match it will send `ExpectInput.input`
 * content to the process.
 * <p>
 * This is used for enter password of rsync when there is 'Password:' prompt of the rsync process.
 *
 * @author yangbo
 */
public class ExpectInput {
    /**
     * The expected string for match the output of process.
     */
    private String expect;

    /**
     * The input content to send to the process.
     */
    private String input;


    public ExpectInput(){

    }

    public ExpectInput(String expect, String input) {
        this.expect = expect;
        this.input = input;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "ExpectInput{" +
                "expect='" + expect + '\'' +
                ", input='" + input + '\'' +
                '}';
    }

}
