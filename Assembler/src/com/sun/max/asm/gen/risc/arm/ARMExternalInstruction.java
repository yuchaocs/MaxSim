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
/**
 *
 */
package com.sun.max.asm.gen.risc.arm;

import com.sun.max.asm.*;
import com.sun.max.asm.dis.*;
import com.sun.max.asm.gen.*;
import com.sun.max.asm.gen.risc.*;
import com.sun.max.collect.*;


/**
 * Output of ARM instructions in external assembler format.
 *
 * @author Sumeet Panchal
 */
public class ARMExternalInstruction extends RiscExternalInstruction {

    ARMExternalInstruction(ARMTemplate template, Sequence<Argument> arguments) {
        super(template, arguments);
    }

    public ARMExternalInstruction(ARMTemplate template, Sequence<Argument> arguments, ImmediateArgument address, AddressMapper addressMapper) {
        super(template, arguments, address, addressMapper);
    }

    @Override
    public boolean isAbsoluteBranch() {
        return false;
        // An absolute branch instruction in PowerPC has an AA field with its bit set
        //return Sequence.Static.containsEqual(_template.optionFields(), ARMFields._aa) && (_template.opcode() & ARMFields._aa.bitRange().instructionMask()) != 0;
    }

}
