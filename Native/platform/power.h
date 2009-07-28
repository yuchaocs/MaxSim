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
 * @author Bernd Mathiske
 */
#ifndef __power_h__
#define __power_h__ 1

#include "word.h"

typedef void *power_OsTeleIntegerRegisters;

typedef struct power_CanonicalIntegerRegisters {
    Word R0;
    /* TODO */
} power_CanonicalIntegerRegistersAggregate, *power_CanonicalIntegerRegisters;

extern void power_canonicalizeTeleIntegerRegisters(power_OsTeleIntegerRegisters osTeleIntegerRegisters, power_CanonicalIntegerRegisters canonicalIntegerRegisters);

extern void power_canonicalizeTeleFloatingPointRegisters(power_OsTeleFloatingPointRegisters osTeleFloatingPointRegisters, power_CanonicalFloatingPointRegisters canonicalFloatingPointRegisters);

extern void power_canonicalizeTeleStateRegisters(power_OsTeleStateRegisters osTeleStateRegisters, power_CanonicalStateRegisters canonicalStateRegisters);

extern void power_printCanonicalIntegerRegisters(power_CanonicalIntegerRegisters canonicalIntegerRegisters);

extern void power_printCanonicalFloatingPointRegisters(power_CanonicalFloatingPointRegisters canonicalFloatingPointRegisters);

#endif /*__power_h__*/
