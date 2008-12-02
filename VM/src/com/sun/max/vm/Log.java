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
package com.sun.max.vm;

import java.io.*;

import com.sun.max.annotate.*;
import com.sun.max.memory.*;
import com.sun.max.program.*;
import com.sun.max.unsafe.*;
import com.sun.max.vm.actor.member.*;
import com.sun.max.vm.runtime.*;
import com.sun.max.vm.thread.*;

/**
 * This class presents a low-level VM logging facility that closely resembles (but extends) that offered by standard
 * {@link PrintStream}s. All output of the methods in this class goes to a configurable {@linkplain #os output stream}.
 *
 * @author Ben L. Titzer
 * @author Doug Simon
 */
public final class Log {

    private Log() {
    }

    static {
        new CriticalNativeMethod(Log.class, "log_lock");
        new CriticalNativeMethod(Log.class, "log_unlock");

        new CriticalNativeMethod(Log.class, "log_print_buffer");
        new CriticalNativeMethod(Log.class, "log_print_boolean");
        new CriticalNativeMethod(Log.class, "log_print_char");
        new CriticalNativeMethod(Log.class, "log_print_int");
        new CriticalNativeMethod(Log.class, "log_print_long");
        new CriticalNativeMethod(Log.class, "log_print_float");
        new CriticalNativeMethod(Log.class, "log_print_double");
        new CriticalNativeMethod(Log.class, "log_print_word");
        new CriticalNativeMethod(Log.class, "log_print_newline");
    }

    @C_FUNCTION
    private static native void log_print_buffer(Address val);

    @C_FUNCTION
    private static native void log_print_boolean(boolean val);

    @C_FUNCTION
    private static native void log_print_char(int val);

    @C_FUNCTION
    private static native void log_print_int(int val);

    @C_FUNCTION
    private static native void log_print_long(long val);

    @C_FUNCTION
    private static native void log_print_float(float val);

    @C_FUNCTION
    private static native void log_print_double(double val);

    @C_FUNCTION
    private static native void log_print_word(Word val);

    @C_FUNCTION
    private static native void log_print_newline();

    @C_FUNCTION
    private static native void log_lock();

    @C_FUNCTION
    private static native void log_unlock();

    /**
     * The singleton VM log output stream.
     *
     * This output stream writes to the native standard output stream by default. It can be redirected
     * to write to the native standard error stream or a file instead by setting the value of the
     * environment variable (<b>not</b> system property) named {@code MAXINE_LOG_FILE}.
     * If set, the value of this environment variable is interpreted as a file path to which VM
     * output will be written. The special values {@code "stdout"} and {@code "stderr"} are
     * interpreted to mean the native standard output and error streams respectively.
     */
    public static final OutputStream os = new LogOutputStream();


    /**
     * The singleton VM print stream. This print stream sends all its output to {@link #os}.
     */
    public static final LogPrintStream out = new LogPrintStream(os);

