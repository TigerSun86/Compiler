        /* define SYS_exit = 1 (from sys/syscall.h)  */
        .set   SYS_exit, 1

	    /* define SYS_write = 4 (from sys/syscall.h)  */
	    .set   SYS_write, 4

        /* define the standard output stream (from stdio.h)  */
        .set   stdout, __iob+16

        /* exit_program -- trap to operating system */
        .macro exit_program
        set     stdout,%o0      ! first arg, %c0 := stdout
        call    fflush          ! call fflush from C library (libc); requires -lc
        nop
        clr     %o0             ! %o0 := 0;  program status=0=success
        mov     SYS_exit, %g1   ! %g1 := SYS_exit; determine system call
        ta      0
        .endm

        /* print -- print a int using "println" of the rumtime.c */
        .macro print iValue
		set \iValue,%l0
        ld [%l0],%o0            ! first arg,  %c0 := int value
        call println            ! call "println" of the rumtime.c; requires -lc
        nop                     ! delay slot for pipelining
        set     stdout,%o0      ! first arg, %c0 := stdout
        call    fflush          ! call fflush from C library (libc); requires -lc
        nop
        .endm
