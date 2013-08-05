/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     vpasquier <vpasquier@nuxeo.com>
 *     slacoin <slacoin@nuxeo.com>
 */
package org.nuxeo.ecm.automation.core.trace;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @since 5.7.3
 */
public class TracePrinter {

    private static final Log log = LogFactory.getLog(TracePrinter.class);

    protected final BufferedWriter writer;

    protected String preamble = "";

    public TracePrinter(Writer writer) {
        this.writer = new BufferedWriter(writer);
    }

    public TracePrinter(OutputStream out) {
        this(new OutputStreamWriter(out));
    }

    protected void printLine(String line) throws IOException {
        writer.write(preamble + line);
    }

    protected void printHeading(String heading) throws IOException {
        printLine(System.getProperty("line.separator")
                + System.getProperty("line.separator") + "****** " + heading
                + " ******");
    }

    public void print(Trace trace) throws IOException {
        StringBuilder sb = new StringBuilder();
        printHeading("chain");
        if (trace.error != null) {
            sb.append(System.getProperty("line.separator"));
            sb.append("Name: ");
            sb.append(trace.getChain().getId());
            sb.append(System.getProperty("line.separator"));
            sb.append("Exception: ");
            sb.append(trace.error.getClass().getSimpleName());
            sb.append(System.getProperty("line.separator"));
            sb.append("Caught error: ");
            sb.append(trace.error.getMessage());
            sb.append(System.getProperty("line.separator"));
            sb.append("Caused by: ");
            sb.append(trace.error.getCause());
            printLine(sb.toString());
        } else {
            printLine("produced output of type "
                    + trace.output.getClass().getSimpleName());
            // printObject((OperationType) trace.output);
        }
        print(trace.operations);
        StringBuilder sbStack = new StringBuilder();
        sbStack.append(System.getProperty("line.separator"));
        if (trace.error.getStackTrace().length != 0) {
            log.error("StackTrace: ", trace.error);
            sbStack.append(System.getProperty("line.separator"));
        }
        printLine(sbStack.toString());
        writer.flush();
    }

    public void print(List<Call> calls) throws IOException {
        for (Call call : calls) {
            print(call);
        }
    }

    public void print(Call call) throws IOException {
        printCall(call);
    }

    public void printCall(Call call) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
            sb.append("****** " + call.getType().getId() + " ******");
            sb.append(System.getProperty("line.separator"));
            sb.append("Class: ");
            sb.append(call.getType().getType().getSimpleName());
            sb.append(System.getProperty("line.separator"));
            sb.append("Method: '");
            sb.append(call.getMethod().getMethod().getName());
            sb.append("' | Input Type: ");
            sb.append(call.getMethod().getConsume());
            sb.append(" | Output Type: ");
            sb.append(call.getMethod().getProduce());
            sb.append(System.getProperty("line.separator"));
            sb.append("Input: ");
            sb.append(call.getInput());
            if (!call.getParmeters().isEmpty()) {
                sb.append(System.getProperty("line.separator"));
                sb.append("Parameters ");
                for (String parameter : call.getParmeters().keySet()) {
                    sb.append(" | ");
                    sb.append("Name: ");
                    sb.append(parameter);
                    sb.append(", Value: ");
                    sb.append(call.getParmeters().get(parameter));
                }
            }
            if (!call.getVariables().isEmpty()) {
                sb.append(System.getProperty("line.separator"));
                sb.append("Context Variables");
                for (String keyVariable : call.getVariables().keySet()) {
                    sb.append(" | ");
                    sb.append("Key: ");
                    sb.append(keyVariable);
                    sb.append(", Value: ");
                    sb.append(call.getVariables().get(keyVariable));
                }
            }
            sb.append(System.getProperty("line.separator"));
            printLine(sb.toString());
        } catch (IOException e) {
            log.error(e);
        }
    }
}