    /**
     * Equivalent to calling {@link LogPrintStream#print(String)} on {@link #out}.
     */
    public static void print(String s) {
        out.print(s);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(String, boolean)} on {@link #out}.
     */
    public static void print(String s, boolean withNewLine) {
        out.print(s, withNewLine);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(Object)} on {@link #out}.
     */
    public static void print(Object object) {
        out.print(object);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(int)} on {@link #out}.
     */
    public static void print(int i) {
        out.print(i);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(long)} on {@link #out}.
     */
    public static void print(long i) {
        out.print(i);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(char)} on {@link #out}.
     */
    public static void print(char c) {
        out.print(c);
    }

    /**
     * Equivalent to calling {@link DebugPrintStream#print(boolean))} on {@link #out}.
     */
    public static void print(boolean b) {
        out.print(b);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(double)} on {@link #out}.
     */
    public static void print(double d) {
        out.print(d);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(float)} on {@link #out}.
     */
    public static void print(float f) {
        out.print(f);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(char[])} on {@link #out}.
     */
    public static void print(char[] c) {
        out.print(c);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#print(Word)} on {@link #out}.
     */
    public static void print(Word word) {
        out.print(word);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(String)} on {@link #out}.
     */
    public static void println(String s) {
        out.println(s);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(Object)} on {@link #out}.
     */
    public static void println(Object object) {
        out.println(object);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println()} on {@link #out}.
     */
    public static void println() {
        out.println();
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(int)} on {@link #out}.
     */
    public static void println(int i) {
        out.println(i);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(long)} on {@link #out}.
     */
    public static void println(long i) {
        out.println(i);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(char)} on {@link #out}.
     */
    public static void println(char c) {
        out.println(c);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(boolean)} on {@link #out}.
     */
    public static void println(boolean b) {
        out.println(b);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(double)} on {@link #out}.
     */
    public static void println(double d) {
        out.println(d);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(float)} on {@link #out}.
     */
    public static void println(float f) {
        out.println(f);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(char[])} on {@link #out}.
     */
    public static void println(char[] c) {
        out.println(c);
    }

    /**
     * Equivalent to calling {@link LogPrintStream#println(Word)} on {@link #out}.
     */
    public static void println(Word word) {
        out.println(word);
    }

    private static final class LogOutputStream extends OutputStream {
        /**
         * Only a {@linkplain Log#os singleton} instance of this class exists.
         */
        private LogOutputStream() {
        }

        @PROTOTYPE_ONLY
        private static final OutputStream _prototypeOutputStream;
        static {
            // Use the same environment variable as used by the native code - see Native/share/debug.c
            String path = System.getenv("MAXINE_LOG_FILE");
            if (path == null) {
                path = "stdout";
            }
            if (path.equals("stdout")) {
                _prototypeOutputStream = System.out;
            } else if (path.equals("stderr")) {
                _prototypeOutputStream = System.err;
            } else {
                try {
                    _prototypeOutputStream = new FileOutputStream(path);
                } catch (FileNotFoundException fileNotFoundException) {
                    throw ProgramError.unexpected("Could not open file for VM output stream: " + path, fileNotFoundException);
                }
            }
        }

        @Override
        public void write(int b) throws IOException {
            if (MaxineVM.isPrototyping()) {
                _prototypeOutputStream.write(b);
                if (b == '\n') {
                    _prototypeOutputStream.flush();
                }
            } else {
                log_print_char(b);
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            if (MaxineVM.isPrototyping()) {
                _prototypeOutputStream.write(b, off, len);
                for (int i = (off + len) - 1; i >= off; --i) {
                    if (b[i] == '\n') {
                        _prototypeOutputStream.flush();
                        break;
                    }
                }
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                final Pointer buffer = BootMemory.buffer();
                final int bufferSize = BootMemory.bufferSize();
                int i = off;
                final int end = off + len;
                while (i < end) {
                    i = CString.writeBytes(b, i, end, buffer, bufferSize);
                    log_print_buffer(buffer);
                }
                Log.unlock(lockDisabledSafepoints);
            }
        }
    }

    /**
     * Convenience routine for printing a FieldActor to a given DebugPrintStream. The output is of the form:
     *
     * <pre>
     *     &lt;name&gt;:&lt;descriptor&gt; in &lt;holder&gt;
     * </pre>
     *
     * For example, the output for {@link System#err} is:
     *
     * <pre>
     * &quot;err:Ljava/io/PrintStream; in java.lang.System&quot;
     * </pre>
     * @param fieldActor the field actor to print
     * @param withNewline specifies if a newline should be appended to the stream after the field actor
     */
    public static void printFieldActor(FieldActor fieldActor, boolean withNewline) {
        boolean lockDisabledSafepoints = false;
        if (!MaxineVM.isPrototyping()) {
            lockDisabledSafepoints = lock();
        }
        print(fieldActor.name().string());
        print(":");
        print(fieldActor.descriptor().string());
        print(" in ");
        print(fieldActor.holder().name().string(), withNewline);
        if (!MaxineVM.isPrototyping()) {
            unlock(lockDisabledSafepoints);
        }
    }

    /**
     * Convenience routine for printing a MethodActor to a given DebugPrintStream. The output is of the form:
     *
     * <pre>
     *     &lt;name&gt;&lt;descriptor&gt; in &lt;holder&gt;
     * </pre>
     *
     * For example, the output for {@link Runnable#run()} is:
     *
     * <pre>
     * &quot;run()V in java.lang.Runnable&quot;
     * </pre>
     * @param methodActor the method actor to print
     * @param withNewline specifies if a newline should be appended to the stream after the method actor
     */
    public static void printMethodActor(MethodActor methodActor, boolean withNewline) {
        boolean lockDisabledSafepoints = false;
        if (!MaxineVM.isPrototyping()) {
            lockDisabledSafepoints = lock();
        }
        print(methodActor.name().string());
        print(methodActor.descriptor().string());
        print(" in ");
        print(methodActor.holder().name().string(), withNewline);
        if (!MaxineVM.isPrototyping()) {
            unlock(lockDisabledSafepoints);
        }
    }

    /**
     * Convenience routine for printing a {@link VmThread} to a given DebugPrintStream. The output is of the form:
     *
     * <pre>
     *     &lt;name&gt;[&lt;id&gt;]
     * </pre>
     *
     * For example, the output for the main thread of execution may be:
     *
     * <pre>
     * &quot;main[id=1]&quot;
     * </pre>
     * @param vmThread the thread to print
     * @param withNewline specifies if a newline should be appended to the stream after the thread
     */
    public static void printVmThread(VmThread vmThread, boolean withNewline) {
        boolean lockDisabledSafepoints = false;
        if (!MaxineVM.isPrototyping()) {
            lockDisabledSafepoints = lock();
        }
        print(vmThread.getName());
        print("[serial=");
        print(vmThread.serial());
        print("]", withNewline);
        if (!MaxineVM.isPrototyping()) {
            unlock(lockDisabledSafepoints);
        }
    }

    public static final class LogPrintStream extends PrintStream {
        /**
         * Only a {@linkplain Log#out singleton} instance of this class exists.
         */
        private LogPrintStream(OutputStream output) {
            super(output);
        }

        public void print(String s, boolean withNewline) {
            if (withNewline) {
                println(s);
            } else {
                print(s);
            }
        }
        @Override
        public void print(String s) {
            if (MaxineVM.isPrototyping()) {
                super.print(s);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                printString(s);
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void print(int i) {
            if (MaxineVM.isPrototyping()) {
                super.print(i);
            } else {
                // locking is not really necessary for primitives
                log_print_int(i);
            }
        }
        @Override
        public void print(long i) {
            if (MaxineVM.isPrototyping()) {
                super.print(i);
            } else {
                // locking is not really necessary for primitives
                log_print_long(i);
            }
        }
        @Override
        public void print(char c) {
            if (MaxineVM.isPrototyping()) {
                super.print(c);
            } else {
                // locking is not really necessary for primitives
                log_print_char(c);
            }
        }
        @Override
        public void print(boolean b) {
            if (MaxineVM.isPrototyping()) {
                super.print(b);
            } else {
                // locking is not really necessary for primitives
                log_print_boolean(b);
            }
        }
        @Override
        public void print(double d) {
            if (MaxineVM.isPrototyping()) {
                super.print(d);
            } else {
                // locking is not really necessary for primitives
                log_print_double(d);
            }
        }
        @Override
        public void print(float f) {
            if (MaxineVM.isPrototyping()) {
                super.print(f);
            } else {
                // locking is not really necessary for primitives
                log_print_float(f);
            }
        }
        @Override
        public void print(char[] c) {
            if (MaxineVM.isPrototyping()) {
                super.print(c);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                printChars(c);
                Log.unlock(lockDisabledSafepoints);
            }
        }
        public void print(Word word) {
            if (MaxineVM.isPrototyping()) {
                super.print(word.toHexString());
            } else {
                // locking is not really necessary for primitives
                log_print_word(word);
            }
        }
        @Override
        public void println(String s) {
            if (MaxineVM.isPrototyping()) {
                super.println(s);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                printString(s);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println() {
            if (MaxineVM.isPrototyping()) {
                super.println();
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(int i) {
            if (MaxineVM.isPrototyping()) {
                super.println(i);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_int(i);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(long i) {
            if (MaxineVM.isPrototyping()) {
                super.println(i);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_long(i);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(char c) {
            if (MaxineVM.isPrototyping()) {
                super.println(c);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_char(c);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(boolean b) {
            if (MaxineVM.isPrototyping()) {
                super.println(b);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_boolean(b);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(double d) {
            if (MaxineVM.isPrototyping()) {
                super.println(d);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_double(d);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(float f) {
            if (MaxineVM.isPrototyping()) {
                super.println(f);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_float(f);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        @Override
        public void println(char[] c) {
            if (MaxineVM.isPrototyping()) {
                super.println(c);
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                printChars(c);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }
        public void println(Word word) {
            if (MaxineVM.isPrototyping()) {
                super.println(word.toHexString());
            } else {
                final boolean lockDisabledSafepoints = Log.lock();
                log_print_word(word);
                log_print_newline();
                Log.unlock(lockDisabledSafepoints);
            }
        }

        private void printString(String string) {
            final String s = string == null ? "null" : string;
            final Pointer buffer = BootMemory.buffer();
            final int bufferSize = BootMemory.bufferSize();
            int i = 0;
            while (i < s.length()) {
                i = CString.writePartialUtf8(s, i, buffer, bufferSize);
                log_print_buffer(buffer);
            }
        }

        private void printChars(char[] ch) {
            final Pointer buffer = BootMemory.buffer();
            final int bufferSize = BootMemory.bufferSize();
            int i = 0;
            while (i < ch.length) {
                i = CString.writePartialUtf8(ch, i, buffer, bufferSize);
                log_print_buffer(buffer);
            }
        }
    }

    /**
     * Attempts to acquire the global lock on all debug output, blocking until the lock is successfully acquired. This
     * lock can be acquired recursively by a thread. The lock is not released for other threads until the thread that
     * owns the lock calls {@link #unlock} the same number of times it called this method.
     *
     * This method ensures that safepoints are disabled before it returns.
     *
     * @return true if this call caused safepoints to be disabled (i.e. they were enabled upon entry to this method).
     *         This value must be passed to the paired call to {@link #unlock} so that safepoints are restored to the
     *         appropriate state (i.e. the state they had before the sequence of code protected by this lock was
     *         entered).
     */
    public static boolean lock() {
        final boolean safepointsDisabled = Safepoint.isDisabled();
        if (!safepointsDisabled) {
            Safepoint.disable();
        }
        Log.log_lock();
        return !safepointsDisabled;
    }

    /**
     * Attempts to releases the global lock on all debug output. This must only be called by a thread that currently
     * owns the lock - failure to do so causes the VM to exit. The lock is not released for other threads until this
     * method is called the same number of times as {@link #lock()} was called when acquiring the lock.
     *
     * @param lockDisabledSafepoints specifies if the adjoining call to {@link #lock()} disabled safepoints. If so, then
     *            this call will re-enable them.
     */
    public static void unlock(boolean lockDisabledSafepoints) {
        Log.log_unlock();
        FatalError.check(Safepoint.isDisabled(), "Safepoints must not be re-enabled in code surrounded by Debug.lock() and Debug.unlock()");
        if (lockDisabledSafepoints) {
            Safepoint.enable();
        }
    }
}
