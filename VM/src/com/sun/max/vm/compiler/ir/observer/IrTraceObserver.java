/*
 * Copyright (c) 2007 Sun Microsystems, Inc.  All rights reserved.
 *
 * Sun Microsystems, Inc. has intellectual property rights relating to technology embodied in the product
 * that is described in this document. In particular, and without limitation, these intellectual property
 * rights may include one or more of the U.S. patents listed at http://www.sun.com/patents and one or
 * more additional patents or pending patent applications in the U.S. and in other countries.
 *
 * U.S. Government Rights - Commercial software. Government users are subject to the Sun
 * Microsystems, Inc. standard license agreement and applicable provisions of the FAR and its
 * supplements.
 *
 * Use is subject to license terms. Sun, Sun Microsystems, the Sun logo, Java and Solaris are trademarks or
 * registered trademarks of Sun Microsystems, Inc. in the U.S. and other countries. All SPARC trademarks
 * are used under license and are trademarks or registered trademarks of SPARC International, Inc. in the
 * U.S. and other countries.
 *
 * UNIX is a registered trademark in the U.S. and other countries, exclusively licensed through X/Open
 * Company, Ltd.
 */
package com.sun.max.vm.compiler.ir.observer;

import com.sun.max.io.*;
import com.sun.max.program.*;
import com.sun.max.vm.compiler.ir.*;

/**
 * This class implements an IR observer that prints a trace of a method's IR code before and after generation and
 * transformation.
 * <p>
 * To enable the IrTraceObserver, specify the following System properties:
 * <p>
 * <pre>
 *     -Dmax.ir.observers=IrTraceObserver
 * </pre>
 * <p>
 * To simplify tracing of all IR's for a particular method, the {@code "max.ir.trace"} property can be used as described
 * {@linkplain IrObserverConfiguration here}.
 *
 * @author Ben L. Titzer
 * @author Doug Simon
 */
public class IrTraceObserver extends IrObserverAdapter {

    protected static final IndentWriter _out = IndentWriter.traceStreamWriter();

    private final int _traceLevel;

    private final Class<? extends IrMethod> _irMethodType;

    public static final String PROPERTY_TRACE_LEVEL = "max.ir.trace.level";

    protected static final int DEFAULT_TRACE_LEVEL = 3;

    public IrTraceObserver() {
        this(IrMethod.class);
    }

    public IrTraceObserver(Class<? extends IrMethod> irMethodType) {
        _irMethodType = irMethodType;
        int traceLevel = 3;
        try {
            traceLevel = Integer.parseInt(System.getProperty(PROPERTY_TRACE_LEVEL, String.valueOf(traceLevel)));
        } catch (NumberFormatException e) {
            ProgramWarning.message("Value for system property \"" + PROPERTY_TRACE_LEVEL + "\" not a valid integer: " + System.getProperty(PROPERTY_TRACE_LEVEL));
        }
        _traceLevel = traceLevel;
    }

    protected String traceString(IrMethod irMethod, String description) {
        return irMethod.getClass().getSimpleName() + "[" + irMethod.classMethodActor() + "] - " + description;
    }

    protected boolean hasLevel(int requiredLevel) {
        return _traceLevel >= requiredLevel;
    }

    protected int allocationTraceLevel() {
        return DEFAULT_TRACE_LEVEL;
    }

    protected int afterGenerationTraceLevel() {
        return DEFAULT_TRACE_LEVEL;
    }

    protected int transformTraceLevel(Object transform) {
        return DEFAULT_TRACE_LEVEL;
    }

    @Override
    public void observeAllocation(IrMethod irMethod) {
        if (_irMethodType.isAssignableFrom(irMethod.getClass())) {
            if (hasLevel(allocationTraceLevel())) {
                _out.println(traceString(irMethod, "allocated"));
            }
        }
    }

    @Override
    public void observeBeforeGeneration(IrMethod irMethod, IrGenerator irGenerator) {
        // For console tracing, the IR state before generation is typically not very interesting
    }

    @Override
    public void observeAfterGeneration(IrMethod irMethod, IrGenerator irGenerator) {
        if (_irMethodType.isAssignableFrom(irMethod.getClass())) {
            if (hasLevel(afterGenerationTraceLevel())) {
                _out.println(traceString(irMethod, "after generation"));
                _out.println(irMethod.traceToString());
                _out.flush();
            }
        }
    }

    @Override
    public void observeBeforeTransformation(IrMethod irMethod, Object context, Object transform) {
        if (_irMethodType.isAssignableFrom(irMethod.getClass())) {
            if (hasLevel(transformTraceLevel(transform))) {
                _out.println(traceString(irMethod, "before transformation: " + transform));
                _out.println(irMethod.traceToString());
            }
        }
    }

    @Override
    public void observeAfterTransformation(IrMethod irMethod, Object context, Object transform) {
        if (_irMethodType.isAssignableFrom(irMethod.getClass())) {
            if (hasLevel(transformTraceLevel(transform))) {
                _out.println(traceString(irMethod, "after transformation: " + transform));
                _out.println(irMethod.traceToString());
            }
        }
    }
}
