        .section        ".data"
        .align          4

G_A:    .word           0
G_B:    .word           0
G_C:    .word           0
G_A_Y:  .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0
        .word           0

        .section        ".text"
        .align          4
        .global         start
start:
        print G_A_Y + 20    ! Print array
        print G_A           ! Print varible
        set G_A_Y,%l0       ! Assign array
        set 12,%l1          ! Array offset
        set 1,%l2
        st %l2,[%l0 + %l1]
        print G_A_Y + 12    ! Print array
        print G_B           ! Print varible
        set G_B,%l0         ! Assign varible
        set 9,%l2
        st %l2,[%l0]
        print G_B           ! Print varible
        print G_A_Y + 12    ! Print array
exit_program
