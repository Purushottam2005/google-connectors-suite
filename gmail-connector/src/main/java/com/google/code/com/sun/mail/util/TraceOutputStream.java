/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is a subclass of DataOutputStream that copies the
 * data being written into the DataOutputStream into another output
 * stream. This class is used here to provide a debug trace of the
 * stuff thats being written out into the DataOutputStream.
 *
 * @author John Mani
 */

public class TraceOutputStream extends FilterOutputStream {
    private boolean trace = false;
    private boolean quote = false;
    private OutputStream traceOut;

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param   out   the underlying output stream.
     * @param	traceOut	the trace stream.
     */
    public TraceOutputStream(OutputStream out, OutputStream traceOut) {
	super(out);
	this.traceOut = traceOut;
    }

    /**
     * Set the trace mode.
     */
    public void setTrace(boolean trace) {
	this.trace = trace;
    }

    /**
     * Set quote mode.
     * @param	quote	the quote mode
     */
    public void setQuote(boolean quote) {
	this.quote = quote;
    }

    /**
     * Writes the specified <code>byte</code> to this output stream.
     * Writes out the byte into the trace stream if the trace mode
     * is <code>true</code>
     */
    public void write(int b) throws IOException {
	if (trace) {
	    if (quote)
		writeByte(b);
	    else
		traceOut.write(b);
	}
	out.write(b);
    }
	    
    /**
     * Writes <code>b.length</code> bytes to this output stream.
     * Writes out the bytes into the trace stream if the trace
     * mode is <code>true</code>
     */
    public void write(byte b[], int off, int len) throws IOException {
	if (trace) {
	    if (quote) {
		for (int i = 0; i < len; i++)
		    writeByte(b[off + i]);
	    } else
		traceOut.write(b, off, len);
	}
	out.write(b, off, len);
    }

    /**
     * Write a byte in a way that every byte value is printable ASCII.
     */
    private final void writeByte(int b) throws IOException {
	b &= 0xff;
	if (b > 0x7f) {
	    traceOut.write('M');
	    traceOut.write('-');
	    b &= 0x7f;
	}
	if (b == '\r') {
	    traceOut.write('\\');
	    traceOut.write('r');
	} else if (b == '\n') {
	    traceOut.write('\\');
	    traceOut.write('n');
	    traceOut.write('\n');
	} else if (b == '\t') {
	    traceOut.write('\\');
	    traceOut.write('t');
	} else if (b < ' ') {
	    traceOut.write('^');
	    traceOut.write('@' + b);
	} else {
	    traceOut.write(b);
	}
    }
}